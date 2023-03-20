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

        @Override
        public String toString() {
            String r = """
                    Cash Current State):
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

        int totalPaid = getTotalAmount(paid);
//        System.out.println("total cash paid: " + totalPaid);
        int returnAmount = totalPaid - charge;
//        System.out.println("total amount to return: " + returnAmount);

        if (returnAmount > getTotalAmount(this.getCash())) {
            rollbackDenomination(paid);
            throw new RuntimeException("Not enough money");
        }

        int quotient = returnAmount / 20;

        //to be returned in response
        int n20 = 0, n10 = 0, n5 = 0, n2 = 0, n1 = 0;

        if (quotient > 0) {
            n20 = quotient;
            //check if denomination available
            if (this.cash.numberOf20 >= n20) {
                returnAmount = returnAmount % 20;
                this.cash.numberOf20 -= n20;
            }
        }
        if (returnAmount > 0) {
            n10 = returnAmount / 10;
            if (this.cash.numberOf10 >= n10) {
                returnAmount = returnAmount % 10;
                this.cash.numberOf10 -= n10;
            }
        }
        if (returnAmount > 0) {
            n5 = returnAmount / 5;
            if (this.cash.numberOf5 >= n5) {
                returnAmount = returnAmount % 5;
                this.cash.numberOf5 -= n5;
            }
        }
        if (returnAmount > 0) {
            n2 = returnAmount / 2;
            if (this.cash.numberOf2 >= n2) {
                returnAmount = returnAmount % 2;
                this.cash.numberOf2 -= n2;
            }
        }
        if (returnAmount > 0) {
            if (this.cash.numberOf1 >= returnAmount) {
                n1 = returnAmount;
                this.cash.numberOf1 -= n1;
            } else {
                rollbackDenomination(paid);
                throw new RuntimeException("Changes not available.");
            }
        }

        return new Cash(n20, n10, n5, n2, n1);
    }

    public int getTotalAmount(Cash cash) {
        int total = 0;
        total += cash.numberOf20 * 20;
        total += cash.numberOf10 * 10;
        total += cash.numberOf5 * 5;
        total += cash.numberOf2 * 2;
        total += cash.numberOf1;

        return total;
    }

    public int getTotalAmount() {
        return getTotalAmount(this.getCash());
    }

    public void rollbackDenomination(Cash cash) {
        this.cash.numberOf20 -= cash.numberOf20;
        this.cash.numberOf10 -= cash.numberOf10;
        this.cash.numberOf5 -= cash.numberOf5;
        this.cash.numberOf2 -= cash.numberOf2;
        this.cash.numberOf1 -= cash.numberOf1;
    }

}