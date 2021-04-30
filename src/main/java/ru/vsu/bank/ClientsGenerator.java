package ru.vsu.bank;

import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Generates a client and adds to the shortest queue
 */

public class ClientsGenerator implements Runnable {
    private List<Cashier> cashiers;
    private int avgServiceTime;
    private int avgClientSum;
    private double clientsPerSecond;
    private Random rnd = new Random();
    private int clientId = 1;

    public ClientsGenerator(List<Cashier> cashiers, int avgServiceTime, int avgClientSum, double clientsPerSecond) {
        this.cashiers = cashiers;
        this.avgServiceTime = avgServiceTime;
        this.avgClientSum = avgClientSum;
        this.clientsPerSecond = clientsPerSecond;
    }

    public ClientsGenerator(int avgServiceTime, int avgClientSum, double clientsPerSecond) {
        this(null, avgServiceTime, avgClientSum, clientsPerSecond);
    }

    private Client generateClient() {
        Client client = new Client(
                ClientOperation.values()[rnd.nextInt(2)],
                rnd.nextInt(2*avgServiceTime) + 1,
                String.format("Client-%d", clientId++),
                rnd.nextInt(2*avgClientSum) + 1
        );
        return client;
    }

    private void addClient(Client client) {
        Cashier cashier = findTellerWithShortestQueue();
        cashier.getClients().put(client);
        System.out.printf("Added %s%n", client.getName());
    }

    private Cashier findTellerWithShortestQueue() {
        Cashier opt = cashiers.get(0);
        for (Cashier cashier : cashiers) {
            if (cashier.getClients().getSize() < opt.getClients().getSize()) {
                opt = cashier;
            }
        }
        return opt;
    }

    public void setTellers(List<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    @Override
    public void run() {
        while(true) {
            int timeline = (int)(2*1000/ clientsPerSecond);
            addClient(generateClient());
            try {
                sleep(timeline);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
