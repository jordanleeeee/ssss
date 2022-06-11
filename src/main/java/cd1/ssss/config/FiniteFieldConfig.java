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
        return new GF2PowerOfN(8, 27);  // 1+x+x^3+x^4+x^8
    }

    @Bean
    public GF2PowerOfN extensionField32() {
        return new GF2PowerOfN(5, 5);   // 1+x^2+x^5, 1+x+x^2+x^3+x^5, 1+x^3+x^5, 1+x+x^3+x^4+x^5, 1+x^2+x^3+x^4+x^5, 1+x+x^2+x^4+x^5
    }

    @Bean
    public GF2PowerOfN extensionField16() {
        return new GF2PowerOfN(4, 3);   // 1+x+x^4, 1+x+x^2+x^3+x^4, 1+x^3+x^4
    }

    @Bean
    public GF2PowerOfN extensionField8() {
        return new GF2PowerOfN(3, 3);   // 1+x+x^3, 1+x^2+x^3
    }

    @Bean
    public GF2PowerOfN extensionField4() {
        return new GF2PowerOfN(2, 3);   // 1+x+x^2
    }

    @Bean
    public GF2PowerOfN extensionField2() {
        return new GF2PowerOfN(1, 1);   // 1+x, x
    }
}
