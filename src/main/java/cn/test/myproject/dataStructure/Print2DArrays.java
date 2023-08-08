package cn.test.myproject.dataStructure;

/**
 * 打印二维数组
 */
public class Print2DArrays {

    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        print2DArray(arr);
        printRotatedArray(arr);
    }

    /**
     * 直接遍历
     */
    public static void print2DArray(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    /**
     * 顺时针打印
     */
    public static void printRotatedArray(int[][] data) {
        //行长度
        int row = data.length;
        //列长度
        int column = 0;
        for (int[] datum : data) {
            column = datum.length;
        }
        //若N*N二维数组只有一个元素时
        if (row == 1 && column == 1) {
            System.out.print(data[row - 1][column - 1] + " ");
            return;
        }
        //打印第一行
        for (int i = 0; i < column; i++) {
            System.out.print(data[0][i] + " ");
        }
        //打印最后一列
        for (int i = 1; i < row; i++) {
            System.out.print(data[i][column - 1] + " ");
        }
        //打印最后一行
        for (int i = column - 2; i >= 0; i--) {
            System.out.print(data[row - 1][i] + " ");
        }
        //打印第一列
        for (int i = row - 2; i >= 1; i--) {
            System.out.print(data[i][0] + " ");
        }
        //若行、列还有元素未打印
        if (row - 2 > 0 && column - 2 > 0) {
            //构造新的二维数组
            int[][] array = new int[row - 2][column - 2];
            for (int i = 1; i <= row - 2; i++) {
                System.arraycopy(data[i], 1, array[i - 1], 0, column - 2);
            }
            //递归
            printRotatedArray(array);
        }
    }


}
