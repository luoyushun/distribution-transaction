package cn.com.arithmetic.balance;

import cn.com.arithmetic.entity.TwinBalanceData;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 11:09
 *@desc
 **/
public interface TwinBalanceArithmetic {

    final static int LH= 1;
    final static int EH = 0;
    final static int RH = -1;

    /**
     * 这个是右旋
     * @param twinBalanceData
     */
    public void rightRotate(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator);

    /**
     * 这个是左旋
     * @param twinBalanceData
     */
    public void leftRotate(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator);

    /**
     * 这个是插入元素信息
     * @param parent
     * @param element
     * @param comparator
     * @return
     */
    public boolean insertElement(TwinBalanceData parent, TwinBalanceData element, Comparator<TwinBalanceData> comparator);

    /**
     * 这个左平衡
     * @param twinBalanceData
     */
    public void leftBalance(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator);

    /**
     * 这个是插入数据是进行修改对应的值的
     * @param twinBalanceData
     * @param comparator
     */
    public void leftBalanceElement(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator);

    /**
     * 这个是右平衡信息
     * @param twinBalanceData
     */
    public void rightBalance(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator);

    /**
     * 这个是插入数据是进行修改对应的值的
     * @param twinBalanceData
     * @param comparator
     */
    public void rightBalanceElement(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator);

    /**
     * 这个是浏览数据信息
     * @param twinBalanceData
     */
    public void broswerBalanceMidle(TwinBalanceData twinBalanceData);

    /**
     * 这个是删除数据信息
     * @param parent
     * @param element
     * @param comparator
     */
    public void removeElement(TwinBalanceData parent, TwinBalanceData element, Comparator<TwinBalanceData> comparator);

    /**
     * 这个是查询数据信息
     * @param parent
     * @param element
     * @param comparator
     * @return
     */
    public TwinBalanceData findElement(TwinBalanceData parent, TwinBalanceData element, Comparator<TwinBalanceData> comparator);

    /**
     * 获取根
     * @param root
     * @return
     */
    public TwinBalanceData getRoot(TwinBalanceData root);

}
