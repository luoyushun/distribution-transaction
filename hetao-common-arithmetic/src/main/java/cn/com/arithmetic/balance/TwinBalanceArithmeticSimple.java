package cn.com.arithmetic.balance;

import cn.com.arithmetic.entity.TwinBalanceData;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/24 11:15
 *@desc 这个是对平衡二叉树的简单实现
 *
 * 4、平衡二叉树
 * 平衡二叉树（Balanced Binary Tree或Height-Balanced Tree)又称AVL树。它或者是一棵空树，或者是具有下列性质的二叉树；他的左子树和右子树都是平衡二叉树，且
 * 左子树和右子树的深度之差的绝对值不超过1，若将二叉树上节点的平衡因子BF(Balance Factor)定义为该节点的左子树的深度减去他的左子树的深度，则平衡二叉树上所有
 * 节点的平衡因子只可能是-1， 0和1.只要二叉树上有一个节点的平衡因子的绝对值大于1，则该二叉树只可能就是不平衡了。
 *
 * 一、单向右旋平衡处理
 *                              ***                                              ***
 *                            *  A  *                                           * B *
 *                              ***                                              ***
 *                           *        *                                       *        *
 *                          *           *                                  *               *
 *                        ***            ***                  *         ***                 ***
 *                       * B *           * *          **********        * *                * A *
 *                        ***            * *                  *         * *                 ***
 *                      *      *         * *                            * *               *        *
 *                     *         *       * *                            * *            *               *
 *                   ***          ***    * *                            * *          ***                 ***
 *                   * *          * *    * *                            * *          * *                 * *
 *                   * *          ***    ***                            ***          ***                 ***
 *                   ***
 *
 **/
public class TwinBalanceArithmeticSimple implements TwinBalanceArithmetic {

    public void rightRotate(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator) {

        /**
         * 对数据进行获取原始数据
         */
        TwinBalanceData parent = twinBalanceData.getParent();
        TwinBalanceData lc = twinBalanceData.getLeftChild();
        if (parent != null && comparator.compare(parent.getLeftChild(), twinBalanceData) == 0) {
            if (parent.getParent() == null) {
                parent.setRightChild(lc);
                parent.setLeftChild(lc);
            } else {
                parent.setLeftChild(lc);
            }
        } else if (parent != null && comparator.compare(parent.getRightChild(), twinBalanceData) == 0) {
            if (parent.getParent() == null) {
                parent.setRightChild(lc);
                parent.setLeftChild(lc);
            } else {
                parent.setRightChild(lc);
            }
        } else if (parent != null){
            return;
        }
        twinBalanceData.setLeftChild(lc.getRightChild());
        lc.setRightChild(twinBalanceData);
        twinBalanceData.setParent(lc);
        lc.setParent(parent);

    }

    public void leftRotate(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator) {
        TwinBalanceData parent = twinBalanceData.getParent();
        TwinBalanceData rc = twinBalanceData.getRightChild();
        if (parent != null && comparator.compare(parent.getRightChild(), twinBalanceData) == 0) {
            if (parent.getParent() == null) {
                parent.setLeftChild(rc);
                parent.setRightChild(rc);
            }else {
                parent.setRightChild(rc);
            }
        } else if (parent != null && comparator.compare(parent.getLeftChild(), twinBalanceData) == 0) {
            if (parent.getParent() == null) {
                parent.setLeftChild(rc);
                parent.setRightChild(rc);
            } else {
                parent.setLeftChild(rc);
            }
        } else if (parent != null) {
            return;
        }
        twinBalanceData.setRightChild(rc.getLeftChild());
        rc.setLeftChild(twinBalanceData);
        twinBalanceData.setParent(rc);
        rc.setParent(parent);
    }

