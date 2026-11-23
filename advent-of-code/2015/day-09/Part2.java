import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

record Destination(
        String location,
        int distance) {
}

class Graph {

    private static final Pattern PATTERN = Pattern.compile("^(\\w+) to (\\w+) = (\\d{1,3})$");

    private final Map<String, List<Destination>> flights = new HashMap<>();

    void addEntry(String from, Destination destination) {
        flights.compute(from, (k, v) -> {
            v = v != null ? v : new ArrayList<>();
            v.add(destination);
            return v;
        });
    }

    void parse(String line) {
        var matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'");
        }
        var from = matcher.group(1);
        var to = matcher.group(2);
        var distance = Integer.parseInt(matcher.group(3));
        addEntry(from, new Destination(to, distance));
        addEntry(to, new Destination(from, distance));
    }

    int findLongestPath() {
        return flights.keySet()
                .stream()
                .mapToInt(x -> findLongestPath(List.of(), x, 0))
                .max()
                .orElseThrow();
    }

    private int findLongestPath(List<String> visited, String current, int travelDistance) {
        var currentPath = new ArrayList<>(visited);
        currentPath.add(current);
        return flights.get(current)
                .stream()
                .filter(x -> !visited.contains(x.location))
                .mapToInt(x -> findLongestPath(currentPath, x.location, travelDistance + x.distance))
                .max()
                .orElse(travelDistance);
    }

}

void main() {
    var graph = new Graph();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(graph::parse);
    IO.println(graph.findLongestPath());
}
