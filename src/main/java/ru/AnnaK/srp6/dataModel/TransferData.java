package ru.AnnaK.srp6.dataModel;

public class TransferData {
    private String name;
    private Integer N;
    private String S;
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

    public TransferData addS(String S){
        this.S = S;
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

    public String getS() {
        return S;
    }

    public Double getG() {
        return g;
    }

    public Double getV() {
        return V;
    }

    public Double getA() {
        return A;
    }

    public Double getB() {
        return B;
    }

    public StringBuilder getStatus() {
        return status;
    }
}
