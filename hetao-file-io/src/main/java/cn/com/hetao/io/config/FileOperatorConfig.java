package cn.com.hetao.io.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 13:43
 *@desc 这个是文件操作的配置文件
 **/
public class FileOperatorConfig implements Serializable {

    /**
     * 数据路径
     */
    public static String dataPath;

    /**
     * 数据临时数据类型
     */
    public static String dataTempPath;

    /**
     * 文件大小
     */
    public static Long fileSize;

    /**
     * 索引文件的存储路径
     */
    public static String index;

}
