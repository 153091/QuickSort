/** %java Quick.java input.txt
 Note: Quick.java uses unchecked or unsafe operations.
 Note: Recompile with -Xlint:unchecked for details.
 A E E L M O P R S T X
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class Quick {
    private static int CUTOFF = 10; // если меньше 10 , то InsertionSort

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi<=lo) return;

        //срез на Сортировку вставками (улучшение 1)
        int n = hi - lo + 1;
        if (n<=CUTOFF) {
            Insertion.sort(a, lo, hi+1);
            return;
        }

        //замена lo  на среднее между lo, mid, hi (улучшение 2)
        int m = medianOf3(a, lo, lo+(hi-lo)/2, hi);
        exch(a, lo, m);

        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);

    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi+1;
        while(true){
            while(less(a[++i], a[lo])) // поиск слева элемент для свапа
                if(i==hi) break;

            while(less(a[lo], a[--j])) // поиск справа элемент для свапа
                if(j==lo) break;

            if (i>=j) break; // если указатели пересеклись

            exch(a, i, j);
        }
        exch(a, lo, j); // поставить пивот на нужное место
        return j;
    }

    private static int medianOf3(Comparable[] a, int lo, int mid, int hi) {
        if (less(a[lo], a[mid])) {
            if (less(a[mid], a[hi])) return mid;
            else {
                if (less(a[lo], a[hi])) return hi;
                else return lo;
            }
        }
        else {
            if (less(a[hi], a[mid])) return mid;
            else {
                if (less(a[lo], a[hi])) return lo;
                else return hi;
            }

        }
    }


    /*******************************************************************
     *  less exch isSorted.
     *******************************************************************/

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w)<0;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo+1 ; i<=hi; i++)
            if (less(a[i], a[i-1])) return false;
            return true;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length-1);
    }

    /*******************************************************************
     *  Version that takes Comparator as argument.
     *******************************************************************/

    public static void sort(Object[] a, Comparator comparator) {
        StdRandom.shuffle(a);
        sort(a, comparator, 0, a.length-1);
    }

    private static void sort(Object[] a, Comparator comparator, int lo, int hi) {
        if(hi<=lo) return;

        // срез на Insertion sort (улучшение 1)
        int n = hi - lo +1;
        if (n<= CUTOFF) { Insertion.sort(a, lo, hi+1, comparator); }

        //Среднее из lo, mid ,hi (улучшение 2)
        int m= medianOf3(a, comparator, lo, lo+(hi-lo)/2, hi);
        exch(a, lo ,m);

        int j = partition(a, comparator, lo, hi);
        sort(a, comparator, lo , j-1);
        sort(a, comparator, j+1, hi);
    }

    private static int partition(Object[] a, Comparator comparator, int lo, int hi) {
        int i=lo, j =hi+1;

        while (true) {
            while(less(a[++i], a[lo], comparator)) // поиск слева элемент для свапа
                if (i==hi) break;

            while(less(a[lo], a[--j], comparator)) // поиск справа элемент для свапа
                if (j==lo) break;

            if(i>=j) break;

            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static int medianOf3(Object[] a, Comparator comparator, int lo, int mid, int hi) {
        if (less(a[lo], a[mid], comparator)) {
            if (less(a[mid], a[hi], comparator)) return mid;
            else {
                if (less(a[lo], a[hi], comparator)) return hi;
                else return lo;
            }
        }
        else {
            if (less(a[hi], a[mid], comparator)) return mid;
            else {
                if (less(a[lo], a[hi], comparator)) return lo;
                else return hi;
            }

        }
    }

    private static boolean less(Object v, Object w, Comparator comparator) {
        return comparator.compare(v, w)<0;
    }

    private static boolean isSorted(Object[] a, Comparator comparator, int lo, int hi) {
        for (int i = lo+1; i<=hi; i++)
            if (less(a[i], a[i-1], comparator)) return false;
            return true;
    }

    private static boolean isSorted(Object[] a , Comparator comparator) {
        return isSorted(a, comparator, 0, a.length-1);
    }

    public static void main(String[] args) {
        In in =new In(args[0]);
        String[] a = in.readAllStrings();
        sort(a);

        for (String p: a)
            StdOut.print(" " +p);
    }
}
