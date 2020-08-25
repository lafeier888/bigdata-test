package algorithm;

import java.util.Arrays;

/**
 * 快速排序--填坑法
 * 右边填左边坑，左边填右边坑，填不上的时候就相遇了，相遇位置就是一个坑，坑右边大于坑左边
 */
public class QuickSort {
    public static int partition(int a[], int l, int r) {


        int key = a[l]; // 去除第一个元素的值作为基准值，这个位置就是一个坑

        while (l < r) { // 从表的两端交替向中间扫描

            //右边的找出一个小于小于等于基准值的元素，填补左边的坑
            //如果没有找到，左右指针会在左边的坑相遇
            while (l < r && a[r] > key)
                r--;
            if (l < r) {//没相遇，填左边的坑
                a[l] = a[r];
                l++;
            }
            //左边的找到大于基准值的元素，填补右边的坑
            //如果没有找到，左右指针会在右边的坑相遇
            while (l < r && a[l] <= key)
                l++;
            if (l < r) //没相遇，填右边的坑
            {
                a[r] = a[l];
                r--;
            }
        }

        //将基准数值填回坑，无论是左填右相遇，还是右填左相遇，最终都是在坑的位置相遇，坑的左边是小于等于基准值，右边是大于基准值
        a[l] = key;

        return l;

    }

    public static void quickSort(int a[], int l, int r) {
        if (l > r) return;
        int mid = partition(a, l, r);

        quickSort(a, l, mid - 1); // 对低子表进行递归排序
        quickSort(a, mid + 1, r); // 对高子表进行递归排序
    }

    public static void main(String[] args) {

        //int a[] = { 1,2,3,4,5,6,7,8,9 };
        int arr[] = {3, 4, 7, 5, 18, 4, 5, 8, 9};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}