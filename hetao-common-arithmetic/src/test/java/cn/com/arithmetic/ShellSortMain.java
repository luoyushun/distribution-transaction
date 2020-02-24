package cn.com.arithmetic;

import cn.com.arithmetic.entity.DataEntity;
import cn.com.arithmetic.entity.DataEntityCompare;
import cn.com.arithmetic.sort.ShellSortCollection;
import cn.com.arithmetic.sort.shell.ShellSort;

import java.util.ArrayList;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 19:03
 *@desc
 **/
public class ShellSortMain {

    public static void main(String[] args) {
        List<DataEntity> dataEntities = new ArrayList<DataEntity>();
        DataEntityCompare selfCompare = new DataEntityCompare();
        for (int i=0; i< 100; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId((long) i);
            dataEntity.setName("" + Math.floor(Math.random()*100));
            dataEntities.add(dataEntity);
        }
        List<Integer> dks = new ArrayList<Integer>();
        dks.add(3);
        dks.add(2);
        dks.add(1);
        ShellSortCollection shellSortCollection = new ShellSort();
        shellSortCollection.shellSort(dataEntities, dks, selfCompare);
        System.out.println(dataEntities);
    }

}
