package ru.vsu.bank;

import static java.lang.Thread.sleep;




public class Cashier implements Runnable {
    private String name;
    private CashBox cashBox;
    private ClientBlockingQueue clients;

    public Cashier(String name, CashBox cashBox) {
        this.name = name;
        this.cashBox = cashBox;
        this.clients = new ClientBlockingQueue();
    }

    public Cashier() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CashBox getCashBox() {
        return cashBox;
    }

    public void setCashBox(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    public ClientBlockingQueue getClients() {
        return clients;
    }

    public void setClients(ClientBlockingQueue clients) {
        this.clients = clients;
    }

    private void serviceClient(Client client) {
        System.out.printf(
                "Starting %s service %s %s %.2f%n",
                name,
                client.getName(),
                client.getOperation().toString(),
                client.getSum()
        );
        switch (client.getOperation()) {
            case DEPOSIT:
                cashBox.deposit(client.getSum());
                break;
            case WITHDRAW:
                try {
                    cashBox.withdraw(client.getSum());
                } catch (RuntimeException ex) {
                    System.out.printf(
                            "Finish %s service %s %s\nCash: %.2f%n",
                            name,
                            client.getName(),
                            "Refuse",
                            cashBox.getCash()
                    );
                    return;
                }
                break;
        }
        try {
            sleep(client.getServTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf(
                "Finish %s service %s %s\nCash: %.2f%n",
                name,
                client.getName(),
                "Ok",
                cashBox.getCash()
        );
    }

    @Override
    public void run() {
        while (true) {
            serviceClient(clients.next());
        }
    }
}
