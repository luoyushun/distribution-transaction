package cn.com.common.hetao.enums;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/19 18:38
 *@desc
 **/
public enum LockStatus {
    FAILURE(1),
    SUCCESS(2),
    LOCKING(4),
    CANCEL(5),
    TIMEOUT(6),
    RELEASE(7),
    REQUEST(8),
    UNKWON(3);
    private Integer value;
    private LockStatus(Integer value) {
        this.value = value;
    }
    public Integer value() {
        return this.value;
    }
}
