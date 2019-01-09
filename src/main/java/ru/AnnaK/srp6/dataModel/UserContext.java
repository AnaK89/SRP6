package ru.AnnaK.srp6.dataModel;

public class UserContext {
    private final String name;
    private final Integer N;
    private final String S;
    private final Double g;
    private Double V;
    private Double A;
    private Double B;

    private Double b;

    public UserContext(final String name, final String S, final Double V, final Integer N, final Double g) {
        this.name = name;
        this.S = S;
        this.V = V;
        this.N = N;
        this.g = g;
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

    public void setV(Double v) {
        V = v;
    }

    public void setA(Double a) {
        A = a;
    }

    public void setB(Double b) {
        B = b;
    }

    public void setServerB(Double b) {
        this.b = b;
    }

    public Double getServerB(){
        return this.b;
    }
}
