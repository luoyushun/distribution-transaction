package cn.com.io;

import cn.com.hetao.io.config.FileOperatorConfig;
import cn.com.hetao.io.operator.simple.DataFileOperatorAbs;

import java.util.Scanner;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 16:30
 *@desc
 **/
public class FileIoMain {

    public static void main(String[] args) {
        FileOperatorConfig.dataPath = "D:\\hetao";
        FileOperatorConfig.dataTempPath = "D:\\hetao_temp";
        Scanner scanner = new Scanner(System.in);
        DataEntityCompare compare = new DataEntityCompare();
        DataEntity dataEntity = new DataEntity();
        dataEntity.setId(0L);
        dataEntity.setName("" + Math.random() * 100);

        DataFileOperatorAbs dataFileOperatorAbs = new DataFileOperatorAbs(){};
        try {
            dataFileOperatorAbs.addData(dataEntity, "hello", compare);
            DataEntity entity = dataFileOperatorAbs.findOneDatas(dataEntity, "hello", compare);
            System.out.println(entity);
            dataEntity.setId(100L);
            dataFileOperatorAbs.updateData(dataEntity, "hello", compare);
            entity = dataFileOperatorAbs.findOneDatas(dataEntity, "hello", compare);
            System.out.println(entity);
        }catch (Exception e) {
            e.printStackTrace();
        }
//        scanner.nextLine();
    }

}
