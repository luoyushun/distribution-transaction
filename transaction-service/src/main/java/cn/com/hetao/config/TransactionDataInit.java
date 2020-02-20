package cn.com.hetao.config;

import org.springframework.beans.factory.InitializingBean;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/20 15:14
 *@desc
 **/
public class TransactionDataInit implements InitializingBean {
    /**
     * 进行初始化线程数量，线程数量和cup核数的两倍
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        int cupNum = Runtime.getRuntime().availableProcessors();
    }
}
