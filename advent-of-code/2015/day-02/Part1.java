import java.util.regex.Pattern;

class Box {

    private static final Pattern PATTERN = Pattern.compile(
            "^(\\d{1,3})x(\\d{1,3})x(\\d{1,3})$");

    private final int l;
    private final int w;
    private final int h;

    Box(String line) {
        var matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
        }
        l = Integer.parseInt(matcher.group(1));
        w = Integer.parseInt(matcher.group(2));
        h = Integer.parseInt(matcher.group(3));
    }

    int calcWrapping() {
        var slack = IntStream.of(l, w, h)
                .sorted()
                .limit(2)
                .reduce(1, (a, b) -> a * b);
        var wrapping = 2 * l * w + 2 * w * h + 2 * h * l;
        return wrapping + slack;
    }

}

void main() {
    var totalWrapping = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .map(Box::new)
            .mapToInt(Box::calcWrapping)
            .sum();
    IO.println(totalWrapping);
}
