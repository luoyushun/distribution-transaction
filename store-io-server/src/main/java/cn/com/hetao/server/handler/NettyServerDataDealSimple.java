package cn.com.hetao.server.handler;

import cn.com.hetao.mapper.StoreMapper;
import cn.com.hetao.mapper.StoreMapperImpl;
import cn.com.hetao.server.entity.NoticeEntity;
import cn.com.hetao.server.enums.CommendsEnum;
import cn.com.hetao.server.enums.NoticeStartEnum;
import cn.com.hetao.server.enums.RequestStatusEnum;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/9 19:21
 *@desc 这个是数据处理类
 **/
public class NettyServerDataDealSimple implements NettyServerDataDeal {

    Log log = LogFactory.getLog(NettyServerDataDealSimple.class);
    private StoreMapper storeMapper = new StoreMapperImpl();

    @Override
    public void dataDeal(ChannelHandlerContext chc, NoticeEntity entity) {
        boolean c = false;
        if (CommendsEnum.SAVE == entity.getCommend()) {
            // 这里处理保存业务
            c = storeMapper.setValue(entity.getKey(), entity, entity.getTimeout());
        } else if (CommendsEnum.DELETE == entity.getCommend()) {
            // 这里处理删除业务
            c = storeMapper.deleteValue(entity.getKey());
        } else if (CommendsEnum.NX_SAVE == entity.getCommend()) {
            // 这里处理如果不存在就保存的业务
            c = storeMapper.setNXValue(entity.getKey(), entity, entity.getTimeout());
        } else if (CommendsEnum.EX_SAVE == entity.getCommend()) {
            // 这里是处理如果数据存在就修改数据
            c = storeMapper.setEXValue(entity.getKey(), entity, entity.getTimeout());
        } else if (CommendsEnum.FIND == entity.getCommend()) {
            // 这里处理查询数据的
            entity = (NoticeEntity) storeMapper.getValue(entity.getKey());
            if (entity == null) {
                entity = new NoticeEntity();
                c = false;
            } else {
                c = true;
            }
        } else {
            // 这里是其他情况的处理
            log.debug("不能处理该数据的请求" + entity.toString());
        }
        if (c) {
            entity.setStatus(RequestStatusEnum.SUCCESS);
        } else {
            entity.setStatus(RequestStatusEnum.FAIL);
        }
        entity.setIsStart(NoticeStartEnum.RESULT);
        chc.writeAndFlush(entity);
        chc.close();
    }

}
