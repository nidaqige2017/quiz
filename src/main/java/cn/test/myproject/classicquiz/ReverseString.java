package cn.test.myproject.classicquiz;

import java.util.Arrays;

public class ReverseString {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(reverseString1(new char [] {'a','b','c'})));
        System.out.println(Arrays.toString(reverseString2(new char [] {'a','b','c'})));
    }

    public static char[] reverseString1(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp;
            temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
        return s;
    }

    public static char[] reverseString2(char[] s) {
        for (int left = 0, right = s.length - 1; left < right; left++,right--) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
        }
        return s;
    }
}
