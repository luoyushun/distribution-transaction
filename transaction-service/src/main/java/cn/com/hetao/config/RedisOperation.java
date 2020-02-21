package cn.com.hetao.config;

import cn.com.common.hetao.entity.TransactionDefinationEntity;
import cn.com.common.hetao.utils.GainThisComputerInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/21 16:28
 *@desc
 **/
@Component
public class RedisOperation {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 这个是绑定的端口
     */
    @Value("${connection.port}")
    private Integer port;

    public boolean saveRedis(TransactionDefinationEntity entity) {
        String resourceId = entity.getResourcesId();
        String value = JSONObject.toJSONString(entity);
        boolean saveStatus = redisTemplate.opsForValue().setIfAbsent(resourceId, value);
        if (!saveStatus) {
            String redisValue = redisTemplate.opsForValue().get(resourceId);
            TransactionDefinationEntity definationEntity = JSONObject.toJavaObject(JSONObject.parseObject(redisValue), TransactionDefinationEntity.class);
            String ipAddress = definationEntity.getIp() + ":" + definationEntity.getPort().intValue();
            String selfAddress = GainThisComputerInfo.getIp() + ":" + port;
            for (String str : TransactionContainorBean.losedServer) {
                if (str.equals(ipAddress)) {
                    redisTemplate.delete(resourceId);
                    return redisTemplate.opsForValue().setIfAbsent(resourceId, value);
                }
            }
            if (ipAddress.equals(selfAddress)) {
                redisTemplate.delete(resourceId);
                return redisTemplate.opsForValue().setIfAbsent(resourceId, value);
            }
        } else {
            return true;
        }
        return false;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
