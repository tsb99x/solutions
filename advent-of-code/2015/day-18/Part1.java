import java.util.stream.Stream;

class Grid {

    private int[][] curr = new int[102][102];
    private int[][] next = new int[102][102];

    void parse(String[] lines) {
        for (var y = 0; y < lines.length; y++) {
            var line = lines[y];
            for (var x = 0; x < line.length(); x++) {
                curr[y + 1][x + 1] = switch (line.codePointAt(x)) {
                    case '#' -> 1;
                    case '.' -> 0;
                    default -> 0;
                };
            }
        }
    }

    void iterate() {
        for (var row : next) {
            Arrays.fill(row, 0);
        }
        for (var y = 1; y < 101; y++) {
            for (var x = 1; x < 101; x++) {
                var neighbors = curr[y - 1][x - 1]
                        + curr[y - 1][x]
                        + curr[y - 1][x + 1]
                        + curr[y][x - 1]
                        + curr[y][x + 1]
                        + curr[y + 1][x - 1]
                        + curr[y + 1][x]
                        + curr[y + 1][x + 1];
                if ((curr[y][x] == 1 && neighbors == 2)
                        || neighbors == 3) {
                    next[y][x] = 1;
                }
            }
        }
        var swap = curr;
        curr = next;
        next = swap;
    }

    int lightsOn() {
        var counter = 0;
        for (var y = 1; y < 101; y++) {
            for (var x = 1; x < 101; x++) {
                counter += curr[y][x];
            }
        }
        return counter;
    }

}

void main() {
    var grid = new Grid();
    var input = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .toArray(String[]::new);
    grid.parse(input);
    for (var i = 0; i < 100; i++) {
        grid.iterate();
    }
    IO.println(grid.lightsOn());
}
