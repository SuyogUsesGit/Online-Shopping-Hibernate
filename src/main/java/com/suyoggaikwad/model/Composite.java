package com.suyoggaikwad.model;

import java.io.Serializable;


public class Composite implements Serializable {
    private int userID;
    private String itemName;

    public Composite() {

    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Composite composite = (Composite) o;
//        return userID == composite.userID &&
//                Objects.equals(itemName, composite.itemName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userID, itemName);
//    }
}
