package ru.AnnaK.srp6.dataModel;

public class UserContext {
    private final String name;
    private final Integer N;
    private final String s;
    private final Double g;
    private Double V;
    private Double A;
    private Double B;

    private Double b;

    private StringBuilder status = new StringBuilder();

    public UserContext(final String name, final String s, final Double V, final Integer N, final Double g) {
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
        return A;
    }

    public Double getB() {
        return B;
    }

    public Double getServerB(){
        return this.b;
    }

    public String getStatus (){
        return status.toString();
    }

    public void setV(Double V) {
        status.append("V");
        this.V = V;
    }

    public void setA(Double A) {
        status.append("A");
        this.A = A;
    }

    public void setB(Double B) {
        status.append("B");
        this.B = B;
    }

    public void setServerB(Double b) {
        status.append("b");
        this.b = b;
    }
}
