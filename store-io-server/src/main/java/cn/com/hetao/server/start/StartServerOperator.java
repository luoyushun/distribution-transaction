package cn.com.hetao.server.start;

import cn.com.hetao.property.StoreProperty;
import cn.com.hetao.server.impl.NettyServerSimple;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/10 17:22
 *@desc 这个是启动服务器的操作
 **/
@Component
public class StartServerOperator {

    @Resource
    private NettyServerSimple nettyServerSimple;

    @Resource
    private StoreProperty storeProperty;

    @PostConstruct
    public void initServer() {
        nettyServerSimple.startServer(storeProperty.getServerIp(), storeProperty.getServerPort().intValue());
    }

}
