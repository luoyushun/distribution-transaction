package cn.com.hetao.config;

import cn.com.hetao.entity.ValueObjectDefination;
import cn.com.hetao.io.config.KeyObjectDefination;
import cn.com.hetao.io.operator.vkio.KVRefreshDiskFactory;
import cn.com.hetao.io.operator.vkio.KeyFactory;
import cn.com.hetao.io.operator.vkio.ValueFactory;
import cn.com.hetao.io.operator.vkio.ValueKeyFactory;
import cn.com.hetao.io.operator.vkio.impl.KVRefreshDiskAdaptor;
import cn.com.hetao.io.operator.vkio.impl.KeyAdaptor;
import cn.com.hetao.io.operator.vkio.impl.ValueAdaptor;
import cn.com.hetao.io.operator.vkio.impl.ValueKeyAdaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/29 15:01
 *@desc 这个是数据保存的数据信息
 **/
public class StoreBean {

    /**
     * 这个是键的bean的
     */
    public static ConcurrentMap<String, KeyObjectDefination> keyBeans = new ConcurrentHashMap<>();

    /**
     * 这个是bean的value的
     */
    public static ConcurrentMap<String, ValueObjectDefination> valueBeans = new ConcurrentHashMap<>();

    /**
     * 这个是value的值的链表
     */
    public static List<ValueObjectDefination> valueBeanList = new ArrayList<>();

    /**
     * 这个是刷盘机制的入口
     */
    public static KVRefreshDiskFactory kvRefreshDisk = new KVRefreshDiskAdaptor();

    /**
     * 这个是对数据进行操作的静态对象，可以用来处理数据
     */
    public static ValueKeyFactory valueKey = new ValueKeyAdaptor();

    /**
     * 这个是键的对象
     */
    public static KeyFactory keyFactory = new KeyAdaptor();

    /**
     * 这个保存数据的
     */
    public static ValueFactory valueFactory = new ValueAdaptor();

    public static Long cacheSize = 600*1024*1024L;

    public static ScheduledThreadPoolExecutor reflushDisk = null;

}
