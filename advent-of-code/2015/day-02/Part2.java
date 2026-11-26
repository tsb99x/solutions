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

    int calcLength() {
        var smallestSides = IntStream.of(l, w, h)
                .sorted()
                .limit(2);
        var length = 2 * smallestSides.sum();
        var bow = l * w * h;
        return length + bow;
    }

}

void main() {
    var totalLength = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .map(Box::new)
            .mapToInt(Box::calcLength)
            .sum();
    IO.println(totalLength);
}
