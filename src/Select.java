import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class Select {

    public static Comparable select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int lo =0, hi = a.length-1;

        while (hi>lo) {
            int j = partition(a, lo, hi);

            if      (j>k) hi = j-1;
            else if (j<k) lo = j+1;
            else return a[k];
        }
        return a[k];
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j =hi+1;
        while (true) {
            while (less(a[++i], a[lo])) // поиск слева элемент для свапа
                if (i==hi) break;

            while (less(a[lo], a[--j])) // поиск справа элемент для свапа
                if(j==lo) break;

            if (i>= j) break;    // если указатели пересеклись

            exch( a, i, j);
        }
        exch(a, lo, j); // поставить пивот на нужное место
        return j;
    }

    /*******************************************************************
     *  less exch isSorted.
     *******************************************************************/

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w)<0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /*******************************************************************
     *  Version that takes Comparator as argument.
     *******************************************************************/

    public static Object select(Object[] a, int k, Comparator comparator) {
        StdRandom.shuffle(a);
        int lo =0, hi = a.length-1;

        while (hi>lo) {
            int j = partition(a, comparator, lo, hi);

            if      (j>k) hi = j-1;
            else if (j<k) lo = j+1;
            else return a[k];
        }
        return a[k];
    }

    private static int partition(Object[] a, Comparator comparator, int lo, int hi) {
        int i = lo, j =hi+1;
        while (true) {
            while (less(a[++i], a[lo], comparator)) // поиск слева элемент для свапа
                if (i==hi) break;

            while (less(a[lo], a[--j], comparator)) // поиск справа элемент для свапа
                if(j==lo) break;

            if (i>= j) break;    // если указатели пересеклись

            exch( a, i, j);
        }
        exch(a, lo, j); // поставить пивот на нужное место
        return j;
    }

    private static  boolean less(Object v, Object w, Comparator comparator) {
        return comparator.compare(v, w)< 0;
    }

    public static void main(String[] args){
        In in = new In(args[0]);
        String[] a = in.readAllStrings();

        while(!StdIn.isEmpty()) {
            int key = StdIn.readInt();
            StdOut.println(select(a, key));
        }

    }
}
