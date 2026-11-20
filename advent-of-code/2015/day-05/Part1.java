boolean isNice(String s) {
    var vowels = "aeiou";
    var hasAtLeast3Vowels = s.codePoints()
            .filter(ch -> vowels.indexOf(ch) >= 0)
            .count() >= 3;

    var hasDuplicateChars = s.codePoints()
            .boxed()
            .gather(Gatherers.windowSliding(2))
            .anyMatch(w -> w.getFirst().equals(w.getLast()));

    var forbidden = List.of("ab", "cd", "pq", "xy");
    var hasNoForbidden = forbidden.stream()
            .noneMatch(s::contains);

    return hasAtLeast3Vowels
            && hasDuplicateChars
            && hasNoForbidden;
}

void main() {
    var counter = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .filter(this::isNice)
            .count();
    IO.println(counter);
}
