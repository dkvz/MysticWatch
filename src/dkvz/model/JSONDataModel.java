package dkvz.model;

import java.util.*;
import org.json.simple.*;
import java.io.*;
import org.json.simple.parser.*;

/**
 * The data model for the program. This could be an abstraction later on. (Interface or abstract class DataModel) but I
 * try not to lose too much time on things that probably won't ever be useful.
 *
 *
 * @author william
 */
public class JSONDataModel {

    private ArrayList<Item> itemList = new ArrayList<Item>();
    /**
     * Specify default filename for the JSON file here.
     */
    private String filename = "data.json";
    
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_DEMAND = "demand";
    public static final String PROPERTY_SUPPLY = "supply";
    public static final String PROPERTY_LOWESTSELLORDER = "lowestSellOrder";
    public static final String PROPERTY_HIGHESTBUYORDER = "highestBuyOrder";
    public static final String PROPERTY_PROFITFROMDIRECTSELLING = "profitFromDirectSelling";
    public static final String PROPERTY_PROFITFROMSELLORDER = "profitFromSellOrder";
    public static final String PROPERTY_QUANTITY = "qty";
    public static final String PROPERTY_CRAFTINGCOSTHIGH = "craftingCostHigh";
    public static final String PROPERTY_CRAFTINGCOSTLOW = "craftingCostLow";

    public JSONDataModel() {

    }
    
    public void addItem(Item item) {
        if (this.findByID(item.getId()) == null) {
            this.itemList.add(item);
        }
    }
    
    public void removeItem(long id) {
        for (int i = 0; i < this.itemList.size(); i++) {
            if (this.itemList.get(i).getId() == id) {
                this.itemList.remove(i);
                break;
            }
        }
    }
    
    public Item findByID(long id) {
        Item found = null;
        for (Item item : this.itemList) {
            if (item.getId() == id) {
                found = item;
                break;
            }
        }
        return found;
    }
    
    public int count() {
        return this.itemList.size();
    }
    
