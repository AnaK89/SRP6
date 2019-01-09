package ru.AnnaK.srp6.dataModel;

public class ClientData {
    private final String name;
    private final String password;
    private final Integer N;
    private final String s;
    private final Double g;
    private Double V;
    private Double A;
    private Double B;

    private Double x;
    private Double a;

    private StringBuilder status = new StringBuilder();

    public ClientData (final String name, final String password, final String s, final Double g, final Integer N){
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

    public Double getX() {
        return x;
    }

    public Double getClientA(){
        return a;
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

    public void setX(Double x) {
        status.append("x");
        this.x = x;
    }

    public void setClientA(Double a){
        status.append("a");
        this.a = a;
    }
}
