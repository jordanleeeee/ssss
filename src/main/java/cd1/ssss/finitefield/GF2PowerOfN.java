package cd1.ssss.finitefield;

import java.util.Random;

/**
 * @author Jordan
 */
public class GF2PowerOfN implements FiniteField {
    public static final Random RANDOM = new Random();
    private final int n;
    private final int size; // 2^n
    private final byte[] irreduciblePolynomials;

    public GF2PowerOfN(int n, byte[] irreduciblePolynomials) {
        if (irreduciblePolynomials.length != n) {
            throw new IllegalArgumentException("invalid irreducible polynomials");
        }
        this.n = n;
        this.size = (int) Math.pow(2, n);
        this.irreduciblePolynomials = irreduciblePolynomials;
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
        return toValue(multiply(toBinary(a), toBinary(b)));
    }

    private byte[] multiply(byte[] a, byte[] b) {
        //refer to page 25-26 of https://engineering.purdue.edu/kak/compsec/NewLectures/Lecture7.pdf
        if (a.length != b.length) {
            throw new IllegalStateException();
        }

        byte[] result = new byte[n];
        for (int i = n - 1; i >= 0; i--) {
            if (b[i] == 1) {
                // result += a
                result = exclusiveOr(result, a);
            }
            // perform a = a*x
            if (a[0] == 0) {
                a = shiftLeftLogical(a);
            } else {
                a = exclusiveOr(shiftLeftLogical(a), irreduciblePolynomials);
            }
        }
        return result;
    }

    @Override
    public int divide(int a, int b) {
        return toValue(divide(toBinary(a), toBinary(b)));
    }

    @Override
    public int randElement(boolean excludeZero) {
        return excludeZero ? RANDOM.nextInt(size - 1) + 1 : RANDOM.nextInt(size);
    }

    private byte[] divide(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new IllegalStateException();
        }
        // recall that a / b == a * inverseOf(b)
        // from wiki: the inverse of x is x^(p^n − 2).
        byte[] inverse = power(b, size - 2, n);
        return multiply(a, inverse);
    }

    private byte[] power(byte[] a, int index, int n) {
        byte[] result = new byte[n];
        System.arraycopy(a, 0, result, 0, n);

        for (int i = 0; i < index - 1; i++) {
            result = multiply(result, a);
        }
        return result;
    }

    private byte[] toBinary(int a) {
        String binaryString = Integer.toBinaryString(a);
        if (binaryString.length() > n) {
            throw new IllegalStateException("overflow occur");
        }

        byte[] binary = new byte[n];
        for (int i = binaryString.length() - 1, j = n - 1; i >= 0; i--, j--) {
            binary[j] = (byte) (binaryString.charAt(i) - '0');
        }
        return binary;
    }

    private int toValue(byte[] a) {
        StringBuilder binaryString = new StringBuilder();
        for (byte b : a) {
            binaryString.append(b);
        }
        return Integer.parseInt(binaryString.toString(), 2);
    }

    private byte[] exclusiveOr(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new IllegalStateException();
        }
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) ((a[i] == b[i]) ? 0 : 1);
        }
        return result;
    }

    // drop first bit, other bit shift left by 1 unit
    private static byte[] shiftLeftLogical(byte[] a) {
        byte[] result = new byte[a.length];
        if (a.length - 1 >= 0) System.arraycopy(a, 1, result, 0, a.length - 1);
        return result;
    }
}
