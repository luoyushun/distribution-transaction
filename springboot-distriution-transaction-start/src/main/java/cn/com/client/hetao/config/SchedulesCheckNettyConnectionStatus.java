package cn.com.client.hetao.config;

import cn.com.client.hetao.startserver.NettyServerStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 19:56
 *@desc
 **/
@EnableScheduling
@Component
public class SchedulesCheckNettyConnectionStatus {

    @Scheduled(fixedRate = 10000)
    public void checkServerStatus() {
        if (!NettyServerStart.isStart) return;
        if (NettyServerStart.isOver) {
            try {
                Thread.sleep(20000);
            }catch (Exception e) {
                e.printStackTrace();
            }
            boolean c = NettyServerStart.connect(0);
            if (c) {
                NettyServerStart.isOver = false;
                NettyServerStart.isStart = true;
            } else {
                NettyServerStart.isOver = true;
                NettyServerStart.isStart = false;
            }
        }
    }

}
