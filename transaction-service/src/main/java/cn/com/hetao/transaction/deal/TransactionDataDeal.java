package cn.com.hetao.transaction.deal;

import cn.com.hetao.netty.ContainorDataDeal;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 15:30
 *@desc 对数据的处理
 **/
public interface TransactionDataDeal {

    /**
     * 这个是请求锁的处理方法
     * @param dataDeal
     * @return
     */
    public boolean requestResource(ContainorDataDeal dataDeal);

    /**
     * 这个是查询那个锁可以获取资源
     * @param containorDataDeal
     * @return
     * @throws Exception
     */
    public boolean findGainResource(ContainorDataDeal containorDataDeal) throws Exception;

    /**
     * 这个是释放资源
     * @param containorDataDeal
     * @param status
     * @return
     * @throws Exception
     */
    public boolean releaseResource(ContainorDataDeal containorDataDeal, Integer status) throws Exception;

}
