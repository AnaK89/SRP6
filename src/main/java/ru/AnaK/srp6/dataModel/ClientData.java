package ru.AnaK.srp6.dataModel;

import java.math.BigInteger;

public class ClientData {
    private final String name;
    private final String password;
    private final BigInteger N;
    private final String s;
    private final BigInteger g;
    private BigInteger V;
    private BigInteger A;
    private BigInteger B;
    private BigInteger S;
    private BigInteger K;

    private BigInteger x;
    private BigInteger a;

    private StringBuilder status = new StringBuilder();

    public ClientData (final String name, final String password, final String s, final BigInteger g, final BigInteger N){
        this.name = name;
        this.password = password;
        this.s = s;
        this.g = g;
        this.N = N;
        status.append("SgN");
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public BigInteger getN() {
        return N;
    }

    public String getSs() {
        return s;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getV() {
        return V;
    }

    public BigInteger getA() {
        return A;
    }

    public BigInteger getB() {
        return B;
    }

    public BigInteger getS() { return S; }

    public BigInteger getK() { return K; }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getClientA(){
        return a;
    }

    public String getStatus (){
        return status.toString();
    }

    public void setV(BigInteger V) {
        status.append("V");
        this.V = V;
    }

    public void setA(BigInteger A) {
        status.append("A");
        this.A = A;
    }

    public void setB(BigInteger B) {
        status.append("B");
        this.B = B;
    }

    public void setS(BigInteger S) {
        status.append("S");
        this.S = S;
    }

    public void setK(BigInteger K) {
        status.append("K");
        this.K = K;
    }

    public void setX(BigInteger x) {
        status.append("x");
        this.x = x;
    }

    public void setClientA(BigInteger a){
        status.append("a");
        this.a = a;
    }
}
