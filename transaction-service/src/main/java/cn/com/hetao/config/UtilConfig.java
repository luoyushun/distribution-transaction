package cn.com.hetao.config;

import cn.com.common.hetao.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 14:31
 *@desc
 **/
@Configuration
public class UtilConfig {

    @Bean
    public IdWorker getIdWorker() {
        return new IdWorker(1,1,1);
    }

}
