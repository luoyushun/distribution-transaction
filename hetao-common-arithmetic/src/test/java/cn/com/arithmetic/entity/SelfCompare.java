package cn.com.arithmetic.entity;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 12:21
 *@desc
 **/
public class SelfCompare implements Comparator<TwinBalanceData<DataEntity>> {
    public int compare(TwinBalanceData<DataEntity> o1, TwinBalanceData<DataEntity> o2) {
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        return o1.getDate().getName().compareTo(o2.getDate().getName());
    }
}
