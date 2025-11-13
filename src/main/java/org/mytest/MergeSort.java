package org.mytest;

import java.util.Arrays;
import java.util.List;

// Array : [1,3,2,7,3,4,9,5,6,2]
/*
    start_ind = 0
    end_ind = len(arr)-1
    mid_point = (end + start )/ 2 = start + (end - start) / 2

 */
public class MergeSort {
    public static void sort(int[] arr, int start, int end){
        if(start<end){
            int mid = start + (end - start) / 2;

            int segment_len_1 = mid - start + 1;
            int segment_len_2 = end - mid ;

            sort(arr, start, mid);
            sort(arr, mid + 1, end);

            int[] segment_1 = new int[segment_len_1];
            int[] segment_2 = new int[segment_len_2];

            System.arraycopy(arr,start,segment_1,0, segment_len_1);
            System.arraycopy(arr,mid+1,segment_2,0, segment_len_2);

            int x = 0,y = 0;
            int i = start;
            while ( i <= end && x < segment_len_1 && y < segment_len_2) {
                if(segment_1[x] >= segment_2[y]){
                    arr[i] = segment_2[y++];
                }else{
                    arr[i] = segment_1[x++];
                }
                i++;
            }
            while(x<segment_len_1){
                arr[i++] = segment_1[x++];
            }
            while(y<segment_len_2){
                arr[i++] = segment_2[y++];
            }
        }
    }

    public static void main(String[] args) {
        int[] unsorted_arr = {1,3,2,7,3,4,9,5,6,2};
        System.out.println(Arrays.toString(unsorted_arr));

        sort(unsorted_arr, 0, unsorted_arr.length - 1);

        System.out.println(Arrays.toString(unsorted_arr));
    }
}
