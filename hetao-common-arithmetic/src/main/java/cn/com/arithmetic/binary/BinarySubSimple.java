package cn.com.arithmetic.binary;

import cn.com.arithmetic.entity.BinarySubDataStructure;

import java.util.Comparator;

/*
 *@username LUOYUSHUN
 *@datetime 2020/2/25 12:38
 *@desc
 **/
public class BinarySubSimple implements BinarySub {

    private <T> BinarySubDataStructure<T> createElement(T element) {
        BinarySubDataStructure<T> ele = new BinarySubDataStructure<T>();
        ele.setFirstData(element);
        ele.setSecond(null);
        ele.setFirst(null);
        ele.setThird(null);
        ele.setFourth(null);
        ele.setSecondData(null);
        ele.setThirdData(null);
        ele.setLength(1);
        return ele;
    }

    public <T> boolean insertElement(BinarySubDataStructure<T> parent, T element, Comparator<T> comparator) {
        if (parent == null || element == null || comparator == null) return false;
        if (parent.getFirstData() != null && comparator.compare(parent.getFirstData(), element) < 0) {
            if (parent.getFirst() == null) {
                BinarySubDataStructure<T> datas = createElement(element);
                datas.setParent(parent);
                parent.setFirst(datas);
                return false;
            } else {
                boolean check = insertElement(parent.getFirst(), element, comparator);
                if (check) {
                    justifyAdapt(parent, comparator);
                }
                return check;
            }
        } else if (parent.getFirstData() != null && (parent.getSecondData() != null
                && comparator.compare(parent.getSecondData(), element) < 0 || parent.getSecondData() == null)) {
            if (parent.getSecond() == null) {
                if (parent.getSecondData() == null) {
                    parent.setSecondData(element);
                    parent.setLength(parent.getLength() + 1);
                } else {
                    BinarySubDataStructure<T> datas = createElement(element);
                    datas.setParent(parent);
                    parent.setSecond(datas);
                }
            } else {
                boolean check = insertElement(parent.getSecond(), element, comparator);
                if (check) {
                    justifyAdapt(parent, comparator);
                }
                return check;
            }
        } else if (parent.getFirstData() != null && parent.getSecondData() != null
                && comparator.compare(parent.getSecondData(), element) > 0) {
            if (parent.getThird() == null) {
                if (parent.getThirdData() == null) {
                    parent.setThirdData(element);
                    parent.setLength(parent.getLength() + 1);
                    justifyAdapt(parent, comparator);
                    return true;
                } else {
                    BinarySubDataStructure<T> datas = createElement(element);
                    datas.setParent(parent);
                    parent.setThird(datas);
                }
            } else {
                boolean check = insertElement(parent.getSecond(), element, comparator);
                if (check) {
                    justifyAdapt(parent, comparator);
                }
                return check;
            }
        } else {
            return false;
        }
        return false;
    }

    public <T> void justifyAdapt(BinarySubDataStructure<T> dataStructure, Comparator<T> comparator) {
        if (dataStructure.getLength() > 2) {
            adaptStructure(dataStructure, comparator);
        }
    }

