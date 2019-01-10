package ru.AnaK.srp6.dataModel;

import java.math.BigInteger;

public class UserContext {
    private final String name;
    private final BigInteger N;
    private final String s;
    private final BigInteger g;
    private BigInteger V;
    private BigInteger A;
    private BigInteger B;
    private BigInteger S;
    private BigInteger K;

    private BigInteger b;

    private StringBuilder status = new StringBuilder();

    public UserContext(final String name, final String s, final BigInteger V, final BigInteger N, final BigInteger g) {
        this.name = name;
        this.s = s;
        this.V = V;
        this.N = N;
        this.g = g;
        this.status.append("SVNg");
    }

    public String getName() {
        return name;
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

    public BigInteger getServerB(){
        return this.b;
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

    public void setServerB(BigInteger b) {
        status.append("b");
        this.b = b;
    }
}
