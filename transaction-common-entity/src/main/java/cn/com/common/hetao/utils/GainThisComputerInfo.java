package cn.com.common.hetao.utils;

import java.net.InetAddress;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 15:54
 *@desc
 **/
public class GainThisComputerInfo {

    public static String getIp() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostAddress();
        }catch (Exception e) {
            return null;
        }
    }

}
