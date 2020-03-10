package cn.com.hetao.server.start;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.property.StoreProperty;
import cn.com.hetao.server.entity.NettyClientBean;
import cn.com.hetao.server.entity.NoticeEntity;
import cn.com.hetao.server.enums.CommendsEnum;
import cn.com.hetao.server.enums.NoticeStartEnum;
import cn.com.hetao.server.enums.RequestStatusEnum;
import cn.com.hetao.server.impl.NettyClinetSimple;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 15:44
 *@desc
 **/
@Component
public class ConnectClientOperator {

    @Resource
    private StoreProperty storeProperty;

    @Resource
    private NettyClinetSimple nettyClient;

    /**
     * 这个是初始化数据
     */
    @PostConstruct
    public void init() {
        String[] stores = storeProperty.getNettyServerAddr().split(";");
        List<NettyClientBean> beans = nettyClient.connectNettyClient(stores);
        StoreBean.nettyClientBeans = beans;
        if (beans == null) {
            StoreBean.nettyClientBeans = new ArrayList<>();
        }
        for (String url : stores) {
            boolean c = true;
            for (NettyClientBean bean : StoreBean.nettyClientBeans) {
                if (url.equals(bean.getUrl())) {
                    c = false;
                    break;
                }
            }
            if (c) {
                StoreBean.lostNettyClient.add(url);
            }
        }
    }

    /**
     * 发送信息数据
     * @param noticeEntity
     */
    public void sendMessages(NoticeEntity noticeEntity) {
        List<NoticeEntity> noticeEntities = new ArrayList<>();
        for (NettyClientBean bean : StoreBean.nettyClientBeans) {
            sendOneMessagePointUrl(bean, noticeEntity);
            NoticeEntity entity = new NoticeEntity();
            entity.setId(noticeEntity.getId());
            entity.setIp(bean.getUrl());
            entity.setKey(noticeEntity.getKey());
            entity.setIsStart(NoticeStartEnum.SECOND);
            entity.setStatus(RequestStatusEnum.UNKNOWN);
            entity.setPriority(noticeEntity.getPriority());
            entity.setCommend(CommendsEnum.NX_SAVE);
            noticeEntities.add(entity);
        }
        StoreBean.noticeStatus.put(noticeEntity.getId(), noticeEntities);
    }

    /**
     * 这个是发送数据的
     * @param bean
     * @param noticeEntity
     */
    public void sendOneMessagePointUrl(NettyClientBean bean, NoticeEntity noticeEntity) {
        bean.getFuture().channel().writeAndFlush(noticeEntity);
    }

}
