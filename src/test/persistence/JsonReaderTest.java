package persistence;

import model.Transaction;
import model.TransactionList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TransactionList tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTransactionList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTransactionList.json");
        try {
            TransactionList tl = reader.read();
            assertEquals("My transaction list", tl.getName());
            assertEquals(0, tl.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTransactionList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTransactionList.json");
        try {
            TransactionList tl = reader.read();
            assertEquals("My transaction list", tl.getName());
            List<Transaction> transactions = tl.getList();
            assertEquals(2, transactions.size());
            checkTransaction("needle", "needle store", "01/02/2002",15, transactions.get(0));
            checkTransaction("saw", "saw shop", "01/01/2001", 14, transactions.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
