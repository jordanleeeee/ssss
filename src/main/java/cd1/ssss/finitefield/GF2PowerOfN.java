package cd1.ssss.finitefield;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jordan
 */
public class GF2PowerOfN implements FiniteField {
    public static final Random RANDOM = ThreadLocalRandom.current();
    private final int n;
    private final int size; // 2^n
    private final int irreduciblePolynomialsInNum;

    public GF2PowerOfN(int n, int irreduciblePolynomialsInNum) {
        this.n = n;
        this.size = (int) Math.pow(2, n);
        this.irreduciblePolynomialsInNum = irreduciblePolynomialsInNum;
    }

    @Override
    public String toString() {
        return "GF2PowerOfN{" +
            "n=" + n +
            ", size=" + size +
            ", irreduciblePolynomialsInNum=" + irreduciblePolynomialsInNum +
            '}';
    }

    @Override
    public int add(int a, int b) {
        return a ^ b;
    }

    @Override
    public int minus(int a, int b) {
        return a ^ b;
    }

    @Override
    public int multiply(int a, int b) {
        //refer to page 25-26 of https://engineering.purdue.edu/kak/compsec/NewLectures/Lecture7.pdf
        int result = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (getBit(b, n - 1 - i) == 1) {
                // result += a
                result ^= a;
            }
            // perform a = a*x
            if (getBit(a, n - 1) == 0) {
                a = shiftLeftLogical(a);
            } else {
                a = shiftLeftLogical(a) ^ irreduciblePolynomialsInNum;
            }
        }
        return result;
    }

    @Override
    public int divide(int a, int b) {
        // recall that a / b == a * inverseOf(b)
        // from wiki: the inverse of x is x^(p^n − 2).
        int inverse = power(b, size - 2);
        return multiply(a, inverse);
    }

    @Override
    public int randElement(boolean excludeZero) {
        return excludeZero ? RANDOM.nextInt(size - 1) + 1 : RANDOM.nextInt(size);
    }

    private int power(int num, int index) {
        int result = 1;
        for (int i = 0; i < index; i++) {
            result = multiply(result, num);
        }
        return result;
    }

    // drop nth bit, other bit shift left by 1 unit
    private int shiftLeftLogical(int num) {
        return (num & (0x7f ^ (1 << n))) << 1;
    }

    // get the ith bit of num
    public int getBit(int num, int i) {
        return (num >> i) & 1;
    }
}
