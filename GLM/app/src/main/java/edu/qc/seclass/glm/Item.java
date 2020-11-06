package edu.qc.seclass.glm;

public class Item {
    private int itemID;
    private String itemName;
    private String itemTypeName;

    public Item(int itemID, String itemName, String itemTypeName) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemTypeName = itemTypeName;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }



    @Override
    public String toString() {
        return "Item{" +
                "itemID=" + itemID +
                ", itemName=" + itemName +
                ", itemTypeName=" + itemTypeName +
                '}';
    }
}
