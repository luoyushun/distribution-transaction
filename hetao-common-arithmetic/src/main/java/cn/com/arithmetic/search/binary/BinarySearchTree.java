package cn.com.arithmetic.search.binary;

import cn.com.arithmetic.balance.TwinBalanceArithmetic;
import cn.com.arithmetic.balance.TwinBalanceArithmeticSimple;
import cn.com.arithmetic.entity.TwinBalanceData;
import cn.com.arithmetic.search.BinarySearchCollection;
import cn.com.arithmetic.search.SearchElementCollection;

import java.util.Comparator;
import java.util.List;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 11:56
 *@desc
 **/
public class BinarySearchTree<T> implements BinarySearchCollection<T> {

    private TwinBalanceData<T> root;
    private TwinBalanceArithmetic arithmetic = new TwinBalanceArithmeticSimple();

    public BinarySearchTree() {
        root = new TwinBalanceData<T>();
        root.setLeftChild(null);
        root.setRightChild(null);
        root.setParent(null);
    }

    private TwinBalanceData<T> createElement(T data) {
        TwinBalanceData<T> balanceData = new TwinBalanceData<T>();
        balanceData.setDate(data);
        balanceData.setParent(null);
        balanceData.setBalanceLeef(TwinBalanceArithmetic.EH);
        balanceData.setRightChild(null);
        balanceData.setLeftChild(null);
        return balanceData;
    }

    public T searchElement(List<T> datas, T element, Comparator<TwinBalanceData> comparator) {
        if (datas == null || datas.isEmpty() || element == null || comparator == null) return null;
        // 第一步构建平衡二叉树
        for (T entity : datas) {
            TwinBalanceData<T> ele = createElement(entity);
            if (root.getRightChild() == null || root.getLeftChild() == null) {
                ele.setParent(root);
                root.setLeftChild(ele);
                root.setRightChild(ele);
            } else {
                arithmetic.insertElement(root.getLeftChild(), ele, comparator);
            }
        }
        // 第二步查询数据
        TwinBalanceData<T> tTwinBalanceData = createElement(element);
        TwinBalanceData<T> result = arithmetic.findElement(root.getLeftChild(), tTwinBalanceData, comparator);
        return result == null ? null : result.getDate();
    }
}
