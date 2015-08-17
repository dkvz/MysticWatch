
package dkvz.model;

import java.io.IOException;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author william
 */
public class GW2APIHelper {
    
    // Turns out httpClient also has a ParseException somewhere.
    
    public static final String URL_BASE_ITEM = "http://api.guildwars2.com/v2/items/";
    public static final String URL_BASE_LISTINGS = "http://api.guildwars2.com/v2/commerce/listings/";
    public static final String URL_BASE_PRICES = "http://api.guildwars2.com/v2/commerce/prices/";
    
    public static String getItemName(long itemId) throws IOException, org.json.simple.parser.ParseException {
        // They do not close httpclient in examples I've seen, I guess it kinda closes itself?
        // Maybe I'm going to close the clients that I'm going to run periodically.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = GW2APIHelper.URL_BASE_ITEM.concat(Long.toString(itemId));
        String name = "";
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse rep = httpclient.execute(httpGet);
            try {
                //System.out.println(rep.getStatusLine());
                HttpEntity content = rep.getEntity();
                String data = EntityUtils.toString(content);
                // Do something with the JSON...
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);
                JSONObject jsonObject = (JSONObject) obj;
                // If the request works but there's nothing in the json,
                // name will actually be set to "null" because that's how get
                // works.
                name = (String)jsonObject.get("name");
                EntityUtils.consume(content);
            } finally {
                rep.close();
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new org.json.simple.parser.ParseException(0);
        }
        return name;
    }
    
    // There's a way to make a request for several item id's instead of just the one.
    // I'm going to start with a 1 by 1 approach, which is totally not efficient.
    public static void getSupplyDemandHighBuyLowSellForItem(Item item) throws IOException, org.json.simple.parser.ParseException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = GW2APIHelper.URL_BASE_PRICES.concat(Long.toString(item.getId()));
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse rep = httpclient.execute(httpGet);
            try {
                HttpEntity content = rep.getEntity();
                String data = EntityUtils.toString(content);
                // Do something with the JSON...
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);
                JSONObject jsonObject = (JSONObject) obj;
                // I need to get the two maps "buys" and "sells":
                Map<String, Object> buys = (Map<String,Object>)jsonObject.get("buys");
                Map<String, Object> sells = (Map<String,Object>)jsonObject.get("sells");
                if (buys == null || sells == null) {
                    // We have a problem.
                    throw new org.json.simple.parser.ParseException(10);
                }
                Long highestBuy = (Long)buys.get("unit_price");
                Long lowestSell = (Long)sells.get("unit_price");
                item.setHighestBuyOrder(highestBuy.doubleValue());
                item.setLowestSellOrder(lowestSell.doubleValue());
                item.setDemand((Long)buys.get("quantity"));
                item.setOffer((Long)sells.get("quantity"));
                EntityUtils.consume(content);
            } finally {
                rep.close();
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new org.json.simple.parser.ParseException(0);
        }
    }
    
}
