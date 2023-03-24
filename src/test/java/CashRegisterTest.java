import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CashRegisterTest {
    CashRegister r;

    @BeforeAll
    void init() {
        r = new CashRegister(new CashRegister.Cash(1, 1, 2, 5, 1));
    }

    @Test
    @Order(1)
    void when_add_should_return_added_denomination() {
        r.add(new CashRegister.Cash(1, 0, 0, 0, 0));
        CashRegister.Cash cash = r.getCash();
        assertEquals(cash.getNumberOf20(), 2);
        assertEquals(cash.getNumberOf10(), 1);
        assertEquals(cash.getNumberOf5(), 2);
        assertEquals(cash.getNumberOf2(), 5);
        assertEquals(cash.getNumberOf1(), 1);
    }

    @Test
    @Order(2)
    void when_get_changes_should_return_right_denomination1() {
        CashRegister.Cash changes = r.getChanges(2, new CashRegister.Cash(0, 1, 0, 0, 0));
        assertEquals(changes.getNumberOf20(), 0);
        assertEquals(changes.getNumberOf10(), 0);
        assertEquals(changes.getNumberOf5(), 1);
        assertEquals(changes.getNumberOf2(), 1);
        assertEquals(changes.getNumberOf1(), 1);

        assertEquals(r.getCash().getNumberOf20(), 2);
        assertEquals(r.getCash().getNumberOf10(), 2);
        assertEquals(r.getCash().getNumberOf5(), 1);
        assertEquals(r.getCash().getNumberOf2(), 4);
        assertEquals(r.getCash().getNumberOf1(), 0);
    }

    @Test
    @Order(3)
    void when_get_changes_should_return_right_denomination2() {
        CashRegister.Cash changes = r.getChanges(2, new CashRegister.Cash(0, 1, 0, 0, 0));
        assertEquals(changes.getNumberOf20(), 0);
        assertEquals(changes.getNumberOf10(), 0);
        assertEquals(changes.getNumberOf5(), 0);
        assertEquals(changes.getNumberOf2(), 4);
        assertEquals(changes.getNumberOf1(), 0);

        assertEquals(r.getCash().getNumberOf20(), 2);
        assertEquals(r.getCash().getNumberOf10(), 2);
        assertEquals(r.getCash().getNumberOf5(), 1);
        assertEquals(r.getCash().getNumberOf2(), 0);
        assertEquals(r.getCash().getNumberOf1(), 0);
    }

}