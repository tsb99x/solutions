import java.util.function.IntUnaryOperator;
import java.util.regex.Pattern;

class Computer {

    private static final Pattern UNARY = Pattern.compile(
            "^(hlf|tpl|inc) ([ab])$");
    private static final Pattern JMP = Pattern.compile(
            "^(jmp) ([-+]?\\d{1,3})$");
    private static final Pattern BINARY = Pattern.compile(
            "^(jie|jio) ([ab]), ([-+]?\\d{1,3})$");

    private final List<String> instructions = new ArrayList<>();
    private int ip = 0;
    private int a = 0;
    private int b = 0;

    void storeInstruction(String line) {
        instructions.add(line);
    }

    void applyFn(String r, IntUnaryOperator fn) {
        switch (r) {
            case "a" -> a = fn.applyAsInt(a);
            case "b" -> b = fn.applyAsInt(b);
            default -> throw new IllegalArgumentException(
                    "Failed to applyFn to register '" + r + "'!");
        }
    }

    int resolve(String r) {
        return switch (r) {
            case "a" -> a;
            case "b" -> b;
            default -> throw new IllegalArgumentException(
                    "Failed to resolve register '" + r + "'!");
        };
    }

    int clock(String cmd) {
        var matcher = JMP.matcher(cmd);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(2));
        }

        matcher = UNARY.matcher(cmd);
        if (matcher.find()) {
            var op = matcher.group(1);
            var r = matcher.group(2);
            applyFn(r, switch (op) {
                case "hlf" -> (x -> x / 2);
                case "tpl" -> (x -> x * 3);
                case "inc" -> (x -> x + 1);
                default -> throw new IllegalArgumentException(
                        "Failed to parse op '" + op + "'!");
            });
            return 1;
        }

        matcher = BINARY.matcher(cmd);
        if (matcher.find()) {
            var op = matcher.group(1);
            var r = matcher.group(2);
            var offset = Integer.parseInt(matcher.group(3));
            return switch (op) {
                case "jie" -> (resolve(r) % 2 == 0) ? offset : 1;
                case "jio" -> (resolve(r) == 1) ? offset : 1;
                default -> throw new IllegalArgumentException(
                        "Failed to parse op '" + op + "'!");
            };
        }

        return 1;
    }

    void run() {
        while (ip < instructions.size()) {
            var cmd = instructions.get(ip);
            ip += clock(cmd);
        }
    }

}

void main() {
    var computer = new Computer();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(computer::storeInstruction);
    computer.applyFn("a", _ -> 1);
    computer.run();
    IO.println(computer.resolve("b"));
}