    public boolean insertElement(TwinBalanceData parent, TwinBalanceData element, Comparator<TwinBalanceData> comparator) {
        // 进行插入数据信息
        if (element == null) return false;
        if (comparator.compare(parent, element) > 0) {
            if (parent.getLeftChild() == null) {
                parent.setLeftChild(element);
//                element.setRightChild(null);
//                element.setLeftChild(null);
                element.setTaller(true);
//                element.setBalanceLeef(TwinBalanceArithmetic.EH);
                element.setParent(parent);
                leftBalanceElement(parent, comparator);
                return true;
            } else {
                boolean isOver = insertElement(parent.getLeftChild(), element, comparator);
                if (!isOver) return false;
                leftBalanceElement(parent, comparator);
            }
        } else if (comparator.compare(parent, element) < 0) {
            if (parent.getRightChild() == null) {
                parent.setRightChild(element);
//                element.setRightChild(null);
//                element.setLeftChild(null);
                element.setTaller(true);
                element.setParent(parent);
//                element.setBalanceLeef(TwinBalanceArithmetic.EH);
                rightBalanceElement(parent, comparator);
                return true;
            } else {
                boolean isOver = insertElement(parent.getRightChild(), element, comparator);
                if (!isOver) return false;
                rightBalanceElement(parent, comparator);
            }
        }
        return true;
    }

