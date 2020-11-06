package edu.qc.seclass.glm;
import androidx.annotation.NonNull;

public class UserListItem {
    private int userListItemsID;
    private int isChecked;
    private String itemQuantity;
    private String itemName;

    public UserListItem(int userListItemsID, int isChecked, String itemQuantity, String itemName ) {
        this.userListItemsID = userListItemsID;
        this.isChecked = isChecked;
        this.itemQuantity = itemQuantity;
        this.itemName = itemName;
    }


    public int getUserListItemsID() {
        return userListItemsID;
    }

    public void setUserListItemsID(int userListItemsID) {
        this.userListItemsID = userListItemsID;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
