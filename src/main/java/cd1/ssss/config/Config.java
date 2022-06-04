package cd1.ssss.config;

import cd1.ssss.service.GF2PowerOfN;
import cd1.ssss.service.PrimeField;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jordan
 */
@Configuration
public class Config {
    @Bean
    public PrimeField primeField() {
        return new PrimeField(13);
    }

    @Bean
    public GF2PowerOfN extensionField() {
        return new GF2PowerOfN(8, new byte[]{0, 0, 0, 1, 1, 0, 1, 1});
    }
}
