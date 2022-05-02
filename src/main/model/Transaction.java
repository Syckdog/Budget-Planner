package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a transaction having an item, vendor, month, date and amount (in dollars)
public class Transaction implements Writable, Comparable<Transaction> {

    private String item;
    private String vendor;
    private String date;
    private double amount;


     // EFFECTS: transaction is given an item, vendor, month, date and amount (in dollars)
    public Transaction(String item, String vendor, String date, double amount) {
        this.item = item;
        this.vendor = vendor;
        this.date = date;
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    // EFFECTS: capitalizes the first letter of a string
    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    //EFFECTS: returns a string representation of transaction
    @Override
    public String toString() {
        return "Item: " + capitalizeFirstLetter(this.getItem()) + " | Vendor: "
                + capitalizeFirstLetter(this.getVendor()) + " | Date: " + this.getDate() + " | Amount: $"
                + String.format("%.2f", this.getAmount());
    }

    // EFFECTS: returns a JSON representation of transaction
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("item", item);
        json.put("vendor", vendor);
        json.put("date", date);
        json.put("amount", amount);
        return json;
    }

    // EFFECTS: compares to dates
    @Override
    public int compareTo(Transaction t) {
        return getDate().compareTo(t.getDate());
    }
}
