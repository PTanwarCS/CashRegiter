public class Test {
    public static void main(String[] args) {
        var r = new CashRegister(new CashRegister.Cash(10, 1, 0, 2, 1));
        System.out.println("INITIAL------- " + r.getCash());
        System.out.println("-----------------");
        r.add(new CashRegister.Cash(1, 1, 0, 0, 0));
        System.out.println("AFTER ADDITION------- " + r.getCash());
        CashRegister.Cash changes = r.getChanges(3, new CashRegister.Cash(11, 0, 0, 0, 0));
        System.out.println(changes);
        System.out.println("AFTER CHANGE-------" + r.getCash());
    }
}
