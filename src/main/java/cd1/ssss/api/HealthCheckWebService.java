package cd1.ssss.api;

import cd1.ssss.util.Network;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jordan
 */
@RestController
public class HealthCheckWebService {
    @GetMapping("/ping")
    String ping(){
        return "pong: " + Network.LOCAL_HOST_NAME;
    }
}
