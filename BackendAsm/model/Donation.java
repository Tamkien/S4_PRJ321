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
public class Donation {
    String donatorName, amount, donationID, donatorAddress;

    public Donation() {
    }

    public String getDonatorAddress() {
        return donatorAddress;
    }

    public void setDonatorAddress(String donatorAddress) {
        this.donatorAddress = donatorAddress;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDonationID() {
        return donationID;
    }

    public void setDonationID(String donationID) {
        this.donationID = donationID;
    }
    
}
