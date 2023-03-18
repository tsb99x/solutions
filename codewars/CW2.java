package codewars;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import common.Runner;

public class CW2 {

    public static void main(String[] args) {
        List<Object> src = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5,
                6, 7, 8,
                9, 10, 11,
                12, 13, 14,
                15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
                47, 48, 49, 50));
        var exp = Arrays.asList(11, 22, 33, 44, 5, 17, 29, 41, 3, 16, 30, 43, 7,
                21, 36, 50, 15, 32, 48, 14, 34, 1, 20, 39, 9, 28, 2, 25, 47, 24,
                49, 27, 8, 38, 19, 6, 42, 35, 26, 23, 31, 40, 4, 18, 12, 13, 46,
                37, 45, 10);
//        Runner.timedI((i) -> {
//            josephusPermutation(src, 11);
//            return i;
//        }, 1_000_000, 10);
        var arrs = new ArrayList<List<Object>>();
        for (int i = 0; i < 1_000_000; i++)
            arrs.add(new LinkedList<>(src));
        Runner.timedI((i) -> {
            josephusPermutation2(arrs.get(i), 11);
            return 1;
        }, 1_000_000, 1);
    }

    public static <T> List<T> josephusPermutation(final List<T> items,
            final int k) {
        var order = new ArrayList<T>();
        var queue = new ArrayDeque<T>(items);
        while (!queue.isEmpty()) {
            for (int i = 0; i < k - 1; i++)
                queue.add(queue.poll());
            order.add(queue.poll());
        }
        return order;
    }

    public static <T> List<T> josephusPermutationN(final List<T> items,
            final int k) {
        var order = new ArrayList<T>();
        var it = items.iterator();
        while (!items.isEmpty()) {
            for (int i = 0; i < k - 1; i++) {
                if (!it.hasNext())
                    it = items.iterator();
                it.next();
            }
            if (!it.hasNext())
                it = items.iterator();
            order.add(it.next());
            it.remove();
        }
        return order;
    }

    public static Collection<Object> josephusPermutation2(
            final List<Object> its /* items */, final int k) {
//        var its = new LinkedList<>(items);
        Collection<Object> permutation = new ArrayList<>();
        int position = 0;
        while (its.size() > 0) {
            position = (position + k - 1) % its.size();
            permutation.add(its.remove((int) position));
        }
        return permutation;
    }

}
