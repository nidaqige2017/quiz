package cn.test.myproject.classicquiz;

/**
 * 有两个字符串a和b，判断是否能通过B中的字母组成A，B中的字母只能用一次
 */
public class CanAComposeB {

    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) return false;
        //TODO 经典： 创建26个字母数组字典
        int[] dictionary = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            int index = magazine.charAt(i) - 'a';
            dictionary[index]++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if (0 == dictionary[c - 'a']) return false;
            else dictionary[c - 'a']--;
        }
        return true;
    }
}
