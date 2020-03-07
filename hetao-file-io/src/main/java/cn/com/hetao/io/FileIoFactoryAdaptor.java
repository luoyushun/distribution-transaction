package cn.com.hetao.io;

import cn.com.common.hetao.entity.fileio.FileConfig;
import cn.com.hetao.io.config.FileOperatorConfig;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/7 14:09
 *@desc
 **/
public class FileIoFactoryAdaptor implements FileIoFactoryConfig {

    @Override
    public void setConfig(FileConfig fileConfig) {
        FileOperatorConfig.index = fileConfig.getIndexPath();
        FileOperatorConfig.fileSize = fileConfig.getMaxSize();
        FileOperatorConfig.dataPath = fileConfig.getDataPath();
        FileOperatorConfig.dataTempPath = fileConfig.getDateTempPath();
    }

    @Override
    public void setOperatorFile() {
        FileOperatorConfig.index = "./index";
        FileOperatorConfig.dataTempPath = "./temp";
        FileOperatorConfig.dataPath = "./data";
        FileOperatorConfig.fileSize = 1024*1024*1024L;
    }
}
