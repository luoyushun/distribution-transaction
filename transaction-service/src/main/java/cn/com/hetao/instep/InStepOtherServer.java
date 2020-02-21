package cn.com.hetao.instep;

import cn.com.common.hetao.entity.TransactionDefinationEntity;

import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 13:19
 *@desc 和其他服务器进行通信
 **/
public interface InStepOtherServer {

    public boolean inStepOneToOtherServer(TransactionDefinationEntity entity) throws Exception;

    public boolean inStepListsToOtherServer(List<TransactionDefinationEntity> entities) throws Exception;

    public boolean inStepListsToPointServer(List<TransactionDefinationEntity> entities, String ip, Integer port) throws Exception;

    public boolean inStepOneToPointServer(TransactionDefinationEntity entity, String ip, Integer port) throws Exception;

}
