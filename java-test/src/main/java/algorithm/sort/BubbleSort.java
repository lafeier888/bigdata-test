package algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 核心逻辑：趟数 和 每趟需要多少次冒泡
 */
public class BubbleSort {
    public static void main(String[] args) {
        //int[] arr = {1,2,3,4,5,6,7,8,9};
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        bubblesort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void bubblesort(int[] arr) {
        for (int i = 0; i < arr.length -1; i++) //需要length-1趟，因为最后一个元素用不上
            for (int j = 0; j < arr.length - i - 1; j++) { //第n趟需要length-n-1次冒泡
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
    }
}
