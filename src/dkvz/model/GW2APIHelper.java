
package dkvz.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.simple.*;
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
    public static final String URL_BASE_TO_HISTORY_BUYS = "https://api.guildwars2.com/v2/commerce/transactions/history/buys";
    
    // Would be smart to set the name only if it's empty, but I'll leave to the logic using this
    // static method.
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
                // We're getting the costs in gold so we have to divide by 10000.
                item.setHighestBuyOrder(highestBuy / 10000.0);
                item.setLowestSellOrder(lowestSell/ 10000.0);
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
    
    // Quick test I'm doing right now because I need to know if I'm getting the materials for a craft.
    // Probably going to be quick and dirty.
    // The API Key has to be provided in the headers.
    public static List<Item> getTPBuyHistory(String APIKey, Date limit) throws IOException, org.json.simple.parser.ParseException {
        List<Item> res = new ArrayList<Item>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = GW2APIHelper.URL_BASE_TO_HISTORY_BUYS;
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer ".concat(APIKey));
        try {
            CloseableHttpResponse rep = httpclient.execute(httpGet);
            try {
                HttpEntity content = rep.getEntity();
                String data = EntityUtils.toString(content);
                // We're parsing the thing, until we get to a certain point in the past
                // (the "limit" date argument).
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);
                // This time the response is an array.
                JSONArray jsonList = (JSONArray) obj;
                // EXAMPLE: 2015-08-31T10:58:13+00:00
                DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                for (Object it : jsonList) {
                    JSONObject listIt = (JSONObject) it;
                    // Parse the "purchased" date first:
                    Item item = new Item();
                    Date purchased = null;
                    if (listIt.get("item_id")!= null) {
                        Long itemId = (Long)listIt.get("item_id");
                        item.setId(itemId);
                        // We're not getting the names here.
                        try {
                            String trnDate = (String)listIt.get("purchased");
                            if (trnDate != null) {
                                purchased = dateParser.parse(trnDate);
                            }
                            if (limit == null || purchased.after(limit)) {
                                item.setTransactionEndDate(purchased);
                                String trnStartDate = (String)listIt.get("started");
                                if (trnStartDate != null) {
                                    item.setTransactionStartDate(dateParser.parse(trnStartDate));
                                }
                                // Get the price too. Set as both values in the item.
                                Object price = listIt.get("price");
                                if (price != null) {
                                    Long priceL = (Long)price;
                                    double priceD = priceL / 10000.0;
                                    item.setLowestSellOrder(priceD);
                                    item.setHighestBuyOrder(priceD);
                                }
                                // Get the quantity:
                                Object qty = listIt.get("quantity");
                                if (qty != null) {
                                    Long quantity = (Long)qty;
                                    item.setQty(quantity.intValue());
                                }
                                // Add the item.
                                res.add(item);
                            }
                        } catch (java.text.ParseException ex) {
                            // We couldn't get a date parsed, abort adding that item.
                            ex.printStackTrace();
                            item = null;
                        }
                    }
                }
                EntityUtils.consume(content);
            } finally {
                rep.close();
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new org.json.simple.parser.ParseException(0);
        }
        return res;
    }
    
    // Method to query for TP listing information.
    public static TPListings getTPListings(long itemId) throws IOException, org.json.simple.parser.ParseException {
        TPListings res = new TPListings(itemId);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = GW2APIHelper.URL_BASE_LISTINGS.concat(Long.toString(itemId));
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse rep = httpclient.execute(httpGet);
            try {
                HttpEntity content = rep.getEntity();
                String data = EntityUtils.toString(content);
                // Let's save the full JSON so it's easier to write to disk later on.
                // (hopefully).
                res.setFullJSONData(data);
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray buys = (JSONArray) jsonObject.get("buys");
                JSONArray sells = (JSONArray) jsonObject.get("sells");
                // The following could be refactored.
                for (Object b : buys) {
                    Map<String, Long> bMap = (Map<String, Long>)b;
                    Long listings = bMap.get("listings");
                    Long unitPrice = bMap.get("unit_price");
                    Long quantity = bMap.get("quantity");
                    Long [] arr = new Long[2];
                    arr[0] = listings;
                    arr[1] = quantity;
                    res.getBuys().put(unitPrice, arr);
                }
                for (Object b : sells) {
                    Map<String, Long> bMap = (Map<String, Long>)b;
                    Long listings = bMap.get("listings");
                    Long unitPrice = bMap.get("unit_price");
                    Long quantity = bMap.get("quantity");
                    Long [] arr = new Long[2];
                    arr[0] = listings;
                    arr[1] = quantity;
                    res.getSells().put(unitPrice, arr);
                }
                EntityUtils.consume(content);
            } finally {
                rep.close();
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new org.json.simple.parser.ParseException(0);
        }
        return res;
    }
    
}
