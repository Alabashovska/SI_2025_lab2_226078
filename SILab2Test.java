
import java.util.*;

public class SILab2Test {

    @Test
    public void test_nullItemList_throwsException() {
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, "1234567890123456"));
        assertEquals("allItems list can't be null!", e.getMessage());
    }

    @Test
    public void test_invalidItemName_throwsException() {
        List<Item> items = List.of(new Item(null, 1, 100, 0));
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "1234567890123456"));
        assertEquals("Invalid item!", e.getMessage());
    }

    @Test
    public void test_discountAppliedAndPenaltyApplied() {
        Item item = new Item("Book", 11, 400, 0.1);
        List<Item> items = List.of(item);
        double expected = -30 + 400 * (1 - 0.1) * 11;
        double result = SILab2.checkCart(items, "1234567890123456");
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void test_noDiscount_noPenalty() {
        Item item = new Item("Pen", 2, 10, 0.0);
        List<Item> items = List.of(item);
        double expected = 10 * 2;
        double result = SILab2.checkCart(items, "1234567890123456");
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void test_cardNumberTooShort_throwsException() {
        Item item = new Item("Pen", 2, 10, 0.0);
        List<Item> items = List.of(item);
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "12345"));
        assertEquals("Invalid card number!", e.getMessage());
    }

    @Test
    public void test_cardNumberWithInvalidChar_throwsException() {
        Item item = new Item("Pen", 2, 10, 0.0);
        List<Item> items = List.of(item);
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "1234567890abcd!@"));
        assertEquals("Invalid character in card number!", e.getMessage());
    }
}
