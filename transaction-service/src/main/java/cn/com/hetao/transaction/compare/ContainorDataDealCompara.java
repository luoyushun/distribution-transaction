package cn.com.hetao.transaction.compare;

import cn.com.hetao.netty.ContainorDataDeal;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 16:09
 *@desc
 **/
public class ContainorDataDealCompara implements Comparator<ContainorDataDeal> {
    @Override
    public int compare(ContainorDataDeal o1, ContainorDataDeal o2) {
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        if (o1.getDefinationEntity().getPriority() > o2.getDefinationEntity().getPriority()) {
            return -1;
        }else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
