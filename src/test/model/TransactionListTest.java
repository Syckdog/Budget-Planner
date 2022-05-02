package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionListTest {

    private static final String random = "Tea";
    private static final String empty = "";
    private static final String valid = "14/12/2022";
    private static final String invalid = "0";
    private static final double positive = 14.50;
    private static final double negative = -14.50;

    private Transaction t1;
    private Transaction t2;
    private TransactionList list1;
    private List<Transaction> list2;

    @BeforeEach
    public void beforeRun() {
        t1 = new Transaction(random, random, valid, positive);
        t2 = new Transaction(empty, empty, invalid, negative);
        list1 = new TransactionList("My transaction list");
        list2 = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, list1.size());
    }

    @Test
    public void testAdd() {
        list1.add(t1);
        assertTrue(list1.contains(t1));
        assertEquals(1, list1.size());
        list1.add(t2);
        assertTrue(list1.contains(t1));
        assertTrue(list1.contains(t2));
        assertEquals(2, list1.size());
    }

    @Test
    public void testAddDuplicates() {
        list1.add(t1);
        list1.add(t1);
        assertTrue(list1.contains(t1));
        assertEquals(2, list1.size());
    }

    @Test
    public void testRemove() {
        list1.add(t1);
        list1.remove(t1);
        assertFalse(list1.contains(t1));
        assertEquals(0, list1.size());
        assertEquals(true, list1.remove(t1));
    }

    @Test
    public void testRemoveWhenMultipleTransactions() {
        list1.add(t1);
        list1.add(t2);
        list1.remove(t1);
        assertTrue(list1.contains(t2));
        assertFalse(list1.contains(t1));
        assertEquals(1, list1.size());
        assertEquals(true, list1.remove(t1));
    }

    @Test
    public void testRemoveWhenNotFound() {
        list1.add(t1);
        list1.remove(t2);
        assertTrue(list1.contains(t1));
        assertEquals(1, list1.size());
        assertEquals(false, list1.remove(t1));
    }

    @Test
    public void testRemoveWhenNull() {
        list1.remove(t1);
        assertEquals(true, list1.remove(t1));
    }

    @Test
    public void testSumAmount() {
        list1.add(t1);
        list1.add(t2);
        assertEquals(positive+negative, list1.sumAmount());
    }

    @Test
    public void testEmptySumAmount() {
        assertEquals(0, list1.sumAmount());
    }

    @Test
    public void testSize() {
        assertEquals(0, list1.size());
        list1.add(t1);
        list1.add(t2);
        assertEquals(2, list1.size());
    }

    @Test
    public void testContains() {
        assertFalse(list1.contains(t1));
        list1.add(t1);
        assertTrue(list1.contains(t1));
        assertFalse(list1.contains(t2));
    }

    @Test
    public void testGetList() {
        assertEquals(list2, list1.getList());
        list1.add(t1);
        list2.add(t1);
        assertEquals(list2, list1.getList());
    }
}


