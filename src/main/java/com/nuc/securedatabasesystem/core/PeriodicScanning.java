package com.nuc.securedatabasesystem.core;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName PeriodicScanning
 * @Deacription TODO 定期扫描
 * @Author DeepBlue
 * @Date 2020/11/24 11:34
 * @Version 1.0
 **/
@Component
@Slf4j
public class PeriodicScanning {

    @Autowired
    private ClassScanUtil scanUtil;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Tool tool;

    private boolean flag = true;

    private ScheduledExecutorService scheduledExecutorService;

    public PeriodicScanning() {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    public void scan() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("进行检测--------------------------------------------");
            Set<Class> class4Annotation = scanUtil.getClass4Annotation("com.nuc.securedatabasesystem", Mapper.class);
            for (Class s : class4Annotation) {
                if ("com.nuc.securedatabasesystem.mapper.UserMapper".equals(s.getName())) {
                    continue;
                }
                BaseMapper baseMapper = (BaseMapper) applicationContext.getBean(s);
                List list = baseMapper.selectList(new QueryWrapper());
                for (int i = 0; i < list.size(); i++) {
                    try {
                        //重置Flag要不然一一直发邮件。
                        if (!tool.verify(list.get(i)) && flag) {
                            flag = false;
                            tool.sendMail(list.get(i));
                            log.error("数据被篡改！");
                            Thread.currentThread().interrupt();
                        }
                    } catch (Exception e) {
                        log.error("数据被篡改！");
                    }
                }
            }
        }, 1, 5, TimeUnit.SECONDS);
    }

}
