package ru.AnnaK.srp6;

import java.util.Random;

public class OperationsSRP6 {
    private static final int K = 3;

    public double generateRandomNumber(){
        Random random = new Random(1);
        return random.nextDouble();
    }

    public double hashFunction (String str){
        return str.hashCode() % 100;
    }

    public double hashFunction (String str1, String str2){
        return (str1.hashCode() + str2.hashCode()) % 100;
    }

    public double hashFunction (Double d){
        return d.hashCode() % 100;
    }

    public double hashFunction (Double d1, Double d2){
        return (d1.hashCode() + d2.hashCode()) % 100;
    }

    public double generateV (double g, double x, int N){
        return Math.pow(g, x) % N;
    }

    public double generateA (double g, double a, int N){
        return Math.pow(g, a) % N;
    }

    public double generateB (double V, double g, double b, int N){
        return (K * V) + (Math.pow(g, b) % N);
    }

    public double clientGenerateS (double B, double g, double x, double a, double u, int N){ //S = ((B - k*(g^x % N)) ^ (a + u*x)) % N
        return Math.pow( B - (K * (Math.pow(g, x) % N)), a + u*x) % N;
    }

    public double serverGenerateS (double A, double V, double u, double b, int N){ //S = ((A*(v^u % N)) ^ B) % N
        return Math.pow(A * Math.pow(V, u) % N, b) % N;
    }
}
