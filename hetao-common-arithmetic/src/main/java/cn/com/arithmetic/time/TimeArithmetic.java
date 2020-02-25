package cn.com.arithmetic.time;

import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 17:02
 *@desc
 **/
public interface TimeArithmetic {

    final static int rangeAm = 1;
    final static int rangePm = 2;
    final static int rangeAll = 3;
    final static String timeKey = "time";
    final static String rangeKey = "range";

    /**
     * 这个是计算时间的算法
     * @param startTime 开始计算的时间
     * @param endTime 结束计算的时间
     * @param excludTime 剔除的时间 time的键表示时间，range键是范围，其中表示下午还是上午还是整天
     * @param amStart 上午开始的时间
     * @param amEnd 上午结束时间
     * @param pmStart 下午开始时间
     * @param pmEnd 下午结束时间
     * @return
     */
    public long caculateTime(Date startTime, Date endTime, List<Map<String, Object>> excludTime, String amStart, String amEnd, String pmStart, String pmEnd);

    /**
     * 这个是计算上午的时间
     * @param startTime
     * @param endTime
     * @param amStart
     * @param amEnd
     * @return
     */
    public long caculateTimeSplit(Date startTime, Date endTime, String amStart, String amEnd);

}
