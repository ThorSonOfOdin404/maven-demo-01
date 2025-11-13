package org.mytest;

import java.util.Arrays;

public class QuickSort {

    private static int partition(int[] arr, int start, int end){
        int pivot = arr[end];
        int i = start-1, j = start;
        while(j < end){
            if(arr[j] <= pivot){
                swap(arr,++i, j);
            }
            j++;
        }
        swap(arr, ++i, end);
        return i;
    }

    private static void swap(int[] arr, int ind1, int ind2){
        int temp = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = temp;
    }

    public static void sort(int[] arr, int start, int end){
        if(start < end){
            int pi = partition(arr,start,end);

            sort(arr, start, pi-1);
            sort(arr, pi+1, end);
        }
    }

    public static void main(String[] args) {
        int[] unsorted_arr = {1,3,2,7,3,4,9,5,6,2};
        System.out.println(Arrays.toString(unsorted_arr));

        sort(unsorted_arr, 0, unsorted_arr.length - 1);

        System.out.println(Arrays.toString(unsorted_arr));
    }

}
