package cn.com.hetao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 14:01
 *@desc
 **/
@Configuration
public class ContainorConfig {

    @Bean
    public ExecutorService getExecutorThread() {
        return Executors.newCachedThreadPool();
    }
    
}
