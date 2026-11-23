boolean isNice(String s) {
    var span = 2;
    var hasRepeatingSubsequence = s.codePoints()
            .boxed()
            .gather(Gatherers.windowSliding(span))
            .map(w -> w.stream()
                    .map(Character::toString)
                    .collect(Collectors.joining()))
            .map(ws -> s.lastIndexOf(ws) - s.indexOf(ws))
            .anyMatch(d -> d >= span);

    var hasRepeatingLetter = s.codePoints()
            .boxed()
            .gather(Gatherers.windowSliding(3))
            .anyMatch(w -> w.getFirst().equals(w.getLast()));

    return hasRepeatingSubsequence && hasRepeatingLetter;
}

void main() {
    var counter = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .filter(this::isNice)
            .count();
    IO.println(counter);
}
