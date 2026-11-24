static final Pattern INTEGER = Pattern.compile("(-?\\d{1,3})");

void main() {
    var input = IO.readln();
    var sum = INTEGER.matcher(input)
            .results()
            .map(MatchResult::group)
            .mapToInt(Integer::parseInt)
            .sum();
    IO.println(sum);
}
