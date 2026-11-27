class Lights {

    private static final Pattern PATTERN = Pattern.compile(
            "^(turn on|toggle|turn off) (\\d{1,3}),(\\d{1,3}) through (\\d{1,3}),(\\d{1,3})$");

    private final int[][] grid = new int[1000][1000];

    void apply(String cmd) {
        var matcher = PATTERN.matcher(cmd);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse command '" + cmd + "'!");
        }
        var op = matcher.group(1);
        var fromX = Integer.parseInt(matcher.group(2));
        var fromY = Integer.parseInt(matcher.group(3));
        var toX = Integer.parseInt(matcher.group(4));
        var toY = Integer.parseInt(matcher.group(5));
        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                switch (op) {
                    case "turn on" -> grid[x][y] += 1;
                    case "toggle" -> grid[x][y] += 2;
                    case "turn off" -> grid[x][y] = Math.max(grid[x][y] - 1, 0);
                    default -> throw new IllegalArgumentException("Failed to apply operation '" + op + "'!");
                }
            }
        }
    }

    int totalBrightness() {
        int counter = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                counter += grid[x][y];
            }
        }
        return counter;
    }
}

void main() {
    var lights = new Lights();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(lights::apply);
    IO.println(lights.totalBrightness());
}
