package codewars;

import java.util.function.Function;
import java.util.stream.Collectors;

public class CW4 {

    public static void main(String[] args) {
        System.out.println(duplicateCount("abcde"));
    }

    public static int duplicateCount(String text) {
        return (int) text.toLowerCase()
                .chars().boxed()
                .collect(Collectors.toMap(Function.identity(), c -> 1,
                        Math::addExact))
                .entrySet().stream()
                .filter(kv -> kv.getValue() > 1)
                .count();
    }

}
