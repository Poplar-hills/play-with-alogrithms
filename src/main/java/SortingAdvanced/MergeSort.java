package SortingAdvanced;

import java.util.Arrays;

import static SortingBasic.Helpers.generateRandomIntArr;

/*
* 归并排序（Merge Sort）
* - 复杂度是 O(nlogn)
* - 归并排序递归地：
*   1. 将数组进行二分
*   2. 分别对左右两半进行排序
*   3. 再将排序后的两部分归并起来
*
*              不断进行二分                第一次归并                第二次归并                第三次归并
*   Level0   8 6 2 3 1 5 7 4          8 6 2 3 1 5 7 4          8 6 2 3 1 5 7 4          1 2 3 4 5 6 7 8
*   Level1   8 6 2 3|1 5 7 4   --->   8 6 2 3|1 5 7 4   --->   2 3 6 8|1 4 5 7   --->
*   Level2   8 6|2 3|1 5|7 4          6 8|2 3|1 5|4 7
*   Level3   8|6|2|3|1|5|7|4
*
* - 归并排序的思想：
*   - 有 n 个元素的数组可以进行 log(n) 次二分操作，共分出 log(n) 个层级。
*   - 如果每层的排序和归并过程能在 O(n) 的复杂度内完成，则该算法的整体复杂度就是 O(nlogn)。
* - 这个思想也是所有 O(nlogn) 复杂度的算法的来源 —— 通过二分达到 log(n) 的层级，在每层内用 O(n) 的算法来做事情。
* */

public class MergeSort {
    public static void sort(Comparable[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    // 递归地对 arr[l...r] 的范围（前闭后闭）进行排序
    private static void sort(Comparable[] arr, int l, int r) {
        if (l >= r)
            return;

        int mid = (r - l) / 2  + l;  // 也可以写成 (l + r) / 2，但是可能整型溢出
        sort(arr, l, mid);
        sort(arr, mid + 1, r);
        merge(arr, l, mid, r);       // 递归到最小单元后再归并
    }

    // 将 arr[l, mid] 和 arr[mid + 1, r] 这两部分进行归并
    private static void merge(Comparable[] arr, int l, int mid, int r) {
        // 创建辅助数组（空间换时间）
        Comparable[] aux = Arrays.copyOfRange(arr, l, r + 1);

        // 进行归并
        int i = l, j = mid + 1;  // 初始化，i 指向左半部分的起始索引 l；j 指向右半部分起始索引 mid + 1
        for (int k = l; k <= r; k++) {
            if (i > mid) {  // 如果左半部分元素已经全部处理完毕
                arr[k] = aux[j - l]; j++;
            }
            else if (j > r) {  // 如果右半部分元素已经全部处理完毕
                arr[k] = aux[i - l]; i++;
            }
            else if (aux[i - l].compareTo(aux[j - l]) < 0) {  // 左半部分所指元素 < 右半部分所指元素
                arr[k] = aux[i - l]; i++;
            }
            else {  // 左半部分所指元素 >= 右半部分所指元素
                arr[k] = aux[j - l]; j++;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = generateRandomIntArr(10);
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
