package cn.com.client.hetao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 19:26
 *@desc
 **/
public class NettyClientProperty {

    public static volatile String urls;

    public static volatile Integer priority;

    public static volatile String applicationName;

}
