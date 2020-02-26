package cn.com.hetao.io;

import cn.com.common.hetao.entity.fileio.FileConfig;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/26 13:21
 *@desc 这个是文件工程，这里有设置文件的配置文件等其他信息
 **/
public interface FileIoFactoryConfig {

    public void setConfig(FileConfig fileConfig);

    public void setOperatorFile();

}
