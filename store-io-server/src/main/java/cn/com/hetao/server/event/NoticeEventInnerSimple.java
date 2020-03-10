package cn.com.hetao.server.event;

import cn.com.hetao.config.StoreBean;
import cn.com.hetao.server.entity.NoticeEntity;
import cn.com.hetao.server.enums.RequestStatusEnum;

import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 17:59
 *@desc 这个是处理数据的
 **/
public class NoticeEventInnerSimple implements NoticeEventInner {

    @Override
    public void noticeEvent(NoticeEntity noticeEntity) {
        List<NoticeEntity> entities = StoreBean.noticeStatus.get(noticeEntity.getId());
        if (entities == null) return;
        boolean c = true;
        for (NoticeEntity entity : entities) {
            if (entity.getIp().equals(noticeEntity.getId())) {
                entity.setStatus(noticeEntity.getStatus());
            }
            if (entity.getStatus() == RequestStatusEnum.SUCCESS) {
                c = c && true;
            } else if (entity.getStatus() == RequestStatusEnum.FAIL) {
                StoreBean.noticeStatus.remove(noticeEntity.getId());
                // 这里直接返回结果，不必在乎接下来的任何处理了。
                c = c && false;
                break;
            } else {
                c = c && false;
                // 这里是出现了不能处理的事务的处理信息
                break;
            }
        }
        // 这里是处理通知事务的数据
        // 这里情况数据
        StoreBean.noticeStatus.remove(noticeEntity.getId());
        RequestStatusEnum statusEnum = c?RequestStatusEnum.SUCCESS:RequestStatusEnum.FAIL;
        for (NoticeEvent event : StoreBean.registerEvent) {
            event.noticeListen(noticeEntity.getKey(), noticeEntity.getId(), statusEnum);
        }
    }

}
