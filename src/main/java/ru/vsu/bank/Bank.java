package ru.vsu.bank;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




public class Bank {
    private List<Thread> cashiers;
    private CashBox cashBox;
    private Thread generator;

    public Bank(int cashierCount, CashBox cashBox, ClientsGenerator generator) {
        List<Cashier> cashiers = new ArrayList<>();
        for (int i = 0;i < cashierCount;i++) {
            cashiers.add(new Cashier(String.format("Cashier%d", i + 1), cashBox));
        }
        generator.setTellers(cashiers);
        this.cashiers = cashiers.stream().map(Thread::new).collect(Collectors.toList());
        this.cashBox = cashBox;
        this.generator = new Thread(generator);
    }


    public List<Thread> getCashiers() {
        return cashiers;
    }

    public CashBox getCashBox() {
        return cashBox;
    }

    public Thread getGenerator() {
        return generator;
    }

    /**
     * Starts the bank
     */
    public void start() {
        for (Thread teller : cashiers) {
            teller.start();
        }
        generator.start();
    }
}
