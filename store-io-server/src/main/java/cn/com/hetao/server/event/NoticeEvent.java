package cn.com.hetao.server.event;

import cn.com.hetao.server.enums.RequestStatusEnum;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 16:53
 *@desc 这个是通知事务,这个是对外开放的通知事务
 **/
public interface NoticeEvent {

    /**
     * 这个是通知事件信息，这个是拿来进行处理的
     * @param key 这个是保存的键值
     * @param id 这个是请求资源的唯一标识
     * @param status 这个是状态
     */
    public void noticeListen(String key, Long id, RequestStatusEnum status);

}
