/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tamkien
 */
public class FoodStorage {
    String familyID;
    float daysLeft;

    public FoodStorage() {
    }

    public String getFamilyID() {
        return familyID;
    }

    public void setFamilyID(String familyID) {
        this.familyID = familyID;
    }

    public float getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(float daysLeft) {
        this.daysLeft = daysLeft;
    }
    
}
