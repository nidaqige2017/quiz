package cn.test.myproject.classicquiz;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 一个乱序的集合，使得奇数在左边，偶数在右边
 */
public class SortArrayByOddEven {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sortArrayByOddEven(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9})));
    }

    private static Integer [] sortArrayByOddEven(int [] arr){
        LinkedList<Integer> list = new LinkedList<>();
        for (Integer integer : arr) {
            if (0 == integer % 2){
                //偶数
                list.addLast(integer);
            } else list.addFirst(integer);
        }
        return list.toArray(new Integer[arr.length]);
    }
}