    public void leftBalance(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator) {
        TwinBalanceData lc = twinBalanceData.getLeftChild();
        switch(lc.getBalanceLeef()) {
            case TwinBalanceArithmetic.LH:
                twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.EH);
                lc.setBalanceLeef(TwinBalanceArithmetic.EH);
                rightRotate(twinBalanceData, comparator);
                break;
            case TwinBalanceArithmetic.RH:
                TwinBalanceData rd = lc.getRightChild();
                switch (rd.getBalanceLeef()) {
                    case TwinBalanceArithmetic.LH:
                        twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.RH);
                        lc.setBalanceLeef(TwinBalanceArithmetic.EH);
                        break;
                    case TwinBalanceArithmetic.EH:
                        twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.EH);
                        lc.setBalanceLeef(TwinBalanceArithmetic.EH);
                        break;
                    case TwinBalanceArithmetic.RH:
                        twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.EH);
                        lc.setBalanceLeef(TwinBalanceArithmetic.LH);
                        break;
                }
                rd.setBalanceLeef(TwinBalanceArithmetic.EH);
                leftRotate(twinBalanceData.getLeftChild(), comparator);
                rightRotate(twinBalanceData, comparator);
                break;
        }
    }

    public void leftBalanceElement(TwinBalanceData parent, Comparator<TwinBalanceData> comparator) {
        if (parent.getLeftChild() == null) return;
        if (parent.getLeftChild().getTaller()) {
            switch (parent.getBalanceLeef().intValue()) {
                case TwinBalanceArithmetic.LH:
                    leftBalance(parent, comparator);
                    parent.setTaller(false);
                    break;
                case TwinBalanceArithmetic.EH:
                    parent.setTaller(true);
                    parent.setBalanceLeef(TwinBalanceArithmetic.LH);
                    break;
                case TwinBalanceArithmetic.RH:
                    parent.setTaller(false);
                    parent.setBalanceLeef(TwinBalanceArithmetic.EH);
                    break;
            }
        }
    }

    public void rightBalance(TwinBalanceData twinBalanceData, Comparator<TwinBalanceData> comparator) {
        TwinBalanceData rd = twinBalanceData.getRightChild();
        switch (rd.getBalanceLeef()) {
            case TwinBalanceArithmetic.RH:
                twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.EH);
                rd.setBalanceLeef(TwinBalanceArithmetic.EH);
                leftRotate(twinBalanceData, comparator);
                break;
            case TwinBalanceArithmetic.LH:
                TwinBalanceData lc = rd.getLeftChild();
                switch(lc.getBalanceLeef()) {
                    case TwinBalanceArithmetic.LH:
                        twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.LH);
                        rd.setBalanceLeef(TwinBalanceArithmetic.EH);
                        break;
                    case TwinBalanceArithmetic.EH:
                        twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.EH);
                        rd.setBalanceLeef(TwinBalanceArithmetic.EH);
                        break;
                    case TwinBalanceArithmetic.RH:
                        twinBalanceData.setBalanceLeef(TwinBalanceArithmetic.EH);
                        rd.setBalanceLeef(TwinBalanceArithmetic.RH);
                        break;
                }
                lc.setBalanceLeef(TwinBalanceArithmetic.EH);
                rightRotate(twinBalanceData.getRightChild(), comparator);
                leftRotate(twinBalanceData, comparator);
                break;
        }
    }

    public void rightBalanceElement(TwinBalanceData parent, Comparator<TwinBalanceData> comparator) {
        if (parent.getRightChild() == null) return;
        if (parent.getRightChild().getTaller()) {
            switch (parent.getBalanceLeef().intValue()) {
                case TwinBalanceArithmetic.LH:
                    parent.setTaller(false);
                    parent.setBalanceLeef(TwinBalanceArithmetic.EH);
                    break;
                case TwinBalanceArithmetic.EH:
                    parent.setTaller(true);
                    parent.setBalanceLeef(TwinBalanceArithmetic.RH);
                    break;
                case TwinBalanceArithmetic.RH:
                    rightBalance(parent, comparator);
                    parent.setTaller(false);
                    break;
            }
        }
    }

    public void broswerBalanceMidle(TwinBalanceData twinBalanceData) {
        if (twinBalanceData == null) return;
        broswerBalanceMidle(twinBalanceData.getRightChild());
        System.out.print(twinBalanceData.getDate().toString());
        broswerBalanceMidle(twinBalanceData.getLeftChild());
    }

    public void removeElement(TwinBalanceData parent, TwinBalanceData element, Comparator<TwinBalanceData> comparator) {
        if (parent == null || element == null) return;
        if (comparator.compare(parent, element) > 0) {
            removeElement(parent.getLeftChild(), element, comparator);
            leftBalanceElement(parent, comparator);
        } else if (comparator.compare(parent, element) < 0) {
            removeElement(parent.getRightChild(), element, comparator);
            rightBalanceElement(parent, comparator);
        } else {
            // 删除数据，要删除数据，其中有如下情况发生
            // 平衡树的状态的发生变化
            // 是否会引起旋转的问题
            TwinBalanceData delete = parent;
            TwinBalanceData parents = parent.getParent();
            TwinBalanceData left = parent.getLeftChild();
            TwinBalanceData right = parent.getRightChild();
            if (parents.getLeftChild() != null && comparator.compare(parents.getLeftChild(), parent) == 0 && left != null) {
                if (parents.getParent() == null) {
                    parents.setRightChild(left);
                    parents.setLeftChild(left);
                    left.setParent(parents);
                    insertElement(left, right, comparator);
                } else {
                    parents.setLeftChild(left);
                    left.setParent(parents);
                    insertElement(parents, right, comparator);
                }
            } else if (parents.getRightChild() != null && comparator.compare(parents.getRightChild(), parent) == 0 && right != null) {
                if (parents.getParent() == null) {
                    parents.setRightChild(right);
                    parents.setLeftChild(right);
                    right.setParent(parents);
                    insertElement(right, left, comparator);
                } else {
                    parents.setRightChild(right);
                    right.setParent(right);
                    insertElement(parents, left, comparator);
                }
            } else if (left == null && right == null){
                parents.setRightChild(null);
                parents.setLeftChild(null);
            }
        }
    }

    public TwinBalanceData findElement(TwinBalanceData parent, TwinBalanceData element, Comparator<TwinBalanceData> comparator) {
        if (parent == null || element == null || comparator == null) return null;
        if (comparator.compare(parent, element) > 0) {
            return findElement(parent.getLeftChild(), element, comparator);
        } else if (comparator.compare(parent, element) < 0) {
            return findElement(parent.getRightChild(), element, comparator);
        } else {
            return  parent;
        }
    }

    public TwinBalanceData getRoot(TwinBalanceData root) {
        if (root == null) return null;
        if (root.getParent() == null) return root;
        return getRoot(root.getParent());
    }
}
