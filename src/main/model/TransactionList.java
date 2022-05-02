package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a list of transactions made
public class TransactionList implements Writable {
    private String name;
    private List<Transaction> list;

    /*
     * EFFECTS: a new empty list of transactions is created
     */
    public TransactionList(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds a transaction to the list
    public void add(Transaction t) {
        list.add(t);

        EventLog.getInstance().logEvent(new Event("You have added a new transaction: " + t));
    }

    public boolean remove(Transaction t) {
        if (list.contains(t)) {
            list.remove(0);
            EventLog.getInstance().logEvent(new Event("You have removed a transaction: " + t));
            return false;
        } else {
            return true;
        }
    }

    // EFFECTS: adds up amount of all transactions
    public double sumAmount() {
        double total = 0;
        for (Transaction t : list) {
            total += t.getAmount();
        }
        return total;
    }

    // EFFECTS: returns size of list
    public int size() {
        return list.size();
    }

    // EFFECTS: checks if transaction is in the list
    public boolean contains(Transaction t) {
        return list.contains(t);
    }

    public List<Transaction> getList() {
        return list;
    }

    // EFFECTS: returns JSON representation of transaction list
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns transactions in this list as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : list) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}


