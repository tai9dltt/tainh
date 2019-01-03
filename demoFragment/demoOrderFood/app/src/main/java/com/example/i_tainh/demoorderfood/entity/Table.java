package com.example.i_tainh.demoorderfood.entity;

public class Table {
    private int tableID;
    private String tableName;
    private boolean clicked;

    public Table() {
    }

    public Table(int tableID, String tableName) {
        this.tableID = tableID;
        this.tableName = tableName;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
