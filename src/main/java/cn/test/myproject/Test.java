package cn.test.myproject;

public class Test {

    public static void main(String[] args) {
        System.out.println(isPalindrome1("A man, a plan, a canal: Panama"));
    }

    /**
     * 双指针解法
     */
    public static boolean isPalindrome1(String s) {
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
