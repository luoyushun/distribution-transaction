package cn.com.hetao.server.entity;

import io.netty.channel.ChannelFuture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/8 19:29
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NettyClientBean implements Serializable {

    private String url;
    private ChannelFuture future;

}
