package ui;

import model.Transaction;
import model.TransactionList;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;

public class BudgetAppGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/transactions.json";
    private TransactionList transactionList;
    private Transaction transaction;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel mainMenu;
    private JPanel transactionListPanel;
    private JPanel transactionPanel;
    private JPanel amountSpentPanel;

    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JButton addTransaction;

    private JTextField t1;
    private JTextField t2;
    private JFormattedTextField t3;
    private JTextField t4;

    private JLabel vendor;
    private JLabel item;
    private JLabel date;
    private JLabel amount;
    private JLabel totalAmount;
    private JLabel transactions;

    // Makes a new JFrame with different attributes
    public BudgetAppGUI() throws FileNotFoundException {
        super("Budget App");

        transactionList = new TransactionList("My Transaction List");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));
        makeSplashScreen();
        initializeMenu();
        makeEnterTransactionPanel();
        makeTransactionListPanel();
        makeAmountSpentPanel();

        JLabel welcomeLabel = new JLabel("Welcome to Budget App");
        JLabel mainScreenImage = new JLabel();
        addLabel(welcomeLabel);
        addImageToLabel(mainScreenImage);
        initializeMenuButtons();

        addButtons(b1, b2, b3, b4, b5, b6);
        addActionToButton();

        mainMenu.setVisible(true);
    }

    // EFFECTS: Makes the main menu panel and changes the background color
    public void initializeMenu() {
        mainMenu = new JPanel();
        mainMenu.setBackground(Color.gray);
        add(mainMenu);
        transactions = new JLabel("You have no current transactions", SwingConstants.CENTER);
    }

    // EFFECTS: makes the splash screen
    public void makeSplashScreen() {
        JWindow splashScreen = new JWindow();
        splashScreen.getContentPane().add(new JLabel("", new ImageIcon("./data/loading-bar.gif"),
                SwingConstants.CENTER));
        splashScreen.setBounds(600, 300, 346, 252);
        splashScreen.setVisible(true);
        try {
            Thread.sleep(8300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        splashScreen.setVisible(false);
        splashScreen.dispose();
    }

    // EFFECTS: Initializes main menu buttons and gives them labels
    public void initializeMenuButtons() {
        b1 = new JButton("View your transactions");
        b2 = new JButton("Add a transaction");
        b3 = new JButton("Remove most recent transaction");
        b4 = new JButton("Save");
        b5 = new JButton("Load");
        b6 = new JButton("Exit application");
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to main menu
    public void addButton(JButton b1, JPanel panel) {
        b1.setFont(new Font("Serif", Font.BOLD, 12));
        b1.setBackground(Color.white);
        panel.add(b1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: utilises addButton to add buttons to main menu
    public void addButtons(JButton b1, JButton b2, JButton b3, JButton b4, JButton b5, JButton b6) {
        addButton(b1, mainMenu);
        addButton(b2, mainMenu);
        addButton(b3, mainMenu);
        addButton(b4, mainMenu);
        addButton(b5, mainMenu);
        addButton(b6, mainMenu);
    }

    // EFFECTS: creates label to welcome user in the main menu
    public void addLabel(JLabel j1) {
        j1.setFont(new Font("Serif", Font.BOLD, 46));
        mainMenu.add(j1);
    }

    // EFFECTS: adds image to main menu
    public void addImageToLabel(JLabel j1) {
        j1.setIcon(new ImageIcon("./data/by0qeE.jpg"));
        mainMenu.add(j1);
    }

    // MODIFIES: this
    // EFFECTS: adds actions to each button
    public void addActionToButton() {
        b1.addActionListener(this);
        b1.setActionCommand("View your transactions");
        b2.addActionListener(this);
        b2.setActionCommand("Add a transaction");
        b3.addActionListener(this);
        b3.setActionCommand("Remove a transaction");
        b4.addActionListener(this);
        b4.setActionCommand("Save");
        b5.addActionListener(this);
        b5.setActionCommand("Load");
        b6.addActionListener(this);
        b6.setActionCommand("Exit application");
    }

    // EFFECTS: performs each method when buttons are clicked
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(("View your transactions"))) {
            initializeTransactionListPanel();
        } else if (ae.getActionCommand().equals("Add a transaction")) {
            initializeTransactionsPanel();
        } else if (ae.getActionCommand().equals("Add a transaction to the list")) {
            addTransactionToList();
        } else if (ae.getActionCommand().equals("Remove a transaction")) {
            removeTransactionFromList(transaction);
        } else if (ae.getActionCommand().equals("Check amount spent")) {
            initializeAmountSpentPanel();
        } else if (ae.getActionCommand().equals("Order date in descending order")) {
            descendingOrder();
        } else if (ae.getActionCommand().equals("Order date in ascending order")) {
            ascendingOrder();
        } else if (ae.getActionCommand().equals("Save")) {
            saveTransactionList();
        } else if (ae.getActionCommand().equals("Load")) {
            loadTransactions();
        } else if (ae.getActionCommand().equals("Return to main menu")) {
            returnToMainMenu();
        } else if (ae.getActionCommand().equals("Exit application")) {
            printLog(EventLog.getInstance());
        }
    }

    // MODIFIES: this
    // EFFECTS: creates panel allowing users to input transaction
    public void makeEnterTransactionPanel() {
        transactionPanel = new JPanel(new GridLayout(0, 2));
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addButton(mainMenuButton, transactionPanel);

        createTransactionsPage();
        addLabelsToTransactions();
    }

    // EFFECTS: adds transaction entering page to screen
    public void initializeTransactionsPanel() {
        add(transactionPanel);
        transactionPanel.setVisible(true);
        mainMenu.setVisible(false);
        transactionListPanel.setVisible(false);
        amountSpentPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates the list to add transactions with ability to enter fields for the transaction
    public void createTransactionsPage() {
        addTransaction = new JButton("Add a transaction to the list");
        addTransaction.setActionCommand("Add a transaction to the list");
        addTransaction.addActionListener(this);

        vendor = new JLabel("Vendor:");
        t1 = new JTextField();
        item = new JLabel("Item:");
        t2 = new JTextField();
        date = new JLabel("Date:");
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        t3 = new JFormattedTextField(df);
        amount = new JLabel("Amount:");
        t4 = new JTextField();

        labelSettings();
    }

    // EFFECTS: adds user input labels on panel
    public void addLabelsToTransactions() {
        transactionPanel.add(addTransaction);
        transactionPanel.add(vendor);
        transactionPanel.add(t1);
        transactionPanel.add(item);
        transactionPanel.add(t2);
        transactionPanel.add(date);
        transactionPanel.add(t3);
        transactionPanel.add(amount);
        transactionPanel.add(t4);
    }

    // EFFECTS: changes certain attributes of labels and text fields
    private void labelSettings() {
        addTransaction.setBackground(Color.BLACK);
        addTransaction.setFont(new Font("Serif", Font.BOLD, 12));

        vendor.setFont(new Font("Serif", Font.BOLD, 24));
        item.setFont(new Font("Serif", Font.BOLD, 24));
        date.setFont(new Font("Serif", Font.BOLD, 24));
        amount.setFont(new Font("Serif", Font.BOLD, 24));

        t1.setMaximumSize(new Dimension(1200, 400));
        t2.setMaximumSize(new Dimension(1200, 400));
        t3.setMaximumSize(new Dimension(1200, 400));
        t4.setMaximumSize(new Dimension(1200, 400));
    }

    // EFFECTS: adds transaction to list
    public void addTransactionToList() {
        try {
            transaction = new Transaction(t1.getText(), t2.getText(), t3.getText(), Integer.parseInt(t4.getText()));
            transactionList.add(transaction);
            clearFields();
            Collections.sort(transactionList.getList());
            StringBuilder print = new StringBuilder();
            for (Transaction tt : transactionList.getList()) {
                print.append(tt + "<br/>");
            }
            transactions.setText("<html>Current Transactions: <br/>" + print + "</html>");
            totalAmount.setText("Amount Spent: $" + String.format("%.2f", transactionList.sumAmount()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please try again");
        } catch (IndexOutOfBoundsException e) {
            transactions.setText("Please initialize transactions file before proceeding");
        }
    }

    public void removeTransactionFromList(Transaction t) {
        try {
            transactionList.remove(t);
            StringBuilder print = new StringBuilder();
            for (Transaction tt : transactionList.getList()) {
                print.append(tt + "<br/>");
            }
            transactions.setText("<html>Current Transactions: <br/>" + print + "</html>");
            totalAmount.setText("Amount Spent: $" + String.format("%.2f", transactionList.sumAmount()));
        } catch (NullPointerException e) {
            System.out.println("Please enter transaction before removing it");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please initialize transaction list file before proceeding");
        }
    }

    // EFFECTS: clears all text fields after you add transaction
    private void clearFields() {
        t1.setText(null);
        t2.setText(null);
        t3.setText(null);
        t4.setText(null);
    }

    // EFFECTS: creates the window with transaction list
    public void makeTransactionListPanel() {
        transactionListPanel = new JPanel(new GridLayout(5, 1));
        JScrollPane scroll = new JScrollPane(transactions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addButton(mainMenuButton, transactionListPanel);
        JButton amountSpentButton = new JButton("Check Amount Spent");
        amountSpentButton.setActionCommand("Check amount spent");
        amountSpentButton.addActionListener(this);
        addButton(amountSpentButton, transactionListPanel);
        JButton descendingOrder = new JButton("Order Date in Descending Order");
        descendingOrder.setActionCommand("Order date in descending order");
        descendingOrder.addActionListener(this);
        addButton(descendingOrder, transactionListPanel);
        JButton ascendingOrder = new JButton("Order Date in Ascending Order");
        ascendingOrder.setActionCommand("Order date in ascending order");
        ascendingOrder.addActionListener(this);
        addButton(ascendingOrder, transactionListPanel);

        transactions.setFont(new Font("Serif", Font.BOLD, 12));
        transactionListPanel.add(scroll);
    }

    // EFFECTS: sets window to transaction list
    public void initializeTransactionListPanel() {
        add(transactionListPanel);
        transactionListPanel.setVisible(true);
        mainMenu.setVisible(false);
        transactionPanel.setVisible(false);
        amountSpentPanel.setVisible(false);
    }

    // EFFECTS: creates the window with amount spent by user
    public void makeAmountSpentPanel() {
        amountSpentPanel = new JPanel(new GridLayout(2, 1));
        JButton transactionListButton = new JButton("Return to Transactions");
        transactionListButton.setActionCommand("View your transactions");
        transactionListButton.addActionListener(this);
        addButton(transactionListButton, amountSpentPanel);

        createAmountSpentPage();
        amountSpentPanel.add(totalAmount);
    }

    // EFFECTS: sets window to check amount spent
    public void initializeAmountSpentPanel() {
        add(amountSpentPanel);
        transactionListPanel.setVisible(false);
        mainMenu.setVisible(false);
        transactionPanel.setVisible(false);
        amountSpentPanel.setVisible(true);
    }

    // EFFECTS: creates amount spent page
    public void createAmountSpentPage() {
        totalAmount = new JLabel("Amount Spent: " + "$" + "0.00", SwingConstants.CENTER);
        totalAmount.setFont(new Font("Serif", Font.BOLD, 18));
    }

    // EFFECTS: orders date in descending order
    public void descendingOrder() {
        Collections.sort(transactionList.getList(), Collections.reverseOrder());
        StringBuilder print = new StringBuilder();
        for (Transaction tt : transactionList.getList()) {
            print.append(tt + "<br/>");
        }
        transactions.setText("<html>Current Transactions: <br/>" + print + "</html>");
    }

    // EFFECTS: orders date in descending order
    public void ascendingOrder() {
        Collections.sort(transactionList.getList());
        StringBuilder print = new StringBuilder();
        for (Transaction tt : transactionList.getList()) {
            print.append(tt + "<br/>");
        }
        transactions.setText("<html>Current Transactions: <br/>" + print + "</html>");
    }

    // MODIFIES: this
    // EFFECTS: loads the transaction list from file
    private void loadTransactions() {
        try {
            transactionList = jsonReader.read();
            Collections.sort(transactionList.getList());
            StringBuilder print = new StringBuilder();
            for (Transaction tt : transactionList.getList()) {
                print.append(tt + "<br/>");
            }
            transactions.setText("<html>Current Transactions: <br/>" + print + "</html>");
            totalAmount.setText("Amount Spent: $" + String.format("%.2f", transactionList.sumAmount()));
            System.out.println("Loaded " + transactionList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please initialize transactions first");
        } catch (NullPointerException e) {
            System.out.println("No transactions to load");
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
        } catch (NullPointerException e) {
            System.out.println("Please load the file before saving");
        }
    }

    // EFFECTS: returns user to main menu
    public void returnToMainMenu() {
        clearFields();
        mainMenu.setVisible(true);
        transactionListPanel.setVisible(false);
        transactionPanel.setVisible(false);
        amountSpentPanel.setVisible(false);
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
        System.exit(0);
    }
}


