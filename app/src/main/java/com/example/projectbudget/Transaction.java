package com.example.projectbudget;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Transactions")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "transactionID")
    private Long ID;

    @ColumnInfo(name = "date")
    private Date date;          // Change to a month object?

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "cost")
    private double cost;

   /* @ColumnInfo(name = "category")
    private String category; */   //Change this to a category object NOT NECESSARY IN FIRST PRODUCTION

    @ColumnInfo(name = "description")
    private String description;

    public Transaction(String name){
        this.name = name;
    }

    public Transaction(String name, double amount, String description, Date date){
        this(name);
        this.name = name;
        this.cost = amount;
        this.description = description;
        this.date = date;
    }

    public Long getID(){
        return ID;
    }

    public double getCost(){
        return cost;
    }

    public void setCost(double cost){
        this.cost = cost;
    }

    /*public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }*/

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDate(Date date){this.date = date;}

    public Date getDate(){
        return this.date;
    }

    public void setID(@NonNull Long id){
        this.ID = id;
    }

    public void setName(String name){this.name = name;}

    public String getName(){return name;}
}
