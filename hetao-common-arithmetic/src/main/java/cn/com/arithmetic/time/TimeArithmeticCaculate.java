package cn.com.arithmetic.time;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 17:11
 *@desc 这个是计算时间的算法
 **/
public class TimeArithmeticCaculate implements TimeArithmetic {

    public long caculateTime(Date startTime, Date endTime, List<Map<String, Object>> excludTime, String amStart, String amEnd, String pmStart, String pmEnd) {
        long result = 0L;
        while (true) {
            if (startTime.getTime() >= endTime.getTime()) break;
            long sTime = startTime.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            calendar.set(Calendar.HOUR, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            long eTime = calendar.getTimeInMillis();
            if (eTime > endTime.getTime()) {
                eTime = endTime.getTime();
            }
            int step = 0;
            for (Map<String, Object> entry : excludTime) {
                Integer range = (Integer) entry.get(TimeArithmetic.rangeKey);
                Date date = (Date) entry.get(TimeArithmetic.timeKey);
                if (date.getTime() < eTime && date.getTime() > sTime) {
                    step = range.intValue();
                    break;
                }
            }
            if (step == TimeArithmetic.rangeAll) continue;
            if (step == TimeArithmetic.rangeAm || step == 0) {
                long temp = caculateTimeSplit(new Date(sTime), new Date(eTime), pmStart, pmEnd);
                result = result + temp;
            }
            if (step == TimeArithmetic.rangePm || step == 0) {
                long temp = caculateTimeSplit(new Date(sTime), new Date(eTime), amStart, amEnd);
                result = result + temp;
            }
            startTime = new Date(eTime);
        }

        return result;
    }

    public long caculateTimeSplit(Date startTime, Date endTime, String amStart, String amEnd) {
        Calendar calendar = Calendar.getInstance();
        String[] times = amStart.split(":");
        calendar.set(Calendar.HOUR, Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND, 0);
        long start = calendar.getTimeInMillis();
        times = amEnd.split(":");
        calendar.set(Calendar.HOUR, Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND, 0);
        long end = calendar.getTimeInMillis();
        if (startTime.getTime() <= start && end <= endTime.getTime()) return end - start;
        if (startTime.getTime() <= start && end > endTime.getTime() && start < endTime.getTime()) return endTime.getTime() - start;
        if (start < startTime.getTime() && end > endTime.getTime()) return endTime.getTime() - startTime.getTime();
        if (start < startTime.getTime() && end <= endTime.getTime() && startTime.getTime() < end) return end - startTime.getTime();
        return 0;
    }
}
