# Втора лабораториска вежба по Софтверско инженерство
## Име: Јана Алабашовска
## Индекс: 226078
### Control Flow Graph 
Фотографија од control flow graph-ot
### Цекломатска комплелсност
Цекломатска комплелсност на овој код е 10, истата ја довив преку формулата M=E−N+2P.Каде М е циколматската комплексност, Е е бројот на ребра во графот, N е бројот на јазли во графот, а P е бројот на предикатни јазли.
### Tест случаи според критериумот Every statement

public class SILab2Test {

    @Test
    public void test_nullItemList_throwsException() {
        // Path: 1 (throws at start)
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, "1234567890123456"));
        assertEquals("allItems list can't be null!", e.getMessage());
    }

    @Test
    public void test_invalidItemName_throwsException() {
        // Path: 1, 2, 3, 4 (throws on name null)
        List<Item> items = List.of(new Item(null, 1, 100, 0));
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "1234567890123456"));
        assertEquals("Invalid item!", e.getMessage());
    }

    @Test
    public void test_discountAppliedAndPenaltyApplied() {
        // Path: 1, 2, 3, 4, 5 (true), 6 (true), 8, 9, 10, 11
        Item item = new Item("Book", 11, 400, 0.1); // triggers both penalty and discount
        List<Item> items = List.of(item);
        double expected = -30 + 400 * (1 - 0.1) * 11; // -30 + 3960 = 3930
        double result = SILab2.checkCart(items, "1234567890123456");
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void test_noDiscount_noPenalty() {
        // Path: 1, 2, 3, 4, 5 (false), 6 (false), 7, 8, 9, 10, 11
        Item item = new Item("Pen", 2, 10, 0.0);
        List<Item> items = List.of(item);
        double expected = 10 * 2;
        double result = SILab2.checkCart(items, "1234567890123456");
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void test_cardNumberTooShort_throwsException() {
        // Path: 1, 2, 3, 4, 5 (false), 6 (false), 7, 8 (false), 12 (throws)
        Item item = new Item("Pen", 2, 10, 0.0);
        List<Item> items = List.of(item);
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "12345"));
        assertEquals("Invalid card number!", e.getMessage());
    }

    @Test
    public void test_cardNumberWithInvalidChar_throwsException() {
        // Path: 1, 2, 3, 4, 5 (false), 6 (false), 7, 8 (true), 9 (loop), 10 (true), 11 (throws)
        Item item = new Item("Pen", 2, 10, 0.0);
        List<Item> items = List.of(item);
        RuntimeException e = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items, "1234567890abcd!@"));
        assertEquals("Invalid character in card number!", e.getMessage());
    }
}
### Тест случаи според критериумот Every path
@Test
public void test_TC1_allFalse() {
    // A=false, B=false, C=false ➝ false
    Item item = new Item("Item1", 100, 5, 0.0);
    List<Item> items = List.of(item);
    double expected = 100 * 5;  // No penalty
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}

@Test
public void test_TC2_A_true_BC_false() {
    // A=true, B=false, C=false ➝ true
    Item item = new Item("Item2", 400, 5, 0.0);
    List<Item> items = List.of(item);
    double expected = -30 + 400 * 5;  // Penalty applied
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}

@Test
public void test_TC3_B_true_AC_false() {
    // A=false, B=true, C=false ➝ true
    Item item = new Item("Item3", 100, 5, 0.1);
    List<Item> items = List.of(item);
    double expected = -30 + 100 * 0.9 * 5;
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}

@Test
public void test_TC4_C_true_AB_false() {
    // A=false, B=false, C=true ➝ true
    Item item = new Item("Item4", 100, 20, 0.0);
    List<Item> items = List.of(item);
    double expected = -30 + 100 * 20;
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}

@Test
public void test_TC5_AB_true_C_false() {
    // A=true, B=true, C=false ➝ true
    Item item = new Item("Item5", 400, 5, 0.1);
    List<Item> items = List.of(item);
    double expected = -30 + 400 * 0.9 * 5;
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}

@Test
public void test_TC6_AC_true_B_false() {
    // A=true, B=false, C=true ➝ true
    Item item = new Item("Item6", 400, 20, 0.0);
    List<Item> items = List.of(item);
    double expected = -30 + 400 * 20;
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}

@Test
public void test_TC7_BC_true_A_false() {
    // A=false, B=true, C=true ➝ true
    Item item = new Item("Item7", 100, 20, 0.1);
    List<Item> items = List.of(item);
    double expected = -30 + 100 * 0.9 * 20;
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}

@Test
public void test_TC8_ABC_allTrue() {
    // A=true, B=true, C=true ➝ true
    Item item = new Item("Item8", 400, 20, 0.1);
    List<Item> items = List.of(item);
    double expected = -30 + 400 * 0.9 * 20;
    assertEquals(expected, SILab2.checkCart(items, "1234567890123456"), 0.001);
}
Multiple Condition тестирање за условот if (item.getPrice() > 300 || item.getDiscount() > 0 || item.getQuantity() > 10) вклучува 8 можни комбинации на логички вредности (true/false) за трите услови. Секој тест случај ја покрива една од тие комбинации. Минималниот број на потребни тест случаи за целосна покриеност според Multiple Condition критериумот е 8.

