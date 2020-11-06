package edu.qc.seclass.glm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import edu.qc.seclass.glm.Item;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    public static String DATABASE_NAME = "UserListAppGLM.db";
    private static DatabaseHandler mInstance = null;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Singletone
    public static DatabaseHandler getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHandler(ctx.getApplicationContext());
        }
        return mInstance;
    }

    // creation of ItemType table
    private static final String TABLE_ITEM_TYPE = "itemType";
    private static final String COLUMN_ITEM_TYPE_ID = "itemID";
    private static final String COLUMN_ITEM_TYPE_NAME = "itemTypeName";
    private static final String CREATE_TABLE_ITEM_TYPE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ITEM_TYPE + "("
            + COLUMN_ITEM_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ITEM_TYPE_NAME + " TEXT"
            + ")" ;

    // creation of UserList table
    private static final String  TABLE_USER_LIST = "UserList";
    private static final String  COLUMN_USER_LIST_ID = "UserListID";
    private static final String  COLUMN_USER_LIST_NAME = "UserListName";
    //SQLite create table for UserList
    private static final String CREATE_TABLE_USER_LIST = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER_LIST + "("
            + COLUMN_USER_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_LIST_NAME + " TEXT"
            + ")" ;


    // creation of Item table
    private static final String TABLE_ITEM = "Item";
    private static final String COLUMN_ITEM_ID = "itemID";
    private static final String COLUMN_ITEM_NAME = "itemName";

    // type name :  Banana , category: Fruit
    private static final String CREATE_TABLE_ITEM = "CREATE TABLE IF NOT EXISTS "
            + TABLE_ITEM + "("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ITEM_NAME + " TEXT, "
            + COLUMN_ITEM_TYPE_NAME + " TEXT"
            + ")" ;


    // creation of UserListItems table
    private static final String TABLE_USER_LIST_ITEMS = "userListItems";
    private static final String COLUMN_USER_LIST_ITEMS_ID = "userListItemsID";
    private static  final String COLUMN_USER_LIST_ITEMS_IS_CHECKED = "isChecked";
    private static final String COLUMN_USER_LIST_ITEMS_QUANTITY = "itemQuantity";
    private static final String COLUMN_ITEM_FK = "itemFK";

    private static final String CREATE_TABLE_USER_LIST_ITEMS = " CREATE TABLE IF NOT EXISTS "
            + TABLE_USER_LIST_ITEMS + "("
            + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_LIST_ITEMS_ID + " INTEGER, "
            + COLUMN_USER_LIST_ITEMS_IS_CHECKED + " INTEGER DEFAULT 0, "
            + COLUMN_USER_LIST_ITEMS_QUANTITY + " INTEGER, "
            + COLUMN_USER_LIST_ID + " INTEGER, "
            + COLUMN_ITEM_ID + " INTEGER "
            + ")" ;


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println();
        try{
            db.execSQL(CREATE_TABLE_USER_LIST);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            db.execSQL(CREATE_TABLE_ITEM);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            db.execSQL(CREATE_TABLE_ITEM_TYPE);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            db.execSQL(CREATE_TABLE_USER_LIST_ITEMS);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        populateItemList(db, "Apple", "Produce");
        populateItemList(db, "Banana", "Produce");
        populateItemList(db, "Strawberry", "Produce");
        populateItemList(db, "Blueberry", "Produce");
        populateItemList(db, "Peach", "Produce");
        populateItemList(db, "Avocado", "Produce");
        populateItemList(db, "Lemon", "Produce");
        populateItemList(db, "Ground Beef", "Meat");
        populateItemList(db, "Sirloin Steak", "Meat");
        populateItemList(db, "Beef Burger Patties", "Meat");
        populateItemList(db, "Lamb Chops", "Meat");
        populateItemList(db, "Whole Chicken", "Meat");
        populateItemList(db, "Whole Milk", "Dairy");
        populateItemList(db, "Eggs", "Dairy");
        populateItemList(db, "Cream Cheese Spread", "Dairy");
        populateItemList(db, "Cheddar", "Dairy");
        populateItemList(db, "Strawberry Yogurt", "Dairy");
        populateItemList(db, "Chocolate Milk", "Dairy");
        populateItemList(db, "White Bread", "Bakery");
        populateItemList(db, "Whole Wheat Bread", "Bakery");
        populateItemList(db, "Bagels", "Bakery");
        populateItemList(db, "Naan Bread", "Bakery");
        populateItemList(db, "Cheesecake", "Bakery");
        populateItemList(db, "Croissants", "Bakery");
        populateItemList(db, "Black beans", "Grains");
        populateItemList(db, "Chickpeas", "Grains");
        populateItemList(db, "White Rice", "Grains");
        populateItemList(db, "Basmati Rice", "Grains");
        populateItemList(db, "Risotto", "Grains");
        populateItemList(db, "Spagetti", "Grains");
        populateItemList(db, "Waffles", "Frozen");
        populateItemList(db, "Ice Cream", "Frozen");
        populateItemList(db, "Pancakes", "Frozen");
        populateItemList(db, "Frozen Pizza", "Frozen");
        populateItemList(db, "Chicken Nuggets", "Frozen");
        populateItemList(db, "Tater Tots", "Frozen");
        populateItemList(db, "Onion Rings", "Frozen");
        populateItemList(db, "Water Bottles", "Beverages");
        populateItemList(db, "Apple Juice", "Beverages");
        populateItemList(db, "Orange Juice", "Beverages");
        populateItemList(db, "Soda cans", "Beverages");
        populateItemList(db, "Chamomile tea bags", "Beverages");
        populateItemList(db, "Ground coffee", "Beverages");
        populateItemList(db, "Sports Drink", "Beverages");
        populateItemList(db, "Grape Juice", "Beverages");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS ' " +  TABLE_USER_LIST + " ' ");
        db.execSQL(" DROP TABLE IF EXISTS ' " +  TABLE_ITEM + " ' ");
        db.execSQL(" DROP TABLE IF EXISTS ' " +  TABLE_ITEM_TYPE + " ' ");
        db.execSQL(" DROP TABLE IF EXISTS ' " +  TABLE_USER_LIST_ITEMS + " ' ");
    }

    public int deleteUserListByUserListId(int listID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER_LIST, COLUMN_USER_LIST_ID + "= ? ",
                new String[] { String.valueOf(listID)});

    }

    public int deleteAllUserLists(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USER_LIST, null, null);

    }


    public int updateUserListName(int listID, String  newListName ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_LIST_NAME, newListName);
        return db.update(TABLE_USER_LIST, values, COLUMN_USER_LIST_ID + " = ? ",
                new String[] {String.valueOf(listID)});

    }


    public long populateItemList(SQLiteDatabase db, String itemName, String itemType){
        ContentValues insertValues = new ContentValues();
        insertValues.put(COLUMN_ITEM_NAME, itemName);
        insertValues.put(COLUMN_ITEM_TYPE_NAME, itemType);

        return  db.insert(TABLE_ITEM , null, insertValues);
    }

    public long insertItemInDb(String itemName, String itemType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put(COLUMN_ITEM_NAME, itemName);
        insertValues.put(COLUMN_ITEM_TYPE_NAME, itemType);

        return  db.insert(TABLE_ITEM , null, insertValues);
    }

    public boolean deleteAllcheckeditems(int userListid){
        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = COLUMN_USER_LIST_ID + "=" + userListid
                    + " AND "
                    + COLUMN_USER_LIST_ITEMS_IS_CHECKED + "=" + "1";
        return db.delete(TABLE_USER_LIST_ITEMS, whereClause , null) > 0;
    }

    public long insertUserList(String listName){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put(COLUMN_USER_LIST_NAME, listName);
        return db.insert(TABLE_USER_LIST , null, insertValues);
    }


    public ArrayList<UserList> getAllUser() {

        ArrayList<UserList> items = new ArrayList<>();

        String rawSQL = "SELECT * "
                + " FROM " + TABLE_USER_LIST;
                //+ " ORDER BY " + COLUMN_USER_LIST_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(rawSQL, null);

        if (c.moveToFirst()) {
            do {
                items.add(new UserList(
                        c.getInt(c.getColumnIndex(COLUMN_USER_LIST_ID)),
                        c.getString(c.getColumnIndex(COLUMN_USER_LIST_NAME))
                ));
            } while (c.moveToNext());
        }

        c.close();

        return items;

    }

    public ArrayList<Item> getAllItems(String searchString) {

        ArrayList<Item> items = new ArrayList<>();

        String rawSQL = "SELECT * "
                + " FROM " + TABLE_ITEM;

        if(searchString != null){
            rawSQL += " WHERE " + COLUMN_ITEM_NAME
                    + " LIKE " +"'" + searchString + "%" +"'";
        }

        rawSQL += " ORDER BY " + COLUMN_ITEM_TYPE_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(rawSQL, null);

        if (c.moveToFirst()) {
            do {
                items.add(new Item(
                        c.getInt(c.getColumnIndex(COLUMN_ITEM_ID)),
                        c.getString(c.getColumnIndex(COLUMN_ITEM_NAME)),
                        c.getString(c.getColumnIndex(COLUMN_ITEM_TYPE_NAME))
                ));
            } while (c.moveToNext());
        }

        c.close();

        db.close();

        return items;

    }


    public ArrayList<UserListItem> getUserListByUserListId(int userListId) {

        ArrayList<UserListItem> userListItems = new ArrayList<>();
        String itemNameColumnName = "itemName";

        String rawSQL = "SELECT  "
                + COLUMN_USER_LIST_ITEMS_ID + " ,"
                + COLUMN_USER_LIST_ITEMS_IS_CHECKED + " ,"
                + COLUMN_USER_LIST_ITEMS_QUANTITY + " ,"
                + COLUMN_USER_LIST_ITEMS_IS_CHECKED + " ,"
                + TABLE_ITEM + "."+COLUMN_ITEM_NAME  + " AS " + itemNameColumnName
                + " FROM " + TABLE_USER_LIST_ITEMS
                + " JOIN  " + TABLE_ITEM + " "
                + " ON "
                + TABLE_ITEM + "."+COLUMN_ITEM_ID + "="+ TABLE_USER_LIST_ITEMS + "." + COLUMN_USER_LIST_ITEMS_ID
                + " WHERE " + COLUMN_USER_LIST_ID + "=" + userListId;



        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(rawSQL, null);

        if (c.moveToFirst()) {
            do {
                userListItems.add(new UserListItem(
                        c.getInt(c.getColumnIndex(COLUMN_USER_LIST_ITEMS_ID)),
                        c.getInt(c.getColumnIndex(COLUMN_USER_LIST_ITEMS_IS_CHECKED)),
                        c.getString(c.getColumnIndex(COLUMN_USER_LIST_ITEMS_QUANTITY)),
                        c.getString(c.getColumnIndex(itemNameColumnName))
                        ));
            } while (c.moveToNext());
        }

        c.close();

        db.close();

        return userListItems;

    }

    public void incrementItemQuantityInUserList(int itemId , int userListId, int quantity ){
        SQLiteDatabase db = this.getReadableDatabase();
        quantity = quantity +1;
        String rawSQL = " Update "
                        + TABLE_USER_LIST_ITEMS
                        +" SET "
                        + COLUMN_USER_LIST_ITEMS_QUANTITY + "="+quantity
                        + " where "
                        + COLUMN_USER_LIST_ITEMS_ID + "=" + itemId
                        + " AND "
                        + COLUMN_USER_LIST_ID + "=" + userListId;
        System.out.println(rawSQL);
        Cursor c = db.rawQuery(rawSQL, null);

        if(c.moveToFirst()){
            do{
               System.out.println("incremented");
            }while(c.moveToNext());
        }

    }

    public void toggleCheckBoxinUserList(int userListItemId, int checkValue){
       // UPDATE some_table SET an_int_value = IF(an_int_value=1, 0, 1)
        String rawSQL =" UPDATE " +
                TABLE_USER_LIST_ITEMS +
                " SET " +
                COLUMN_USER_LIST_ITEMS_IS_CHECKED +
                " = " +
                checkValue+
                " WHERE "
                + COLUMN_USER_LIST_ITEMS_ID + "=" + userListItemId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(rawSQL, null);

        if(c.moveToFirst()){
            do{
                System.out.println("Toggled");
            }while(c.moveToNext());
        }
        c.close();
    }


    public int getQuantityByItem(int itemId ,int userListId) {
        int quantity = 0;
        String itemNameColumnName = "itemName";

        String rawSQL = "SELECT  "
                + COLUMN_USER_LIST_ITEMS_QUANTITY
                + " FROM " + TABLE_USER_LIST_ITEMS
                + " JOIN  " + TABLE_ITEM + " "
                + " ON "
                + TABLE_ITEM + "."+COLUMN_ITEM_ID + "="+ TABLE_USER_LIST_ITEMS + "." + COLUMN_USER_LIST_ITEMS_ID
                + " WHERE " + COLUMN_USER_LIST_ID + "=" + userListId
                + " AND "
                + COLUMN_USER_LIST_ITEMS_ID + "=" + itemId;

        System.out.println(rawSQL);


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(rawSQL, null);

        if (c.moveToFirst()) {
            do {
                quantity = c.getInt(c.getColumnIndex(COLUMN_USER_LIST_ITEMS_QUANTITY));
            } while (c.moveToNext());
        }

        c.close();

        db.close();

        return quantity;

    }
    public long insertUserListItem(String userListItemName,int quantity , int itemId , int userListId){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_LIST_ID, userListId);
        contentValues.put(COLUMN_USER_LIST_ITEMS_ID, itemId);
        contentValues.put(COLUMN_USER_LIST_ITEMS_QUANTITY, quantity);
        System.out.println("user List id");
        System.out.println(userListId);

        return db.insert(TABLE_USER_LIST_ITEMS , null, contentValues);
    }

}

