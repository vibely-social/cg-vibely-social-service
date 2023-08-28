package com.cg_vibely_social_service.logging;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class UtilsLogger {
    private static final Logger LOGGER = LogManager.getLogger(AppLogger.class);

    @Pointcut("execution(public * com.cg_vibely_social_service.utils.*.*(..))")
    public void utilsMethods() {}

    @AfterThrowing(value = "utilsMethods()", throwing = "e")
    public void utilsLogging(JoinPoint joinPoint, Exception e) {
        LOGGER.warn(String.format("```yaml\n!##EXCEPTION: %s.%s() !ARGS: %s!```",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())));
        LOGGER.error("||[" + e + "]||");
    }
}
