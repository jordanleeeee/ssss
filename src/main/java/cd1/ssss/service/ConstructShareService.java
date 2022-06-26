package cd1.ssss.service;

import cd1.ssss.finitefield.FiniteField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Jordan
 */
@Component
public class ConstructShareService {
    @Resource(name = "extensionField256")
    FiniteField finiteField;

    public int[] constructPoints(int secret, int n, int t) {
        int[] coefficient = new int[t];

        coefficient[0] = secret;
        for (int i = 1; i < coefficient.length; i++) {
            coefficient[i] = finiteField.randElement(true);
        }

        int[] shares = new int[n];
        for (int i = 1; i <= shares.length; i++) {
            shares[i - 1] = getYCCord(i, coefficient);
        }
        return shares;
    }

    public byte[][] constructTextShares(byte[] secret, int n, int t) {
        byte[][] shares = new byte[n][secret.length];

        for (int i = 0; i < secret.length; i++) {
            int unsignedIntSecret = secret[i] & 0xff; // byte to unsigned int,
            int[] coefficient = new int[t];
            coefficient[0] = unsignedIntSecret;
            for (int j = 1; j < coefficient.length; j++) {
                coefficient[j] = finiteField.randElement(true);
            }

            for (int j = 1; j <= shares.length; j++) {
                shares[j - 1][i] = (byte) getYCCord(j, coefficient);
            }
        }
        return shares;
    }

    private int getYCCord(int x, int[] coefficient) {
        // y = f(x), input x, return y
        int val = 1;
        int result = 0;
        for (int coef : coefficient) {
            result = finiteField.add(result, finiteField.multiply(coef, val));
            val = finiteField.multiply(val, x);
        }
        return result;
    }
}
