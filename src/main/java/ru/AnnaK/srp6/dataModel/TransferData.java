package ru.AnnaK.srp6.dataModel;

import java.io.Serializable;

public class TransferData implements Serializable {
    private String name;
    private Integer N;
    private String s;
    private Double g;
    private Double V;
    private Double A;
    private Double B;

    private StringBuilder status = new StringBuilder();

    public TransferData addName(String name){
        this.name = name;
        status.append("I");
        return this;
    }

    public TransferData addN(Integer N){
        this.N = N;
        status.append("N");
        return this;
    }

    public TransferData addSs(String s){
        this.s = s;
        status.append("S");
        return this;
    }

    public TransferData addG(Double g){
        this.g = g;
        status.append("g");
        return this;
    }

    public TransferData addV(Double V){
        this.V = V;
        status.append("V");
        return this;
    }

    public TransferData addA(Double A){
        this.A = A;
        status.append("A");
        return this;
    }

    public TransferData addB(Double B){
        this.B = B;
        status.append("B");
        return this;
    }

    public String getName() {
        return name;
    }

    public Integer getN() {
        return N;
    }

    public String getSs() {
        return s;
    }

    public Double getG() {
        return g;
    }

    public Double getV() {
        return V;
    }

    public Double getA() {
        return A;//(A.toString().isEmpty())? 0.0 : A;
    }

    public Double getB() {
        return B;
    }

    public StringBuilder getStatus() {
        return status;
    }
}
