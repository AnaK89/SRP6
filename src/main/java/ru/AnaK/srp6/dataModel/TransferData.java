package ru.AnaK.srp6.dataModel;

import java.io.Serializable;
import java.math.BigInteger;

public class TransferData implements Serializable {
    private String name;
    private String message;
    private BigInteger N;
    private String s;
    private BigInteger g;
    private BigInteger V;
    private BigInteger A;
    private BigInteger B;
    private BigInteger clientM;
    private BigInteger serverR;

    private StringBuilder status = new StringBuilder();

    public TransferData addName(String name){
        this.name = name;
        status.append("I");
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransferData addN(BigInteger N){
        this.N = N;
        status.append("N");
        return this;
    }

    public TransferData addSs(String s){
        this.s = s;
        status.append("S");
        return this;
    }

    public TransferData addG(BigInteger g){
        this.g = g;
        status.append("g");
        return this;
    }

    public TransferData addV(BigInteger V){
        this.V = V;
        status.append("V");
        return this;
    }

    public TransferData addA(BigInteger A){
        this.A = A;
        status.append("A");
        return this;
    }

    public TransferData addB(BigInteger B){
        this.B = B;
        status.append("B");
        return this;
    }

    public TransferData addClientM (BigInteger clientM){
        this.clientM = clientM;
        status.append("M");
        return this;
    }

    public TransferData addServerR (BigInteger serverR){
        this.serverR = serverR;
        status.append("R");
        return this;
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

    public BigInteger getClientM() {
        return clientM;
    }

    public BigInteger getServerR() {
        return serverR;
    }

    public String getStatus() {
        return status.toString();
    }

    public String getMessage() {
        return message;
    }
}
