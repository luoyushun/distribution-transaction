package cn.com.client.hetao.handler;

import cn.com.client.hetao.entity.TransactionExtDefination;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 19:35
 *@desc 这个类是处理数据等待的情况的
 **/
public interface DealWaitDataHandler {

    public boolean waitDataHandler(TransactionExtDefination extDefination);

}
