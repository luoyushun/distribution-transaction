package cn.com.hetao.transaction.deal;

import cn.com.hetao.netty.ContainorDataDeal;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 15:30
 *@desc
 **/
public interface TransactionDataDeal {

    public boolean requestResource(ContainorDataDeal dataDeal);

    public boolean findGainResource(ContainorDataDeal containorDataDeal) throws Exception;

    public boolean releaseResource(ContainorDataDeal containorDataDeal, Integer status) throws Exception;

}
