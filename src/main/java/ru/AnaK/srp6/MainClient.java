package ru.AnaK.srp6;

import java.math.BigInteger;

public class MainClient {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client("Alisa", "Alisa1", "salt", BigInteger.valueOf(547), BigInteger.valueOf(55_288_875));
        client.run();
    }
}
