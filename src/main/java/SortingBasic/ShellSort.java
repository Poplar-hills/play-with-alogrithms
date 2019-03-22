package SortingBasic;

import SortingAdvanced.MergeSort2;
import SortingAdvanced.QuickSort2Ways;
import SortingAdvanced.QuickSort3Ways;

import static Utils.Helpers.*;

/**
 * 希尔排序（Shell Sort）：
 *
 * - 产生背景：
 *   希尔排序是一种对插入排序的改进。插入排序对于近似有序的数组来说效率很高，可以达到接近 O(n) 的水平。但是平均情况
 *   下，插入排序效率还是比较低，主要原因是它一次只能将元素向前移一位。比如一个长度为 n 的数组，如果最小的元素如果恰
 *   巧在末尾，那么使用插入排序就需一步一步的向前比较和移动（共需要 n-1 次）。
 *
 * - 排序过程：
 *   对于数组： [9, 1, 2, 5, 7, 4, 8, 6, 3, 5] 进行排序：
 *
 *   第1遍排序： 9  1  2  5  7  4  8  6  3  5    这遍排序中，gap1 = N / 2 = 5，即相隔距为5的元素组成一组，可以分为5组：
 *   gap = 5    |______________|                [9, 4], [1, 4], [2, 6], [5, 3], [7, 5]
 *                 |______________|             之后分别对每组进行插入排序。
 *                    |______________|
 *                       |______________|
 *                          |______________|
 *              4  1  2  3  5  9  8  6  5  7
 *
 *   第2遍排序： 4  1  2  3  5  9  8  6  5  7    这遍排序中，gap2 = gap1 / 2 = 2 (取整)，即相隔为2的元素组成一组，可以分为2组：
 *   gap = 2    |_____|_____|_____|_____|       [4, 2, 5, 8, 5], [1, 3, 6, 7, 9]
 *                 |_____|_____|_____|_____|    之后分别对每组进行插入排序。
 *              2  1  4  3  5  6  5  7  8  9
 *
 *   第3遍排序： 2  1  4  3  5  6  5  7  8  9    这遍排序中，gap3 = gap2 / 2 = 1，即相隔距为1的元素组成一组，只有一组。
 *   gap = 1    |__|__|__|__|__|__|__|__|__|    最后对这组进行插入排序，完成整个排序过程。
 *              1  2  3  4  5  5  6  7  8  9
 *
 * - 图解排序过程 SEE：http://www.cnblogs.com/idorax/p/6579332.html
 *
 * - 希尔排序为什么快：
 *   1. 插入排序的问题在于一次只能将元素向前移一位，因此在大数据量时很慢。而希尔排序通过将所有元素划分在几个区域内来提升
 *      插入排序的效率，这样可以让元素一次性的朝最终位置迈进一大步。
 *   2. 由于 gap 是递减的，最初几次 gap 取值较大，因此每个分组中的元素个数较少，排序速度较快；待到后期，gap 取值逐渐变
 *      小，子序列中的对象个数逐渐变多，但由于前面工作的基础，大多数元素已经基本有序，因此再执行排序依然很快。
 *
 * - 步长选择：
 *   - 理论上只要最终步长为1任何步长序列都可以，但不同步长会导致复杂度不同，比如用 3*h + 1 这个序列来计算步长，则步长为
 *     1, 4, 13, 40, 121...，这样实现的算法复杂度胃 O(n^(3/2))，比 O(n^2) 的算法改进了很多。
 *   - Donald Shell 最初建议步长选择为 n/2 并且对步长取半直到步长达到1。
 *   - 已知的最好步长序列是由 Sedgewick 提出的 1, 5, 19, 41, 109...
 *
 * - 插入排序 vs. 希尔排序：
 *   1. 插入排序是稳定的，希尔排序不是。（稳定性：数组中相同的元素是否会被交换位置）
 *   2. 插入排序也适用于链式存储结构，希尔排序不适用。
 *   3. 插入排序更适合于本来就基本有序的集合。
 *   4. 希尔排序的比较次数和移动次数都要比插入排序少，当元素越多时，效果越明显。
 *
 * - 希尔排序的复杂度比 O(n^2) 的算法低，虽然不如 O(nlogn) 的算法快，但是性能差距并不大，而且实现比较简单，只使用循环即可，
 *   不需要递归、不占用系统占空间、也不依赖随机数，因此在很多情况下反而是首选的排序算法。
 * */

public class ShellSort {
    public static void sort(Comparable[] arr) {
        int h = arr.length / 2;  // h 即为上面说的 gap
        while (h >= 1) {         // h-sort the array
            for (int i = h; i < arr.length; i++) {  // 体会一下从 h 开始遍历的目的
                // 循环内部进行插入排序
                Comparable e = arr[i];
                int j = i;
                for (; j >= h && e.compareTo(arr[j - h]) < 0 ; j -= h)  // 在比较的过程中每次步进为 h，即只对 arr[i], arr[i-h], arr[i-2*h]... 进行插入排序
                    arr[j] = arr[j - h];
                arr[j] = e;
            }
            h /= 2;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = generateRandomIntArr(10);
        log(arr);
        sort(arr);
        log(arr);

        // 性能测试
        Integer[] arr2 = generateRandomIntArr(10000);
        Integer[] arr3 = arr2.clone();
        Integer[] arr4 = arr2.clone();
        timeIt(arr3, InsertionSort::sort2);
        timeIt(arr4, QuickSort2Ways::sort);
        timeIt(arr2, ShellSort::sort);  // 希尔排序几乎跟快排一样快
    }
}
