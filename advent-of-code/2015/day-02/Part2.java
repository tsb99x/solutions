import java.util.regex.Pattern;

static final Pattern PATTERN = Pattern.compile(
        "^(\\d{1,3})x(\\d{1,3})x(\\d{1,3})$");

int calcLength(String line) {
    var matcher = PATTERN.matcher(line);
    if (!matcher.find()) {
        throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
    }
    var l = Integer.parseInt(matcher.group(1));
    var w = Integer.parseInt(matcher.group(2));
    var h = Integer.parseInt(matcher.group(3));
    var smallestSides = IntStream.of(l, w, h)
            .sorted()
            .limit(2);
    var length = 2 * smallestSides.sum();
    var bow = l * w * h;
    return length + bow;
}

void main() {
    var totalLength = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .mapToInt(this::calcLength)
            .sum();
    IO.println(totalLength);
}
