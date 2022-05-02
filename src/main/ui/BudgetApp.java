package ui;

import model.Transaction;
import model.TransactionList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// Budget planner application
public class BudgetApp {
    private static final String JSON_STORE = "./data/transactions.json";
    private TransactionList transactionList;
    Scanner scanner = new Scanner(System.in);
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the budget planner application
    public BudgetApp() throws FileNotFoundException {
        transactionList = new TransactionList("My Transaction List");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // taken from TellerApp
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThank you for budgeting with us!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doSum();
        } else if (command.equals("p")) {
            doTransactionList();
        } else if (command.equals("t")) {
            doTransaction();
        } else if (command.equals("b")) {
            doBalance();
        } else if (command.equals("s")) {
            saveTransactionList();
        } else if (command.equals("l")) {
            loadTransactionList();
        } else {
            System.out.println("Please pick one of the given options");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> add a new transaction");
        System.out.println("\tp -> see all of my transactions");
        System.out.println("\ta -> see how much I have spent in total");
        System.out.println("\tb -> input starting amount and check balance");
        System.out.println("\ts -> save transaction list to file");
        System.out.println("\tl -> load transaction list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: takes in a starting balance and prints out how much money is left after transactions
    private void doBalance() {
        System.out.println("Enter your starting balance");
        double startingBalance = scanner.nextDouble();
        while (startingBalance <= 0) {
            System.out.println("Sorry, but your starting balance must be positive");
            System.out.println("Enter your starting balance");
            startingBalance = scanner.nextDouble();
        }
        System.out.println("Your net balance is $" + String.format("%.2f", calculateBalance(startingBalance)));
    }

    // MODIFIES: this
    // EFFECTS: adds up transaction amounts
    private void doSum() {
        System.out.println("You have spent $" + String.format("%.2f", transactionList.sumAmount()));
    }

    // MODIFIES: this
    // EFFECTS: shows all transactions made
    private void doTransactionList() {
        for (Transaction t: transactionList.getList()) {
            System.out.println(t);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter details about transaction
    private void doTransaction() {
        System.out.println("What is your purchase?");
        String item = scanner.nextLine();
        System.out.println("Where did you purchase this?");
        String vendor = scanner.nextLine();
        System.out.println("When did you purchase this?");
        System.out.println("Enter a date in dd/mm/yyyy format");
        String date = scanner.nextLine();
        while (!dateValidation(date)) {
            System.out.println("Date is invalid");
            System.out.println("Enter a date in dd/mm/yyyy format");
            date = scanner.nextLine();
        }
        System.out.println("How much was the transaction?");
        double amount = scanner.nextDouble();
        while (amount <= 0) {
            System.out.println("Sorry, but your price must be positive");
            amount = scanner.nextDouble();
        }
        scanner.nextLine();
        addTransaction(item, vendor, date, amount);
    }

    // EFFECTS: adds transaction to list and prints out transaction made
    public void addTransaction(String item, String vendor, String date, double amount) {
        Transaction t = new Transaction(item, vendor, date, amount);
        transactionList.add(t);
        System.out.println("You spent $" + String.format("%.2f", t.getAmount()) + " on "
                + t.capitalizeFirstLetter(t.getItem()) + " from " + t.capitalizeFirstLetter(t.getVendor())
                + " on " + t.getDate());
    }

    // EFFECTS: calculates balance after transactions added
    public double calculateBalance(double balance) {
        balance -= transactionList.sumAmount();
        return balance;
    }

    // EFFECTS: returns true if date provided is given in dd/mm/yyyy format and false otherwise
    private static boolean dateValidation(String date) {
        boolean status = false;
        if (checkDate(date)) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(date);
                status = true;
            } catch (Exception e) {
                status = false;
            }
        }
        return status;
    }

    // EFFECTS: returns true if date is given in right pattern returns false otherwise
    static boolean checkDate(String date) {
        String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
        boolean flag = false;
        if (date.matches(pattern)) {
            flag = true;
        }
        return flag;
    }

    // MODIFIES: this
    // EFFECTS: loads transaction list from file
    private void loadTransactionList() {
        try {
            transactionList = jsonReader.read();
            System.out.println("Loaded " + transactionList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the transaction list to file
    private void saveTransactionList() {
        try {
            jsonWriter.open();
            jsonWriter.write(transactionList);
            jsonWriter.close();
            System.out.println("Saved " + transactionList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
