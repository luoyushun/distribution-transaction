package cn.com.hetao.netty;

import io.netty.channel.ChannelFuture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 13:27
 *@desc
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NettyClientBean implements Serializable {

    private String ip;
    private Integer port;
    private ChannelFuture channelFuture;

}
