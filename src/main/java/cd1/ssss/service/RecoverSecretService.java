package cd1.ssss.service;

import cd1.ssss.finitefield.FiniteField;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jordan
 */
@Component
public class RecoverSecretService {
    @Resource(name = "extensionField256")
    FiniteField finiteField;

    public int recoverSecret(List<int[]> shares) {
        int result = 0;

        for (int i = 0; i < shares.size(); i++) {
            int temp = shares.get(i)[1];
            for (int j = 0; j < shares.size(); j++) {
                if (i != j) {
                    int fraction = finiteField.divide(shares.get(j)[0], finiteField.minus(shares.get(j)[0], shares.get(i)[0]));
                    temp = finiteField.multiply(temp, fraction);
                }
            }
            result = finiteField.add(result, temp);
        }
        return result;
    }

    public byte[] recoverStringSecret(List<TextShare> shares) {
        int length = shares.get(0).bytes.length;
        int t = shares.size();

        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            List<int[]> unitShare = new ArrayList<>(t);
            for (TextShare share : shares) {
                int unsignedIntShare = share.bytes[i] & 0xff;
                unitShare.add(new int[]{share.x, unsignedIntShare});
            }
            result[i] = (byte) recoverSecret(unitShare);
        }
        return result;
    }
}
