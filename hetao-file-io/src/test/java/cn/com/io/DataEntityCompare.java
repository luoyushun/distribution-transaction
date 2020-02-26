package cn.com.io;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 17:10
 *@desc
 **/
public class DataEntityCompare implements Comparator<DataEntity> {
    public int compare(DataEntity o1, DataEntity o2) {
        Double n1 = Double.parseDouble(o1.getName());
        Double n2 = Double.parseDouble(o2.getName());
        if (n1.doubleValue() > n2.doubleValue()) return 1;
        else if (n1.doubleValue() == n2.doubleValue()) return 0;
        else return -1;
    }
}
