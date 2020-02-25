package cn.com.arithmetic;

import cn.com.arithmetic.binary.BinarySubSimple;
import cn.com.arithmetic.entity.BinarySubDataStructure;
import cn.com.arithmetic.entity.DataEntity;
import cn.com.arithmetic.entity.DataEntityCompare;

import java.util.Scanner;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 15:22
 *@desc
 **/
public class BinarySubMain {

    public static void main(String[] args) {
        DataEntityCompare selfCompare = new DataEntityCompare();
        BinarySubDataStructure<DataEntity> root = new BinarySubDataStructure<DataEntity>();
        root.setThird(null);
        root.setSecond(null);
        root.setFirst(null);
        root.setFourth(null);
        root.setParent(null);
        root.setFirstData(null);
        root.setSecondData(null);
        root.setFirstData(null);
        Scanner scanner = new Scanner(System.in);
        BinarySubSimple subSimple = new BinarySubSimple();
        while (true) {
            scanner.nextLine();
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId(0L);
            dataEntity.setName("" + Math.floor(Math.random()*100));
            BinarySubDataStructure<DataEntity> datas = new BinarySubDataStructure<DataEntity>();
            datas.setThird(null);
            datas.setSecond(null);
            datas.setFirst(null);
            datas.setFourth(null);
            datas.setParent(null);
            datas.setFirstData(null);
            datas.setSecondData(null);
            datas.setFirstData(null);
            datas.setFirstData(dataEntity);
            datas.setLength(1);
            if (root.getFirst() == null) {
                root.setThird(datas);
                root.setSecond(datas);
                root.setFirst(datas);
                root.setFourth(datas);
                datas.setParent(root);
            } else {
                subSimple.insertElement(root.getFirst(), dataEntity, selfCompare);
            }
//            System.out.println(root);
            System.out.println("hello world");
        }
    }

}
