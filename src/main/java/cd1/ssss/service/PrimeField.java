package cd1.ssss.service;

import java.util.Random;

/**
 * @author Jordan
 */
public class PrimeField implements FiniteField {
    public static final Random RANDOM = new Random();
    private final int prime;

    public PrimeField(int prime) {
        this.prime = prime;
    }

    @Override
    public int add(int a, int b) {
        return (a + b) % prime;
    }

    @Override
    public int minus(int a, int b) {
        int result = (a - b) % prime;
        return result < 0 ? result + prime : result;
    }

    @Override
    public int multiply(int a, int b) {
        return (a * b) % prime;
    }

    @Override
    public int divide(int a, int b) {
        return (a * modInverse(b, prime)) % prime;
    }

    @Override
    public int randElement(boolean excludeZero) {
        return excludeZero ? RANDOM.nextInt(prime - 1) + 1 : RANDOM.nextInt(prime);
    }

    // https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
    private int modInverse(int a, int m) {
        int m0 = m;
        int y = 0, x = 1;

        if (m == 1) return 0;

        while (a > 1) {
            int q = a / m;
            int t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }

        if (x < 0)
            x += m0;

        return x;
    }
}
