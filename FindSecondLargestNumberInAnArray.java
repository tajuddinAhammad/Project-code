package com.marolix.practice;


import java.util.Arrays;
import java.util.Scanner;

public class FindSecondLargestNumberInAnArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of elements in the array: ");
        int n = scanner.nextInt();

        Integer[] numbers = new Integer[n];

        System.out.println("Enter the elements of the array:");

        for (int i = 0; i < n; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            numbers[i] = scanner.nextInt();
        }

        Arrays.sort(numbers);

        int secondLargestNumber = numbers[n - 2];

        System.out.println("Second largest number in the array: " + secondLargestNumber);

        scanner.close();
    }
}

//import java.util.Arrays;
//
//public class FindSecondLargestNumberInAnArrays {
//public static void main(String[] args) {
//	Integer[] numbers = {23,4,56,7,78,97,9,3,56,54,7,87};
//	 Arrays.sort(numbers);
//	  int secondLargestNumber = numbers[numbers.length - 2];
//	  System.out.println("Second largest number in an Arrays:"
//              + secondLargestNumber);
//
//}
//}
