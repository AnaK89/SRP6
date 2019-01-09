package ru.AnnaK.srp6;

public class MainClient {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client("Alisa", "Alisa1", "solt", 5, 10);
        client.run();
    }
}
