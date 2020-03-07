package cn.com.common.hetao.entity.fileio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 13:22
 *@desc 这个是文件的相关配置信息
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileConfig implements Serializable {

    /**
     * 数据文件的路径
     */
    private String dataPath;

    /**
     * 这个是临时文件的路径信息
     */
    private String dateTempPath;

    /**
     * 这个是设置文件的大小，单位是字节
     */
    private Long maxSize;

    /**
     * 索引文件的保存路径
     */
    private String indexPath;

}
