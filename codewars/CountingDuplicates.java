// see: https://www.codewars.com/kata/54bf1c2cd5b56cc47f0007a1

package codewars;

import java.util.function.Function;
import java.util.stream.Collectors;

public class CountingDuplicates {

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
