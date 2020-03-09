package cn.com.hetao.server.event;

import cn.com.hetao.server.entity.NoticeEntity;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 16:59
 *@desc 这个是内部的通知事务
 **/
public interface NoticeEventInner {

    /**
     * 这个是通知事务的内部
     * @param noticeEntity
     */
    public void noticeEvent(NoticeEntity noticeEntity);

}
