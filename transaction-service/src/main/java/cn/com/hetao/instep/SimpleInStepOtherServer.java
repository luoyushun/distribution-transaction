package cn.com.hetao.instep;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.common.hetao.enums.InStepStatus;
import cn.com.common.hetao.enums.LockStatus;
import cn.com.hetao.config.TransactionContainorBean;
import cn.com.hetao.netty.NettyClientBean;
import org.springframework.stereotype.Component;

import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 14:38
 *@desc 同步数据与其他服务器
 **/
@Component
public class SimpleInStepOtherServer implements InStepOtherServer {

    @Override
    public boolean inStepOneToOtherServer(TransactionDefinationEntity entity) throws Exception {
        for (String address : TransactionContainorBean.servers) {
            String[] ads = address.split(":");
            String ip = ads[0];
            Integer port = Integer.parseInt(ads[1]);
            inStepOneToPointServer(entity, ip, port);
        }
        return false;
    }

    @Override
    public boolean inStepListsToOtherServer(List<TransactionDefinationEntity> entities) throws Exception {
        for (TransactionDefinationEntity entity: entities) {
            inStepOneToOtherServer(entity);
        }
        return false;
    }

    @Override
    public boolean inStepListsToPointServer(List<TransactionDefinationEntity> entities, String ip, Integer port) throws Exception {
        for (TransactionDefinationEntity entity: entities) {
            inStepOneToPointServer(entity, ip, port);
        }
        return false;
    }

    @Override
    public boolean inStepOneToPointServer(TransactionDefinationEntity entity, String ip, Integer port) throws Exception {
        String address = ip + ":" + port;
        NettyClientBean nettyClientBean = TransactionContainorBean.nettyClinetBean.get(address);
        if (nettyClientBean == null) return false;
        entity.setInStepStatus(InStepStatus.GAINRESOURCE.value().intValue());
        entity.setLockStatus(LockStatus.INSTEP.value().intValue());
        nettyClientBean.getChannelFuture().channel().writeAndFlush(entity);
        return true;
    }
}
