package cn.com.transaction.controller;

import cn.com.client.hetao.startserver.SimpleNettyServerDeal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 22:44
 *@desc
 **/
@RestController
public class TransactionController {

    @RequestMapping("/hello")
    public String helloWorld(String resourceId) throws InterruptedException {
        SimpleNettyServerDeal simpleNettyServerDeal = new SimpleNettyServerDeal();
        boolean c = simpleNettyServerDeal.lock(resourceId, "ok");
//        try{
//            Thread.sleep(1000);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println(Thread.currentThread().getName() + "======" + c);
        simpleNettyServerDeal.unLock("hello");
        return "helloWorld";
    }

}
