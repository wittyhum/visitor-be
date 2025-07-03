package com.qianwang.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MapperPerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(MapperPerformanceAspect.class);

    // 拦截所有 mapper 包下的接口方法
    @Around("execution(* com.qianwang.mapper.*.*(..))")
    public Object profileMapperMethods(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        long start = System.currentTimeMillis();

        try {
            return pjp.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            if (duration > 0) {
                logger.info("Mapper method: {} executed in {} ms", methodName, duration);
            }
        }
    }
}
