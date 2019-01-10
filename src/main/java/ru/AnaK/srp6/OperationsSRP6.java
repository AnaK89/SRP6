package ru.AnaK.srp6;

import java.math.BigInteger;
import java.util.Random;
import java.util.logging.Logger;

public class OperationsSRP6 {
    private static final Logger log = Logger.getLogger(OperationsSRP6.class.getName());
    private static final BigInteger K = BigInteger.valueOf(3);

    public BigInteger generateRandomNumber(){
        Random random = new Random();
        return BigInteger.valueOf(random.nextInt(1000));
    }

    public BigInteger hashFunction (String str){
        return BigInteger.valueOf(str.codePointCount(0, str.length() - 1));
    }

    public BigInteger hashFunction (String str1, String str2){
        return BigInteger.valueOf(str1.codePointCount(0, str1.length() - 1) + str2.codePointCount(0, str2.length() - 1));
    }

    public BigInteger hashFunction (BigInteger b){
        return b;
    }

    public BigInteger hashFunction (BigInteger b1, BigInteger b2){
        return b1.add(b2);
    }

    public BigInteger generateV (BigInteger g, BigInteger x, BigInteger N){
        return g.modPow(x, N);
    }

    public BigInteger generateA (BigInteger g, BigInteger a, BigInteger N){
        return g.modPow(a, N);
    }

    public BigInteger generateB (BigInteger V, BigInteger g, BigInteger b, BigInteger N){
        return (K.multiply(V)).add(g.modPow(b, N));
    }

    public BigInteger clientCalculateS (BigInteger B, BigInteger g, BigInteger x, BigInteger a, BigInteger u, BigInteger N){ //S = ((B - K*(g^(x % N))) ^ (a + u*x)) % N
        return B.subtract(K.multiply(g.pow(x.mod(N).intValue()))).modPow(a.add(u.multiply(x)), N);
    }

    public BigInteger serverCalculateS (BigInteger A, BigInteger V, BigInteger u, BigInteger b, BigInteger N){ //S = ((A*(V^u % N)) ^ b) % N
        return A.multiply(V.modPow(u, N)).modPow(b, N);
    }

    public BigInteger calculateM (BigInteger N, BigInteger g, String name, BigInteger S, BigInteger A, BigInteger B){
        return hashFunction(hashFunction(N).xor(hashFunction(g)).add(hashFunction(g).add(hashFunction(name)).add(S).add(A).add(B).add(K)));
    }

    public BigInteger calculateR(BigInteger A, BigInteger M){
        return hashFunction(A.add(M).add(K));
    }
}
