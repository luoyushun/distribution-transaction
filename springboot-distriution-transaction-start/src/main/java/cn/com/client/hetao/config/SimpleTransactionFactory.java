package cn.com.client.hetao.config;

import cn.com.client.hetao.startserver.NettyServerStart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 21:49
 *@desc
 **/
@Configuration
@Slf4j
public class SimpleTransactionFactory implements TransactionFactory {

    @Autowired
    private TransactionProperty property;

    @Override
    public void initConfig(TransactionProperty property) {
        initConfig();
    }

    @PostConstruct
    @Override
    public void initConfig() {
        NettyClientProperty.applicationName = property.getApplicationName();
        NettyClientProperty.urls = property.getUrls();
        NettyClientProperty.priority = property.getPriority();
        log.info("数据初始化");
        NettyServerStart.init();
        log.info("Netty客户端初始化完毕");
    }
}
