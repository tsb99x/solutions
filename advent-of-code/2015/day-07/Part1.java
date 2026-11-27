import java.util.HashMap;
import java.util.function.IntSupplier;
import java.util.regex.Pattern;

class Circuit {

    private static final Pattern input = Pattern.compile("^(\\w+) -> (\\w+)$");
    private static final Pattern not = Pattern.compile("^NOT (\\w+) -> (\\w+)$");
    private static final Pattern binary = Pattern.compile("^(\\w+) (\\w+) (\\w+) -> (\\w+)$");

    private final Map<String, IntSupplier> wires = new HashMap<>();

    int resolve(String dep) {
        if (dep.codePoints().allMatch(Character::isDigit)) {
            return Integer.parseInt(dep);
        }
        var val = wires.get(dep).getAsInt();
        wires.replace(dep, () -> val);
        return val;
    }

    void parse(String line) {
        var matcher = input.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var wire = matcher.group(2);
            wires.put(wire, () -> resolve(a));
            return;
        }
        matcher = not.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var wire = matcher.group(2);
            wires.put(wire, () -> ~resolve(a));
            return;
        }
        matcher = binary.matcher(line);
        if (matcher.find()) {
            var a = matcher.group(1);
            var op = matcher.group(2);
            var b = matcher.group(3);
            var wire = matcher.group(4);
            wires.put(wire, switch (op) {
                case "AND" -> () -> resolve(a) & resolve(b);
                case "LSHIFT" -> () -> resolve(a) << resolve(b);
                case "OR" -> () -> resolve(a) | resolve(b);
                case "RSHIFT" -> () -> resolve(a) >> resolve(b);
                default -> throw new IllegalArgumentException("Failed to parse op '" + op + "'!");
            });
            return;
        }
        throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
    }

}

void main() {
    var circuit = new Circuit();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(circuit::parse);
    IO.println(circuit.resolve("a"));
}
