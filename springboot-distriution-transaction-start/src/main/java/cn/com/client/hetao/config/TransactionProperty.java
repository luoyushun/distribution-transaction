package cn.com.client.hetao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 21:44
 *@desc
 **/
@Data
@ConfigurationProperties(prefix="transaction.netty")
public class TransactionProperty {

    private String urls = "localhost:9999";

    private Integer priority = 30;

    private String applicationName = "default";

}
