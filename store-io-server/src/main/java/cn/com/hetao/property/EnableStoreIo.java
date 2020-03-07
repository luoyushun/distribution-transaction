package cn.com.hetao.property;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/5 20:36
 *@desc 这个是启动保存的io
 **/
@ComponentScan(basePackages = {"cn.com.hetao.property", "cn.com.hetao.config"})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableConfigurationProperties(StoreProperty.class)
@Configuration
public @interface EnableStoreIo {
}
