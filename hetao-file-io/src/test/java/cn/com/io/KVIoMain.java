package cn.com.io;

import cn.com.hetao.io.config.FileOperatorConfig;
import cn.com.hetao.io.operator.vkio.impl.ValueKeyAdaptor;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/27 12:10
 *@desc
 **/
public class KVIoMain {

    public static void main(String[] args) {

        ValueKeyAdaptor valueKeyAdaptor = new ValueKeyAdaptor();
        FileOperatorConfig.dataPath = "D:\\hetao\\datas";
        FileOperatorConfig.dataTempPath = "D:\\hetao\\temp";
        FileOperatorConfig.index = "D:\\hetao\\index";

        for (int i = 0; i < 100; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId(0L);
            dataEntity.setName("世纪、你好" + Math.random() * 100);

            try {
                boolean c = valueKeyAdaptor.setValue("hello" + i, dataEntity);
                System.out.println(c);
                dataEntity = valueKeyAdaptor.getValue("hello" + i, dataEntity);
                System.out.println(dataEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
