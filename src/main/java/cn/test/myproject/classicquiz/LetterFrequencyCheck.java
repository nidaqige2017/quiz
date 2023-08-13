package cn.test.myproject.classicquiz;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 如果单词1和单词2之间从“a”到“z”的每个字母的频率之差至多为3，则两个字符串单词1和词2被认为几乎相等。
 * 给定长度为n的两个字符串word1和word2，如果word1和word2几乎相等，则返回true，否则返回false。
 * 字母x的频率是它在字符串中出现的次数。
 * 示例1：
 * 输入：word1=“aaaa”，word2=“bccb”
 * 输出：false
 * 说明：“aaaa”中有4'a，但“bccb”中有0'a。
 * 差值为4，大于允许的3。
 * 示例2：
 * 输入：word1=“abcdeef”，word2=“abcdeef”
 * 输出：true
 * 说明：单词1和单词2中每个字母的频率差异最多为3:
 * -“a”在单词1中出现1次，在单词2中出现4次。差别是3。
 * -“b”在单词1中出现1次，在单词2中出现1次。差异为0。
 * -“c”在单词1中出现1次，在单词2中出现2次。差异为1。
 * -“d”在单词1中出现1次，在单词2中出现0次。差异为1。
 * -“e”在单词1中出现2次，在单词2中出现0次。差别是2。
 * -“f”在单词1中出现1次，在单词2中出现0次。差异为1。
 * 示例3：
 * 输入：word1=“cccddabba”，word2=“babababab”
 * 输出：true
 * 说明：单词1和单词2中每个字母的频率差异最多为3:
 * -“a”在单词1中出现2次，在单词2中出现4次。差别是2。
 * -“b”在单词1中出现2次，在单词2中出现5次。差别是3。
 * -“c”在单词1中出现3次，在单词2中出现0次。差别是3。
 * -“d”在单词1中出现2次，在单词2中出现0次。差别是2。
 */
public class LetterFrequencyCheck {

    public static void main(String[] args) {
        System.out.println(letterFrequencyCheck("aaaa","bccb"));
        System.out.println(letterFrequencyCheck("abcdeef","abcdeef"));
        System.out.println(letterFrequencyCheck("cccddabba","babababab"));
    }

    private static boolean letterFrequencyCheck(String str1,String str2){
        Map<Character, Integer> map1 = statisticsLetters(str1);
        Map<Character, Integer> map2 = statisticsLetters(str2);

        Set<Character> key1 = map1.keySet();
        Set<Character> key2 = map2.keySet();

        Sets.SetView<Character> union = Sets.union(key1, key2);

        for (Character c : union) {
            if (Math.abs((null == map1.get(c) ? 0 : map1.get(c)) - (null == map2.get(c) ? 0 : map2.get(c))) > 3) return false;
        }
        return true;
    }

    private static Map<Character,Integer> statisticsLetters(String str){
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (map.containsKey(c)) map.put(c,map.get(c) + 1);
            else map.put(c,1);
        }
        return map;
    }
}
