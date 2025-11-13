package org.mytest;

import java.util.Arrays;

public class BubbleSort {
    public static void sort(int[] arr){
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int flag = 0;
            for(int j = 0; j < length-i-1; j++){
                if(arr[j] > arr[j+1]){
                    swap(arr,j, j+1);
                    flag = 1;
                }
            }
            if (flag == 0){
                break;
            }
        }
    }

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static void main(String[] args) {
        int[] unsorted_arr = {1,3,2,7,3,4,9,5,6,2};
        System.out.println(Arrays.toString(unsorted_arr));

        sort(unsorted_arr);
        System.out.println("Bubble Sorting ");

        System.out.println(Arrays.toString(unsorted_arr));
    }
}
