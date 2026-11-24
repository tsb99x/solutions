import java.util.regex.MatchResult;

static final Pattern OBJECT = Pattern.compile("\\{[^{]+?}");
static final Pattern INTEGER = Pattern.compile("(-?\\d{1,9})");

String sumInts(String s) {
    var sum = INTEGER.matcher(s)
            .results()
            .map(MatchResult::group)
            .mapToInt(Integer::parseInt)
            .sum();
    return Integer.toString(sum);
}

void main() {
    var input = IO.readln();
    for (var m = OBJECT.matcher(input); m.find(); m = OBJECT.matcher(input)) {
        input = m.replaceAll(r -> {
            var object = r.group();
            return object.contains(":\"red\"") ? "null" : sumInts(object);
        });
    }
    IO.println(sumInts(input));
}
