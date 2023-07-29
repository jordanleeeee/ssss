package cd1.ssss;

import cd1.ssss.config.FiniteFieldConfig;
import cd1.ssss.finitefield.GF2PowerOfN;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Jordan
 */
public class Debug {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(FiniteFieldConfig.class);
//        GF2PowerOfN bean = context.getBean(GF2PowerOfN.class); error
        GF2PowerOfN bean = context.getBean("extensionField256", GF2PowerOfN.class);
        System.out.println(bean);
    }
}
