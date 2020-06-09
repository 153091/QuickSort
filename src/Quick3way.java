/** %java Quick3way.java input.txt
 Note: Quick3way.java uses unchecked or unsafe operations.
 Note: Recompile with -Xlint:unchecked for details.
 A E E L M O P R S T X
 */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class Quick3way {
    private static final int CUTOFF = 10; //срез на Insertion Sort

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }

    private  static void sort(Comparable[] a, int lo, int hi) {
        if (hi<=lo) return;

        // Срез на Insertion sort (улучшение 1)
        int n = hi-lo+1;
        if (n<=CUTOFF) {
            Insertion.sort(a, lo, hi+1);
            return;
        }

        //Среднее из lo,mid,hi (улучшение 2)
        int m = medianOf3(a, lo, lo + (hi-lo)/2, hi);
        exch(a, lo, m);

        int i = lo, lt=lo, gt=hi;
        Comparable v = a[lo];

        while (i<=gt) {
            int comp = a[i].compareTo(v);
            if      (comp>0) exch(a, gt--, i);
            else if (comp<0) exch(a, i++, lt++);
            else     i++;
        }

        sort(a, lo, lt-1);
        sort(a, gt+1, hi);


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

    /*******************************************************************
     *  Version that takes Comparator as argument.
     *******************************************************************/

    public static void sort(Object[] a, Comparator comparator) {
        StdRandom.shuffle(a);
        sort(a, comparator, 0, a.length-1);
    }

    private static void sort(Object[] a, Comparator comparator, int lo, int hi) {
        if(hi<=lo) return;

        //первое улучшение срез
        int n = hi-lo+1;
        if (n<=CUTOFF) { Insertion.sort(a, lo, hi+1, comparator); return; }

        //второе улучшение медиана
        int m = medianOf3(a, comparator, lo, lo+(hi-lo)/2, hi);
        exch(a, lo, m);

        int i=lo, lt=lo, gt =hi;
        Object v = a[lo];

        while (i<=gt) {
            int comp = comparator.compare(a[i], v);

            if      (comp >0) exch(a, gt--, i);
            else if (comp <0) exch(a,lt++, i++) ;
            else   i++;
        }
        sort(a, comparator, lo, lt-1);
        sort(a, comparator, gt+1, hi);


    }

    private static boolean less(Object v, Object w, Comparator comparator){
        return comparator.compare(v, w) <0;
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

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] a = in.readAllStrings();

        sort(a);

        for (String p : a)
            StdOut.print(" " +p);
    }
}
