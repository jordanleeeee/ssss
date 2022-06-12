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

    @Around("execution(* cd1.ssss.controller.*.*(..))")
    public Object timeTracker(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        boolean success = false;
        try {
            logger.info("received request: {}", joinPoint.getSignature());
            Object proceed = joinPoint.proceed();
            success = true;
            return proceed;
        } finally {
            logger.info("request {}, elapse={}", success ? "success" : "failed", System.nanoTime() - start);
        }
    }

    @Around("execution(* cd1.ssss.controller.*.*(..))")
    public Object errorHandler(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Throwable e) {
            logger.error("unexpected error", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
