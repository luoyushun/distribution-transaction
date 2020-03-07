package cn.com.hetao.entity;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/5 19:03
 *@desc 比较类
 **/
public class ValueObjectSort implements Comparator<ValueObjectDefination> {
    @Override
    public int compare(ValueObjectDefination o1, ValueObjectDefination o2) {
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        if (o1.getLastAccessTime().getTime() > o2.getLastAccessTime().getTime()) return 1;
        if (o1.getLastAccessTime().getTime() == o2.getLastAccessTime().getTime()) return 0;
        if (o1.getLastAccessTime().getTime() < o2.getLastAccessTime().getTime()) return -1;
        return 0;
    }
}
