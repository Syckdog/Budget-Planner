package persistence;

import model.Event;
import model.EventLog;
import model.TransactionList;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// taken and modified from given CPSC210 model of JsonReader
// represents a Json Reader which reads json files
public class JsonReader {
    private static String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads transaction list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public static TransactionList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded Transaction List"));
        return parseTransactionList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses transaction list from JSON object and returns it
    private static TransactionList parseTransactionList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        TransactionList tl = new TransactionList(name);
        addTransactions(tl, jsonObject);
        return tl;
    }

    // MODIFIES: wr
    // EFFECTS: parses transactions from JSON object and adds them to workroom
    private static void addTransactions(TransactionList tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(tl, nextTransaction);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses transaction from JSON object and adds it to workroom
    private static void addTransaction(TransactionList tl, JSONObject jsonObject) {
        String item = jsonObject.getString("item");
        String vendor = jsonObject.getString("vendor");
        String date = jsonObject.getString("date");
        double amount = jsonObject.getDouble("amount");
        Transaction transaction = new Transaction(item, vendor, date, amount);
        tl.add(transaction);
    }
}
