import java.util.HashMap;
import java.util.function.IntSupplier;
import java.util.regex.Pattern;

class Circuit {

    private static final Pattern input = Pattern.compile("^(\\w+) -> (\\w+)$");
    private static final Pattern and = Pattern.compile("^(\\w+) AND (\\w+) -> (\\w+)$");
    private static final Pattern lshift = Pattern.compile("^(\\w+) LSHIFT (\\w+) -> (\\w+)$");
    private static final Pattern not = Pattern.compile("^NOT (\\w+) -> (\\w+)$");
    private static final Pattern or = Pattern.compile("^(\\w+) OR (\\w+) -> (\\w+)$");
    private static final Pattern rshift = Pattern.compile("^(\\w+) RSHIFT (\\w+) -> (\\w+)$");

    private final Map<String, IntSupplier> wires = new HashMap<>();
    private final Map<String, Integer> memo = new HashMap<>();

    void resetMemo() {
        memo.clear();
    }

    int resolve(String dep) {
        if (dep.codePoints().allMatch(Character::isDigit)) {
            return Integer.parseInt(dep);
        }
        if (memo.containsKey(dep)) {
            return memo.get(dep);
        }
        var value = wires.get(dep).getAsInt();
        memo.put(dep, value);
        return value;
    }

    void parse(String line) {
        // 123 -> x
        var matcher = input.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var wire = matcher.group(2);
            wires.put(wire, () -> resolve(a));
            return;
        }
        // x AND y -> z
        matcher = and.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var b = matcher.group(2);
            var wire = matcher.group(3);
            wires.put(wire, () -> resolve(a) & resolve(b));
            return;
        }
        // p LSHIFT 2 -> q
        matcher = lshift.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var b = matcher.group(2);
            var wire = matcher.group(3);
            wires.put(wire, () -> resolve(a) << resolve(b));
            return;
        }
        // NOT e -> f
        matcher = not.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var wire = matcher.group(2);
            wires.put(wire, () -> ~resolve(a));
            return;
        }
        // x OR y -> e
        matcher = or.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var b = matcher.group(2);
            var wire = matcher.group(3);
            wires.put(wire, () -> resolve(a) | resolve(b));
            return;
        }
        // y RSHIFT 2 -> g
        matcher = rshift.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var b = matcher.group(2);
            var wire = matcher.group(3);
            wires.put(wire, () -> resolve(a) >> resolve(b));
            return;
        }
        throw new IllegalArgumentException("Failed to parse line '" + line + "'");
    }

}

void main() {
    var circuit = new Circuit();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(circuit::parse);
    var aVal = circuit.resolve("a");
    circuit.parse(aVal + " -> b");
    circuit.resetMemo();
    IO.println(circuit.resolve("a"));
}
