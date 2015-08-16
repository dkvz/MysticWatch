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
    private String filename = "data.json";

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
    
    private HashMap<String, Object> populateItemHashMap(Item item) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("id", item.getId());
        props.put("name", item.getName());
        props.put("demand", item.getDemand());
        props.put("supply", item.getOffer());
        props.put("lowestSellOrder", item.getLowestSellOrder());
        props.put("highestBuyOrder", item.getHighestBuyOrder());
        props.put("profitFromDirectSelling", item.getProfitFromDirectSelling());
        props.put("profitFromSellOrder", item.getProfitFromSellOrder());
        return props;
    }

    private HashMap<String, Object> populateComponentsHashMap(Item item) {
        HashMap<String, Object> props = new HashMap<String, Object>();
        props.put("id", item.getId());
        props.put("name", item.getName());
        props.put("lowestSellOder", item.getLowestSellOrder());
        props.put("highestBuyOrder", item.getHighestBuyOrder());
        props.put("qty", item.getQty());
        return props;
    }

    public void saveData() {
        // Save data to file.
        if (this.itemList == null || this.itemList.isEmpty()) {
            // Cannot save in such a situation.
            return;
        }
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        for (Item item : this.itemList) {
            // Must check if the items has components set.

            if (item.isRefreshed()) {
                // There is something to save since last time (apparently, I'll have to 
                // reset this bool somewhere).
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
            }
        }
        obj.put("items", list);

        try {
            FileWriter file = new FileWriter(this.getFilename());
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() throws FileNotFoundException, IOException, ParseException {
        // First check if the file exists.
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(this.getFilename()));

        JSONObject jsonObject = (JSONObject) obj;

        // loop array
        JSONArray msg = (JSONArray) jsonObject.get("items");
        Iterator<String> iterator = msg.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
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
