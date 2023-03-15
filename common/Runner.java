package common;

import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;

public class Runner {

    public static void timed(IntSupplier fn, int iterations_in_batch,
            int batches) {
        System.out.println("-".repeat(80));
        for (int j = 0; j < batches; j++) {
            var start = System.nanoTime();
            var buf = new long[iterations_in_batch];
            for (int i = 0; i < buf.length; i++) {
                buf[i] = fn.getAsInt();
            }
            var delta = System.nanoTime() - start;
            System.out.printf("%.3fns\n", delta / (double) buf.length);
        }
        System.out.println("=".repeat(80));
    }

    public static void timed(LongSupplier fn, int iterations_in_batch,
            int batches) {
        System.out.println("-".repeat(80));
        for (int j = 0; j < batches; j++) {
            var start = System.nanoTime();
            var buf = new long[iterations_in_batch];
            for (int i = 0; i < buf.length; i++) {
                buf[i] = fn.getAsLong();
            }
            var delta = System.nanoTime() - start;
            System.out.printf("%.3fns\n", delta / (double) buf.length);
        }
        System.out.println("=".repeat(80));
    }

    public static void timedL(LongFunction<Integer> fn, int iterations_in_batch,
            int batches) {
        System.out.println("-".repeat(80));
        for (int j = 0; j < batches; j++) {
            var start = System.nanoTime();
            var buf = new long[iterations_in_batch];
            for (int i = 0; i < buf.length; i++) {
                buf[i] = fn.apply(i);
            }
            var delta = System.nanoTime() - start;
            System.out.printf("%.3fns\n", delta / (double) buf.length);
        }
        System.out.println("=".repeat(80));
    }

    public static void timedI(IntFunction<Integer> fn, int iterations_in_batch,
            int batches) {
        System.out.println("-".repeat(80));
        for (int j = 0; j < batches; j++) {
            var start = System.nanoTime();
            var buf = new int[iterations_in_batch];
            for (int i = 0; i < buf.length; i++) {
                buf[i] = fn.apply(i);
            }
            var delta = System.nanoTime() - start;
            System.out.printf("%0.3fns\n", delta / (double) buf.length);
        }
        System.out.println("=".repeat(80));
    }

}
