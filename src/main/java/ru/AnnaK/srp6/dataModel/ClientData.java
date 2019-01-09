package ru.AnnaK.srp6.dataModel;

public class ClientData {
    private final String name;
    private final String password;
    private final Integer N;
    private final String S;
    private final Double g;
    private Double V;
    private Double A;
    private Double B;

    private Double x;
    private Double a;

    public ClientData (final String name, final String password, final String S, final Double g, final Integer N){
        this.name = name;
        this.password = password;
        this.S = S;
        this.g = g;
        this.N = N;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
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

    public Double getX() {
        return x;
    }

    public void setV(Double v) {
        V = v;
    }

    public void setA(Double a) {
        A = a;
    }

    public void setB(Double b) {
        B = b;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getClientA(){
        return a;
    }

    public void setClientA(Double a){
        this.a = a;
    }
}
