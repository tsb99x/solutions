class Reindeer {

    private enum State {
        FLYING,
        RESTING
    }

    private static final Pattern PATTERN = Pattern.compile(
            "^(\\w+) can fly (\\d{1,3}) km/s for (\\w{1,3}) seconds, but then must rest for (\\w{1,3}) seconds.$");

    private final String name;
    private final int kmPerSecond;
    private final int maxSecondsFlying;
    private final int maxSecondsResting;

    private State state = State.FLYING;
    private int distanceTraveled = 0;
    private int secondsToNextState;
    private int points = 0;

    int getDistanceTraveled() {
        return distanceTraveled;
    }

    void awardPoint() {
        points++;
    }

    int getPoints() {
        return points;
    }

    Reindeer(String line) {
        var matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'");
        }
        name = matcher.group(1);
        kmPerSecond = Integer.parseInt(matcher.group(2));
        maxSecondsFlying = Integer.parseInt(matcher.group(3));
        maxSecondsResting = Integer.parseInt(matcher.group(4));
        secondsToNextState = maxSecondsFlying;
    }

    void simulateOneSecond() {
        if (state == State.FLYING) {
            distanceTraveled += kmPerSecond;
        }
        secondsToNextState--;
        if (secondsToNextState <= 0) {
            switch (state) {
                case State.FLYING -> {
                    state = State.RESTING;
                    secondsToNextState = maxSecondsResting;
                }
                case State.RESTING -> {
                    state = State.FLYING;
                    secondsToNextState = maxSecondsFlying;
                }
            }
        }
    }

}

void main() {
    var reindeers = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .map(Reindeer::new)
            .toList();
    for (var i = 0; i < 2503; i++) {
        reindeers.forEach(Reindeer::simulateOneSecond);
        var leaderDistance = reindeers.stream()
                .mapToInt(Reindeer::getDistanceTraveled)
                .max()
                .orElse(0);
        reindeers.stream()
                .filter(x -> x.getDistanceTraveled() == leaderDistance)
                .forEach(Reindeer::awardPoint);
    }
    var maxPoints = reindeers.stream()
            .mapToInt(Reindeer::getPoints)
            .max()
            .orElse(0);
    IO.println(maxPoints);
}
