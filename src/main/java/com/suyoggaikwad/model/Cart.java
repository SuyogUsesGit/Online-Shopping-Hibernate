package com.suyoggaikwad.model;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@IdClass(Composite.class)
public class Cart {

    @Id
    @Column(name = "user_id")
    private int userID;

    @Id
    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_quantity")
    private int quantity;

    @Column(name = "item_price")
    private double price;

    public Cart() {
    }

    public Cart(int userID, String itemName, int quantity, double price) {
        this.userID = userID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
