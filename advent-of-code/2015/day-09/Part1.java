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
        flights.computeIfAbsent(from, _ -> new ArrayList<>())
                .add(destination);
    }

    void parse(String line) {
        var matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
        }
        var from = matcher.group(1);
        var to = matcher.group(2);
        var distance = Integer.parseInt(matcher.group(3));
        addEntry(from, new Destination(to, distance));
        addEntry(to, new Destination(from, distance));
    }

    int findShortestPath() {
        return flights.keySet()
                .stream()
                .mapToInt(x -> findShortestPath(List.of(), x, 0))
                .min()
                .orElseThrow();
    }

    private int findShortestPath(List<String> visited, String current, int travelDistance) {
        var currentPath = new ArrayList<>(visited);
        currentPath.add(current);
        return flights.get(current)
                .stream()
                .filter(x -> !visited.contains(x.location))
                .mapToInt(x -> findShortestPath(currentPath, x.location, travelDistance + x.distance))
                .min()
                .orElse(travelDistance);
    }

}

void main() {
    var graph = new Graph();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(graph::parse);
    IO.println(graph.findShortestPath());
}
