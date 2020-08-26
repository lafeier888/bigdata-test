package algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序--交换法的实现
 * 核心逻辑：左右移动，交换，一直到相遇，相遇位置右边大于左边
 */
public class QuickSort2 {
    public static void main(String[] args) {

        int[] arr = {1, 5, 4, 3, 8, 7, 9, 6};
        quciksort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quciksort(int[] arr, int l, int r) {

        if (l > r) return;
        int mid = partition(arr, l, r);
        quciksort(arr, l, mid - 1);
        quciksort(arr, mid + 1, r);

    }

    private static void println(Object... o) {
        System.out.println(Arrays.toString(o));
    }

    //分区，基准值左边<=基准值，右边>基准值
    private static int partition(int[] arr, int l, int r) {


        int key_pos = l;//基准值初始位置
        int key = arr[key_pos];//基准值

        while (l < r) {//移动左右指针，直到相遇

            while (l < r && arr[r] > key) r--;//移动右指针到小于等于基准值的位置
            while (l < r && arr[l] <= key) l++;//移动左指针到大于基准值的位置
            if (l < r) { //没有相遇，就交换
                int temp = arr[l];
                arr[l] = arr[r];
                arr[r] = temp;
            }
        }
        //相遇，相遇位置一定是小于等于基准值的，原因
        //    如果右指针移动导致左右指针相遇，那左指针肯定是指向的小于等于基准值的位置，这有两种情况
        //       第一次就相遇，那就是左指针自始至终都没有移动，那肯定指向基准值
        //       在交换后，右指针移动，此时由于交换左指针还是指向的小于等于基准值的位置
        //    如果左指针移动导致的相遇，那右指针指向的肯定是指向的小于等于基准值的（不然右指针一定移动了）
        //此时将基准值安排在这里
        //那么，基准值左边的都是小于等于基准值的，右边的都是大于基准值的
        int temp = arr[l];
        arr[l] = arr[key_pos];
        arr[key_pos] = temp;

        //返回基准值的位置
        return l;
    }
}
