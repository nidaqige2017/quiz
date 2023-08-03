package cn.test.myproject.classicquiz;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Description：给定一个数字和数组,求数组中所有相加得该数字的
 */
@Slf4j
public class ArraySumEqualTarget {

    @SneakyThrows
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        method1(10,new int [] {1,2,3,4,5,6,7,8,9});
//        stopWatch.stop();
//        System.out.println(stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        method2(10,new int [] {1,2,3,4,5,6,7,8,9});
        stopWatch.stop();
        System.out.println(stopWatch.getTime());

    }

    //暴力解法
    private static void method1(int target, int [] arrs){
        HashSet<Integer> set = new HashSet<>();
        for (int a : arrs) {
            for (int b : arrs) {
                if (target == (a + b) && !set.contains(10 * a + b)){
                    log.info("{} + {} = {}",a,b,target);
                    set.add(10 * a + b);
                    set.add(10 * b + a);
                    break;
                }
            }
        }
    }

    //双指针算法
    private static void method2(int target, int[] arrs) {
        // 先对数组进行排序，方便后续的双指针遍历
        Arrays.sort(arrs);
        int left = 0, right = arrs.length - 1;
        while (left < right) {
            int sum = arrs[left] + arrs[right];
            if (sum == target) {
                log.info("{} + {} = {}", arrs[left], arrs[right], target);
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
    }


}
