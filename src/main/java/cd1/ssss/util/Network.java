package cd1.ssss.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Jordan
 */
public class Network {
    public static final String LOCAL_HOST_ADDRESS;
    public static final String LOCAL_HOST_NAME;

    static {
        try {
            InetAddress address = InetAddress.getLocalHost();
            LOCAL_HOST_ADDRESS = address.getHostAddress();
            LOCAL_HOST_NAME = address.getHostName();
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
    }
}
