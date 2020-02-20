package cn.com.hetao.transaction;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 13:31
 *@desc
 **/
public abstract class ReceiptDistributionAbs implements ReceiptDistribution {

    public <T> boolean sendMessage(ChannelHandlerContext ctx, T t) throws UnsupportedEncodingException {
//        String result = JSONObject.toJSONString(t);
//        ByteBuf byteBuf = ctx.alloc().buffer(result.getBytes("UTF-8").length);
//        byteBuf.writeBytes(result.getBytes("UTF-8"));
        ctx.writeAndFlush(t);
        return true;
    }

}
