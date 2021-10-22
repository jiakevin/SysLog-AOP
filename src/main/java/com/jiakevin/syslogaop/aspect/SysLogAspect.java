package com.jiakevin.syslogaop.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiakevin.syslogaop.annotation.SysLog;
import com.jiakevin.syslogaop.entity.SysLogBO;
import com.jiakevin.syslogaop.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiakevin
 * @since 2021-10-21 23:09
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    private final SysLogService sysLogService;

    public SysLogAspect(SysLogService service) {
        this.sysLogService = service;
    }

    @Pointcut("@annotation(com.jiakevin.syslogaop.annotation.SysLog)")
    public void logPointCut() { /* place hold method  */ }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try {
            saveLog(point, time);
        } catch (Exception e) {
            log.error(String.format("Can not save log info. %s", e.getMessage()));
        }
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogBO sysLogBO = new SysLogBO();
        sysLogBO.setExecTime(time);
        sysLogBO.setCreateDate(LocalDateTime.now());

        SysLog sysLog = method.getAnnotation(SysLog.class);
        if(sysLog != null){
            sysLogBO.setRemark(sysLog.value());
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogBO.setClassName(className);
        sysLogBO.setMethodName(methodName);

        Object[] args = joinPoint.getArgs();
        try{
            List<String> list = new ArrayList<>();
            for (Object o : args) {
                list.add(new ObjectMapper().writeValueAsString(o));
            }
            sysLogBO.setParams(list.toString());
        }catch (Exception e){
            log.error(String.format("Can not save log info. %s", e.getMessage()));
        }
        sysLogService.save(sysLogBO);
    }
}
