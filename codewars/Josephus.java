// see: https://www.codewars.com/kata/5550d638a99ddb113e0000a2

package codewars;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Josephus {

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

}
