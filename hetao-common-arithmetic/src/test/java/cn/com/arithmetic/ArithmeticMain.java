package cn.com.arithmetic;

import cn.com.arithmetic.balance.TwinBalanceArithmetic;
import cn.com.arithmetic.balance.TwinBalanceArithmeticSimple;
import cn.com.arithmetic.entity.DataEntity;
import cn.com.arithmetic.entity.SelfCompare;
import cn.com.arithmetic.entity.TwinBalanceData;

import java.util.Comparator;
import java.util.Scanner;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 12:18
 *@desc
 **/
public class ArithmeticMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TwinBalanceData<DataEntity> root = new TwinBalanceData<DataEntity>();
        root.setParent(null);
        root.setLeftChild(null);
        root.setRightChild(null);
        TwinBalanceArithmetic twinBalanceArithmetic = new TwinBalanceArithmeticSimple();
        Comparator comparator = new SelfCompare();
        while (true) {
            System.out.println("请输入数据");
            String name = scanner.nextLine();
            String[] comends = name.split(" ");
            String commend = comends[0];
            String value = comends[1];
            TwinBalanceData<DataEntity> temp = ceateElement(value);
            if (commend.equals("add")) {
                if (root.getRightChild() == null) {
                    root.setRightChild(temp);
                    root.setLeftChild(temp);
                    temp.setParent(root);
                } else {
                    twinBalanceArithmetic.insertElement(root.getRightChild(), temp, comparator);
                }
            } else if (commend.equals("delete")) {
                twinBalanceArithmetic.removeElement(root.getRightChild(), temp, comparator);
            } else if (commend.equals("find")) {
                TwinBalanceData twinBalanceData1 = twinBalanceArithmetic.findElement(root.getRightChild(), temp, comparator);
                if (twinBalanceData1 == null) continue;
                System.out.println(twinBalanceData1.getDate().toString());
            }
            twinBalanceArithmetic.broswerBalanceMidle(root.getRightChild());
        }
    }

    public static TwinBalanceData<DataEntity> ceateElement(String name) {
        TwinBalanceData<DataEntity> data = new TwinBalanceData<DataEntity>();
        DataEntity dataEntity = new DataEntity();
        dataEntity.setId(0L);
        dataEntity.setName(name);
        data.setDate(dataEntity);
        data.setRightChild(null);
        data.setLeftChild(null);
        data.setBalanceLeef(TwinBalanceArithmetic.EH);
        return data;
    }

}
