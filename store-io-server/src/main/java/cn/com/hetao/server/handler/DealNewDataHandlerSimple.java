package cn.com.hetao.server.handler;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.io.config.KeyObjectDefination;
import cn.com.hetao.mapper.StoreMapper;
import cn.com.hetao.mapper.StoreMapperImpl;
import cn.com.hetao.server.entity.NoticeEntity;
import cn.com.hetao.server.start.ConnectClientOperator;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/10 16:09
 *@desc 这个是数据的处理类
 **/
@Component
public class DealNewDataHandlerSimple implements DealNewDataHandler {

    @Resource
    private ConnectClientOperator operator;

    private StoreMapper storeMapper = new StoreMapperImpl();

    @Override
    public boolean dealData(NoticeEntity entity) {
        KeyObjectDefination defination = StoreBean.keyBeans.get(entity.getKey());
        if (defination != null) return false;
        boolean c = storeMapper.setNXValue(entity.getKey(), entity, entity.getTimeout(), StoreBean.comparator);
        if (c) {
            operator.sendMessages(entity);
        }
        return true;
    }

    @Override
    public boolean dealData(ChannelHandlerContext chc, NoticeEntity entity) {
        KeyObjectDefination defination = StoreBean.keyBeans.get(entity.getKey());
        if (defination != null) return false;
        boolean c = storeMapper.setNXValue(entity.getKey(), entity, entity.getTimeout(), StoreBean.comparator);
        if (c) {
            operator.sendMessages(entity);
            StoreBean.responseBody.put(entity.getId(), chc);
        }
        return true;
    }
}
