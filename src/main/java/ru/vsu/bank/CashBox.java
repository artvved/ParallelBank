package ru.vsu.bank;



public class CashBox {
    private double cash;

    public CashBox(double cash) {
        this.cash = cash;
    }

    public CashBox() {
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    /**
     * Withdraw money from cash box
     * @param sum - amount of money to take from the cash box
     */
    public synchronized void withdraw(double sum) {
        if (sum > cash) {
            throw new RuntimeException("Not enough money to withdraw");
        }
        cash -= sum;
    }

    /**
     * Deposit money in the cash box
     * @param sum - amount of money to put in the cash box
     */
    public synchronized void deposit(double sum) {
        cash += sum;
    }
}
