String increment(String password) {
    var codepoints = password.codePoints().toArray();
    for (var i = codepoints.length - 1; i >= 0; i--) {
        codepoints[i]++;
        if (codepoints[i] > 'z') {
            codepoints[i] = 'a';
        } else {
            return IntStream.of(codepoints)
                    .mapToObj(Character::toString)
                    .collect(Collectors.joining());
        }
    }
    throw new IllegalStateException("Failed to increment password past 'zzzzzzzz'!");
}

static final String FORBIDDEN = "iol";

boolean isValid(String password) {
    var hasThreeLetterStraight = password.codePoints()
            .boxed()
            .gather(Gatherers.windowSliding(3))
            .anyMatch(w -> w.get(0) == w.get(1) - 1
                    && w.get(0) == w.get(2) - 2);
    var hasNoForbidden = password.codePoints()
            .mapToObj(Character::toString)
            .noneMatch(FORBIDDEN::contains);
    var hasTwoDistinctPairs = password.codePoints()
            .mapToObj(Character::toString)
            .gather(Gatherers.windowSliding(2))
            .filter(w -> w.getFirst().equals(w.getLast()))
            .distinct()
            .count() >= 2;
    return hasThreeLetterStraight && hasNoForbidden && hasTwoDistinctPairs;
}

void main() {
    var password = "cqjxjnds";
    do {
        password = increment(password);
    } while (!isValid(password));
    IO.println(password);
}
