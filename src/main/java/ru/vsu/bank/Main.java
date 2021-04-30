package ru.vsu.bank;


public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank(
                5,
                new CashBox(1234),
                new ClientsGenerator(100, 1000, 50)
        );
        bank.start();
    }
}




