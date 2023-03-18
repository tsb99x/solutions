package codewars;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class Order {

    @Test
    public void test1() {
        assertEquals(order("is2 Thi1s T4est 3a"), "Thi1s is2 3a T4est");
    }

    @Test
    public void test2() {
        assertEquals(order("4of Fo1r pe6ople g3ood th5e the2"),
                "Fo1r the2 g3ood 4of th5e pe6ople");
    }

    @Test
    public void test3() {
        assertEquals(order(""), "");
    }

    public static String order(String words) {
        if (words.isBlank())
            return "";
        var digit = Pattern.compile("\\d");
        var unsorted = words.split(" ");
        var sorted = new String[unsorted.length];
        for (var word : unsorted) {
            var m = digit.matcher(word);
            m.find();
            var pos = m.group();
            sorted[Integer.parseInt(pos) - 1] = word;
        }
        return String.join(" ", sorted);
    }

}
