package persistence;

import model.TransactionList;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TransactionList tl = new TransactionList("My transaction list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            TransactionList tl = new TransactionList("My transaction list");
            JsonWriter writer = new JsonWriter("./data/testWriterTransactionList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTransactionList.json");
            tl = reader.read();
            assertEquals("My transaction list", tl.getName());
            assertEquals(0, tl.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            TransactionList tl = new TransactionList("My transaction list");
            tl.add(new Transaction("saw", "saw shop", "01/01/2002", 15));
            tl.add(new Transaction("needle", "needle store", "02/02/2002", 20));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTransactionList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTransactionList.json");
            tl = reader.read();
            assertEquals("My transaction list", tl.getName());
            List<Transaction> transactions = tl.getList();
            assertEquals(2, transactions.size());
            checkTransaction("saw", "saw shop", "01/01/2002", 15, transactions.get(0));
            checkTransaction("needle","needle store", "02/02/2002", 20, transactions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
