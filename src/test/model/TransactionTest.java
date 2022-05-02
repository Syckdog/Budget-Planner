package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private static final String random = "tea";
    private static final String empty = "";
    private static final String valid = "14/12/2022";
    private static final String invalid = "0";
    private static final double positive = 14.50;
    private static final double negative = -14.50;

    private Transaction t1;
    private Transaction t2;
    private Transaction t3;
    private Transaction t4;

    @BeforeEach
    public void beforeRun() {
        t1 = new Transaction(random, random, valid, positive);
        t2 = new Transaction(empty, empty, invalid, negative);
        t3 = new Transaction(null, "Tea", "2000/01/02", negative);
        t4 = new Transaction(null, "Tea", "2000/01/02", positive);
    }

    @Test
    public void testConstructor() {
        assertEquals(random, t1.getItem());
        assertEquals(empty, t2.getItem());
        assertEquals(random, t1.getVendor());
        assertEquals(empty, t2.getVendor());
        assertEquals(valid, t1.getDate());
        assertEquals(invalid, t2.getDate());
        assertEquals(positive, t1.getAmount());
        assertEquals(negative, t2.getAmount());
    }

    @Test
    public void testGetItem() {
        assertEquals(random, t1.getItem());
    }

    @Test
    public void testGetVendor() {
        assertEquals(random, t1.getVendor());
    }

    @Test
    public void testGetDate() {
        assertEquals(valid, t1.getDate());
    }

    @Test
    public void testGetAmount() {
        assertEquals(positive, t1.getAmount());
    }

    @Test
    public void testCapitalizeFirstLetter() {
        assertEquals("Tea", t1.capitalizeFirstLetter(t1.getVendor()));
        assertEquals(empty, t2.capitalizeFirstLetter(t2.getVendor()));
        assertEquals("Tea", t3.capitalizeFirstLetter(t3.getVendor()));
        assertEquals(null, t3.capitalizeFirstLetter(t3.getItem()));
    }

    @Test
    public void testToString() {
        assertTrue(t1.toString().contains("Item: Tea | Vendor: Tea | Date: 14/12/2022 | Amount: $14.50"));
    }

    @Test
    public void testCompareTo() {
        assertEquals(0, t3.compareTo(t4));
        assertEquals(1, t3.compareTo(t1));
    }
}