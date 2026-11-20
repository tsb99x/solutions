import java.util.regex.Pattern;

int calcWrapping(String line) {
    var pattern = Pattern.compile("^(\\d+)x(\\d+)x(\\d+)$");
    var matcher = pattern.matcher(line);
    if (!matcher.find()) {
        throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
    }
    var l = Integer.parseInt(matcher.group(1));
    var w = Integer.parseInt(matcher.group(2));
    var h = Integer.parseInt(matcher.group(3));
    var slack = IntStream.of(l, w, h)
            .sorted()
            .limit(2)
            .reduce(1, (a, b) -> a * b);
    var wrapping = 2 * l * w + 2 * w * h + 2 * h * l;
    return wrapping + slack;
}

void main() {
    var totalWrapping = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .mapToInt(this::calcWrapping)
            .sum();
    IO.println(totalWrapping);
}
