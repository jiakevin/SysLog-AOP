package com.jiakevin.syslogaop.service;

import com.jiakevin.syslogaop.entity.SysLogBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author jiakevin
 * @since 2021-10-21 23:04
 */
@Service
@Slf4j
public class SysLogService {
    public boolean save(SysLogBO sysLogBO) {
        log.info(String.format("log SysLogBO {}: %s", sysLogBO));
        return true;
    }
}
