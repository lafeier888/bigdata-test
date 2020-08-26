package algorithm.search;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};//要求从小到大顺序
        int pos = binarySearch(arr,5);
        System.out.println(pos);
    }
    public static int binarySearch(int[] arr,int key){
        int left = 0;
        int right = arr.length-1;
        //相遇的时候，还是需要看看这个值是不是要找的值
        while (left<=right){
            int mid = (left+right)/2;
            if(key<arr[mid]){
                right = mid-1;
            }else if(key>arr[mid]){
                left = mid+1;
            }else{
                return mid;
            }
        }
        return -1;//没找到
    }
}
