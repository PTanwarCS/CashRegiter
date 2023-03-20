public class Test {
    public static void main(String[] args) {
        var r = new CashRegister(new CashRegister.Cash(1,1,2,5,1));
        System.out.println("INITIAL------- " + r.getCash());
        System.out.println("-----------------");
        r.add(new CashRegister.Cash(1,0,0,0,0));
        System.out.println("AFTER ADDITION------- " + r.getCash());
        CashRegister.Cash changes = r.getChanges(2, new CashRegister.Cash(0, 1, 0, 0, 0));
        System.out.println(changes);
        System.out.println("AFTER CHANGE-------" + r.getCash());
        CashRegister.Cash changes2 = r.getChanges(2, new CashRegister.Cash(0, 1, 0, 0, 0));
        System.out.println(changes2);
        System.out.println("AFTER CHANGE-------" + r.getCash());
    }
}
