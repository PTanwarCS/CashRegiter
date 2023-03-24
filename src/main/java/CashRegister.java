import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CashRegister {

    public static class Cash {
        private int numberOf20;
        private int numberOf10;
        private int numberOf5;
        private int numberOf2;
        private int numberOf1;

        public Cash(int numberOf20, int numberOf10, int numberOf5, int numberOf2, int numberOf1) {
            this.numberOf20 = numberOf20;
            this.numberOf10 = numberOf10;
            this.numberOf5 = numberOf5;
            this.numberOf2 = numberOf2;
            this.numberOf1 = numberOf1;
        }

        public int getNumberOf20() {
            return numberOf20;
        }

        public int getNumberOf10() {
            return numberOf10;
        }

        public int getNumberOf5() {
            return numberOf5;
        }

        public int getNumberOf2() {
            return numberOf2;
        }

        public int getNumberOf1() {
            return numberOf1;
        }

        public int getTotalAmount() {
            int total = 0;
            total += this.numberOf20 * 20;
            total += this.numberOf10 * 10;
            total += this.numberOf5 * 5;
            total += this.numberOf2 * 2;
            total += this.numberOf1;

            return total;
        }

        @Override
        public String toString() {
            String r = """
                    numberOf20= %s
                    numberOf10= %s
                    numberOf5= %s
                    numberOf2= %s
                    numberOf1= %s
                    """;
            return String.format(r, numberOf20, numberOf10, numberOf5, numberOf2, numberOf1);

        }
    }

    private final Cash cash;

    public CashRegister(Cash cash) {
        this.cash = cash;
    }

    public Cash getCash() {
        return this.cash;
    }

    public void add(Cash cash) {
        this.cash.numberOf20 += cash.numberOf20;
        this.cash.numberOf10 += cash.numberOf10;
        this.cash.numberOf5 += cash.numberOf5;
        this.cash.numberOf2 += cash.numberOf2;
        this.cash.numberOf1 += cash.numberOf1;
    }

    public Cash getChanges(int charge, Cash paid) {
        //Add denominations to existing
        add(paid);

        int totalPaid = paid.getTotalAmount();
        int returnAmount = totalPaid - charge;

        System.out.println("Amount to return: " + returnAmount);

        int[] denominations = {20, 10, 5, 2, 1}; //Todo: hardcoded denominations. To be replaced.
        int[] vals = new int[denominations.length];
        var possibleCombinations = new ArrayList<Cash>();
        getAllCombinations(0, denominations, vals, returnAmount, possibleCombinations, this.cash);

        if (possibleCombinations.isEmpty())
            returnNotPossible(paid);

        for (Cash possibleCombination : possibleCombinations) {
            System.out.println(possibleCombination);
            //todo
            //1. sort based on larger denomination. Return bigger denomination first. This is not need if handled at looping.
            //2. check which combination of denomination is available. Can be done in getAllCombinations to reduce extra looping.
            //3. If found, return and reduce that from Cash
            //4. If none matches, throw error saying "Not possible"
        }

        //to be returned in response
        int n20 = 0, n10 = 0, n5 = 0, n2 = 0, n1 = 0; //todo

        return new Cash(n20, n10, n5, n2, n1); //todo
    }

    public void rollbackDenominations(Cash cash) {
        this.cash.numberOf20 -= cash.numberOf20;
        this.cash.numberOf10 -= cash.numberOf10;
        this.cash.numberOf5 -= cash.numberOf5;
        this.cash.numberOf2 -= cash.numberOf2;
        this.cash.numberOf1 -= cash.numberOf1;
    }

    /**
     * return all possible combination of denominations possible
     */
    public static void getAllCombinations(int index, int[] denominations, int[] vals, int target, List<CashRegister.Cash> result, Cash availableCash) {
        if (target == 0) {
            var copy = Arrays.copyOf(vals, vals.length); //from copy because actual array mutate
            //Can be looped, but assume that the vals is of length 5. This would be handled with removal of hardcoded denominations
            var n20 = copy[0];
            var n10 = copy[1];
            var n5 = copy[2];
            var n2 = copy[3];
            var n1 = copy[4];
            result.add(new CashRegister.Cash(n20, n10, n5, n2, n1));
            return;
        }
        if (index == denominations.length) return;
        int currentDenomination = denominations[index];
        for (int i = 0; i * currentDenomination <= target; i++) {
            vals[index] = i;
            getAllCombinations(index + 1, denominations, vals, target - i * currentDenomination, result, availableCash);
            vals[index] = 0;
        }
    }

    public void returnNotPossible(Cash cashToRollback) {
        rollbackDenominations(cashToRollback);
        throw new IllegalStateException("Not possible");
    }

}