    // I could have used Map in those return types.
    private HashMap<String, Object> populateItemHashMap(Item item) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put(JSONDataModel.PROPERTY_ID, item.getId());
        props.put(JSONDataModel.PROPERTY_NAME, item.getName());
        props.put(JSONDataModel.PROPERTY_DEMAND, item.getDemand());
        props.put(JSONDataModel.PROPERTY_SUPPLY, item.getOffer());
        props.put(JSONDataModel.PROPERTY_LOWESTSELLORDER, item.getLowestSellOrder());
        props.put(JSONDataModel.PROPERTY_HIGHESTBUYORDER, item.getHighestBuyOrder());
        props.put(JSONDataModel.PROPERTY_PROFITFROMDIRECTSELLING, item.getProfitFromDirectSelling());
        props.put(JSONDataModel.PROPERTY_PROFITFROMSELLORDER, item.getProfitFromSellOrder());
        props.put(JSONDataModel.PROPERTY_CRAFTINGCOSTHIGH, item.getCraftingCostHigh());
        props.put(JSONDataModel.PROPERTY_CRAFTINGCOSTLOW, item.getCraftingCostLow());
        return props;
    }

    private HashMap<String, Object> populateComponentsHashMap(Item item) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put(JSONDataModel.PROPERTY_ID, item.getId());
        props.put(JSONDataModel.PROPERTY_NAME, item.getName());
        props.put(JSONDataModel.PROPERTY_LOWESTSELLORDER, item.getLowestSellOrder());
        props.put(JSONDataModel.PROPERTY_HIGHESTBUYORDER, item.getHighestBuyOrder());
        props.put(JSONDataModel.PROPERTY_QUANTITY, item.getQty());
        return props;
    }

    public void saveData() throws IOException {
        // Save data to file.
        if (this.itemList == null || this.itemList.isEmpty()) {
            // Cannot save in such a situation.
            return;
        }
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        for (Item item : this.itemList) {
            // Must check if the items has components set.

            //if (item.isRefreshed()) {
            // There is something to save since last time (apparently, I'll have to 
            // reset this bool somewhere).
            // OK I'm not using that concept for now, it needs revising anyway.
            HashMap<String, Object> props = this.populateItemHashMap(item);
            if (item.getComponents() != null && !item.getComponents().isEmpty()) {
                // Save the components too.
                // This is starting to get complicated...
                ArrayList<HashMap> compoList = new ArrayList<HashMap>();
                for (Item compo : item.getComponents()) {
                    compoList.add(this.populateComponentsHashMap(compo));
                }
                props.put("components", compoList);
            }
            list.add(props);
            //}
        }
        obj.put("items", list);
        
        FileWriter file = new FileWriter(this.getFilename());
        file.write(obj.toJSONString());
        file.flush();
        file.close();
        
    }

    public void loadData() throws FileNotFoundException, IOException, ParseException {
        // First check if the file exists.
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(this.getFilename()));

        try {
            JSONObject jsonObject = (JSONObject) obj;

            // loop array
            JSONArray itemList = (JSONArray) jsonObject.get("items");
            // We have hash maps inside. Let's check if it works...
            for (Object element : itemList) {
                Map<String, Object> map = (Map<String, Object>)element;
                if (map.get(JSONDataModel.PROPERTY_ID) != null) {
                    Item newItem = new Item();
                    newItem.setId((Long)map.get(JSONDataModel.PROPERTY_ID));
                    if (map.get(JSONDataModel.PROPERTY_NAME) != null) {
                        newItem.setName((String)map.get(JSONDataModel.PROPERTY_NAME));   
                    }
                    if (map.get(JSONDataModel.PROPERTY_DEMAND) != null) {
                        newItem.setDemand((Long)map.get(JSONDataModel.PROPERTY_DEMAND));
                    }
                    if (map.get(JSONDataModel.PROPERTY_SUPPLY) != null) {
                        newItem.setOffer((Long)map.get(JSONDataModel.PROPERTY_SUPPLY));
                    }
                    if (map.get(JSONDataModel.PROPERTY_LOWESTSELLORDER) != null) {
                        newItem.setLowestSellOrder((Double)map.get(JSONDataModel.PROPERTY_LOWESTSELLORDER));
                    }
                    if (map.get(JSONDataModel.PROPERTY_HIGHESTBUYORDER) != null) {
                        newItem.setHighestBuyOrder((Double)map.get(JSONDataModel.PROPERTY_HIGHESTBUYORDER));
                    }
                    if (map.get(JSONDataModel.PROPERTY_PROFITFROMDIRECTSELLING) != null) {
                        newItem.setProfitFromDirectSelling((Double)map.get(JSONDataModel.PROPERTY_PROFITFROMDIRECTSELLING));
                    }
                    if (map.get(JSONDataModel.PROPERTY_PROFITFROMSELLORDER) != null) {
                        newItem.setProfitFromSellOrder((Double)map.get(JSONDataModel.PROPERTY_PROFITFROMSELLORDER));
                    }
                    // We need to get the components:
                    if (map.get("components") != null) {
                        List<Map> compos = (List<Map>)map.get("components");
                        if (compos.size() > 0) {
                            newItem.setComponents(new ArrayList<Item>());
                            for (Map<String, Object> comp : compos) {
                                if (comp.get(JSONDataModel.PROPERTY_ID) != null) {
                                    Item newComp = new Item();
                                    newComp.setId((Long)comp.get(JSONDataModel.PROPERTY_ID));
                                    if (comp.get(JSONDataModel.PROPERTY_NAME) != null) {
                                        newComp.setName((String)comp.get(JSONDataModel.PROPERTY_NAME));   
                                    }
                                    if (comp.get(JSONDataModel.PROPERTY_LOWESTSELLORDER) != null) {
                                        newComp.setLowestSellOrder((Double)comp.get(JSONDataModel.PROPERTY_LOWESTSELLORDER));   
                                    }
                                    if (comp.get(JSONDataModel.PROPERTY_HIGHESTBUYORDER) != null) {
                                        newComp.setHighestBuyOrder((Double)comp.get(JSONDataModel.PROPERTY_HIGHESTBUYORDER));   
                                    }
                                    if (comp.get(JSONDataModel.PROPERTY_QUANTITY) != null) {
                                        Long qty = (Long)comp.get(JSONDataModel.PROPERTY_QUANTITY);
                                        newComp.setQty(qty.intValue());   
                                    }
                                    newComp.setComponent(true);
                                    newItem.getComponents().add(newComp);
                                }
                            }
                        }
                    }
                    this.itemList.add(newItem);
                }
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new ParseException(0);
        }

    }

    /**
     * @return the itemList
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * @param itemList the itemList to set
     */
    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

}
