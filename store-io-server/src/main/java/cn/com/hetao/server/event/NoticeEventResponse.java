package cn.com.hetao.server.event;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.entity.ValueObjectDefination;
import cn.com.hetao.server.entity.NoticeEntity;
import cn.com.hetao.server.enums.NoticeStartEnum;
import cn.com.hetao.server.enums.RequestStatusEnum;
import io.netty.channel.ChannelHandlerContext;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/10 16:22
 *@desc
 **/
public class NoticeEventResponse implements NoticeEvent {

    @Override
    public void noticeListen(String key, Long id, RequestStatusEnum status) {
        ValueObjectDefination defination = StoreBean.valueBeans.get(key);
        ChannelHandlerContext ctx = StoreBean.responseBody.get(id);
        NoticeEntity entity = null;
        if (defination.getValue() instanceof NoticeEntity) {
            entity = (NoticeEntity) defination.getValue();
        }
        if (entity == null) return;
        entity.setStatus(status);
        entity.setIsStart(NoticeStartEnum.RESULT);
        ctx.writeAndFlush(entity);
    }

}
