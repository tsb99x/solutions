// see: https://www.codewars.com/kata/55c45be3b2079eccff00010f

import java.util.regex.Pattern;

public class Order {

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
