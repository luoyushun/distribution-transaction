package cn.com.client.hetao.enables;

import cn.com.client.hetao.config.NettyClientProperty;
import cn.com.client.hetao.config.TransactionProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 19:29
 *@desc
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableConfigurationProperties(TransactionProperty.class)
@ComponentScan(basePackages = {"cn.com.client.hetao"})
@Configuration
public @interface EnableTransaction {
}
