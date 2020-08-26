package algorithm.sort;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {9,8,7,6,5,4,3,2,1};
        mergeSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void mergeSort(int[] arr, int l, int r) {
        if (l >= r) return;
        int mid = (l + r) / 2;
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, mid, l, r);

    }

    //合并的时候我们认为l-mid是 有序的   mid+1-r也是有序的
    private static void merge(int[] arr, int mid, int l, int r) {
        int[] tmp = new int[r + 1];//复制l-r之间的数据
        for (int i = l; i <= r; i++) {
            tmp[i] = arr[i];
        }

        int left = l; //左边部分
        int right = mid + 1;//右边部分的指针

        int index=l;//index是填充的索引

        //遍历两个数组，从小到大填充
        while (left <= mid && right <= r) {
            if(tmp[left]<=tmp[right]){
                arr[index] = tmp[left];
                left++;
            }else {
                arr[index] = tmp[right];
                right++;
            }
            index++;
        }
        //左边如果遍历完，left一定等于mid+1
        if(left<=mid) {
            //因为是按照l---r复制的，我们假设是从小到大的顺序
            //最后如果左边先遍历完，那右边的都是大于左边的，保持原位置即可
            //如果是右边的先遍历完，那左边剩下的就是最大的，把剩下的补充到末尾
            for (int j = left; j <= mid; j++) {
                arr[index] = tmp[j];
                index++;
            }
        }

    }
}
