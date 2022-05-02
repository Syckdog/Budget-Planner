package persistence;

import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransaction(String item, String vendor, String date, double amount, Transaction transaction) {
        assertEquals(item, transaction.getItem());
        assertEquals(vendor, transaction.getVendor());
        assertEquals(date, transaction.getDate());
        assertEquals(amount, transaction.getAmount());
    }
}
