package cn.com.hetao.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/*
 *@username LUOYUSHUN
 *@datetime 2020/3/5 19:53
 *@desc 这个是配置信息
 **/
@ConfigurationProperties(prefix = "store.hetao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreProperty implements Serializable {

    private Long cacheSize = 600*1024*1024L;
    private Long flushDisk = 3*1000L;
    private String dataTempPath = "./temp";
    private String dataPath = "./data";
    private String indexPath = "./index";
    private Long maxFileSize = 1024*1024*1024L;
    private String nettyServerAddr = "127.0.0.1:8809;127.0.0.1:8808";
    private String serverIp = "127.0.0.1";
    private Integer serverPort = 8809;
    private boolean enableServer = false;

}
