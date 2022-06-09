package cd1.ssss.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Jordan
 */
@Aspect
@Configuration
public class AspectConfig {
    private final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Around(value = "execution(* cd1.ssss.controller.*.*(..))")
    public Object timeTracker(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            logger.info("received request: {}", joinPoint.getSignature());
            return joinPoint.proceed();
        } finally {
            logger.info("request completed, elapse={}", System.nanoTime() - start);
        }
    }

    @Around(value = "execution(* cd1.ssss.controller.*.*(..))")
    public Object errorHandler(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Throwable e) {
            logger.error("unexpected error, error={}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
