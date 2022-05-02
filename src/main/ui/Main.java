package ui;

import java.io.FileNotFoundException;

// EFFECTS: runs the budget app and prints exceptions if no transaction list is saved
public class Main {
    public static void main(String[] args) {
        try {
            new BudgetAppGUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