    public <T> void adaptStructure(BinarySubDataStructure<T> dataStructure, Comparator<T> comparator) {
        //这里是进行调整的数据，如果长度已经是三了，那么就要进行调整，要将中间的哪一个进行提取出来，解决步骤如下
        // 2、获取父类
        // 3、获取中间值
        // 4、获取第一个值
        // 5、获取最后一个值
        // 6、获取指针
        BinarySubDataStructure<T> parent = dataStructure.getParent();
        BinarySubDataStructure<T> first = dataStructure.getFirst();
        BinarySubDataStructure<T> second = dataStructure.getSecond();
        BinarySubDataStructure<T> third = dataStructure.getThird();
        BinarySubDataStructure<T> fourth = dataStructure.getFourth();
        T firstData = dataStructure.getFirstData();
        T secondData = dataStructure.getSecondData();
        T thirdData = dataStructure.getThirdData();
        // 判断，如果父类已经是根节点了，那么要如下进行分裂
        if (parent.getParent() == null) {
            BinarySubDataStructure<T> one = createElement(firstData);
            BinarySubDataStructure<T> two = createElement(secondData);
            BinarySubDataStructure<T> three = createElement(thirdData);
            // 将中间的数据提出，作为新的节点
            parent.setThird(two);
            parent.setFirst(two);
            parent.setSecond(two);
            parent.setFourth(two);
            two.setParent(parent);
            // 设置右边的子树
            two.setFirst(one);
            one.setParent(two);
            one.setFirst(first);
            one.setSecond(second);
            // 设置左边的子树
            two.setSecond(three);
            three.setParent(two);
            three.setFirst(third);
            three.setSecond(fourth);
            // 调整完成
        } else {

            if (parent.getFirst() == dataStructure) {
                parent.setFirst(null);
            } else if (parent.getSecond() == dataStructure) {
                parent.setSecondData(null);
            } else if (parent.getThird() == dataStructure) {
                parent.setThird(null);
            } else if (parent.getFourth() == dataStructure) {
                parent.setFourth(null);
            }

            BinarySubDataStructure<T> one = createElement(firstData);
            BinarySubDataStructure<T> two = createElement(thirdData);
            one.setFirst(first);
            if (first != null) {
                first.setParent(one);
            }
            one.setSecond(second);
            if (second != null) {
                second.setParent(one);
            }
            two.setFirst(third);
            if (third != null) {
                third.setParent(two);
            }
            two.setFourth(fourth);
            if (fourth != null) {
                fourth.setParent(two);
            }
            one.setParent(parent);
            two.setParent(parent);
            // 如果不是根节点
            if (comparator.compare(parent.getFirstData(), secondData) > 0) {
                parent.setThirdData(parent.getSecondData());
                parent.setSecondData(parent.getFirstData());
                parent.setFirstData(secondData);
                parent.setFourth(parent.getThird());
                parent.setThird(parent.getSecond());
                parent.setSecond(parent.getFirst());

                parent.setFirst(one);
                parent.setSecond(two);
                parent.setLength(parent.getLength() + 1);
            } else if (parent.getSecondData() == null || comparator.compare(parent.getFirstData(), secondData) < 0 && comparator.compare(parent.getSecondData(), secondData) > 0) {
                parent.setThirdData(parent.getSecondData());
                parent.setFourth(parent.getThird());
                parent.setThird(parent.getSecond());
                parent.setSecondData(secondData);

                parent.setSecond(one);
                parent.setThird(two);
                parent.setLength(parent.getLength() + 1);
            } else if (comparator.compare(parent.getSecondData(), secondData) < 0) {
                parent.setThirdData(secondData);
                parent.setThird(one);
                parent.setFourth(two);
                parent.setLength(parent.getLength() + 1);
            }
        }

    }

    public <T> T findElement(BinarySubDataStructure<T> parent, T element, Comparator<T> comparator) {
        if (parent == null || element == null || comparator == null) return null;
        if (comparator.compare(parent.getFirstData(), element) == 0) {
            return parent.getFirstData();
        } else if (comparator.compare(parent.getFirstData(), element) < 0) {
            return findElement(parent.getFirst(), element, comparator);
        } else if (comparator.compare(parent.getSecondData(), element) == 0) {
            return parent.getSecondData();
        } else if (comparator.compare(parent.getFirstData(), element) > 0
                && comparator.compare(parent.getSecondData(), element) < 0) {
            return findElement(parent.getSecond(), element, comparator);
        } else if (comparator.compare(parent.getThirdData(), element) == 0) {
            return parent.getSecondData();
        } else if (comparator.compare(parent.getSecondData(), element) > 0
                && comparator.compare(parent.getThirdData(), element) < 0) {
            return findElement(parent.getThird(), element, comparator);
        } else if (comparator.compare(parent.getThirdData(), element) > 0) {
            return findElement(parent.getFourth(), element, comparator);
        }
        return null;
    }
}
