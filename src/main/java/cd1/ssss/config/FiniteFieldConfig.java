package cd1.ssss.config;

import cd1.ssss.finitefield.GF2PowerOfN;
import cd1.ssss.finitefield.PrimeField;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jordan
 */
@Configuration
public class FiniteFieldConfig {
    @Bean
    public PrimeField primeField() {
        return new PrimeField(13);
    }

    @Bean
    public GF2PowerOfN extensionField256() {
        return new GF2PowerOfN(8, 27);
    }

    @Bean
    public GF2PowerOfN extensionField32() {
        return new GF2PowerOfN(5, 5);
    }

    @Bean
    public GF2PowerOfN extensionField16() {
        return new GF2PowerOfN(4, 3);
    }

    @Bean
    public GF2PowerOfN extensionField8() {
        return new GF2PowerOfN(3, 3);
    }

    @Bean
    public GF2PowerOfN extensionField4() {
        return new GF2PowerOfN(2, 3);
    }

    @Bean
    public GF2PowerOfN extensionField2() {
        return new GF2PowerOfN(1, 1);
    }
}
