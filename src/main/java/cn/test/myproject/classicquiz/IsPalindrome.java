package cn.test.myproject.classicquiz;

/**
 *如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
 * 字母和数字都属于字母数字字符。
 * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
 */
public class IsPalindrome {

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
    }

    /**
     * 双指针解法
     */
    public static boolean isPalindrome(String s) {
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) newStr.append(Character.toLowerCase(c));
        }
        String s2 = String.valueOf(newStr);
        char[] chars = s2.toCharArray();
        for (int left = 0,right = newStr.length()-1; left < right;left++,right--) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
        }
        String s1 = new String(chars);
        return s2.equals(s1);
    }
}
