import java.util.ArrayList;
import java.util.HashMap;

// FIXME : Extremely dirty and type-specific algorithm!
List<List<String>> permutations(Collection<String> elements) {
    var possibilities = new ArrayList<List<String>>();
    if (elements.isEmpty()) {
        possibilities.add(new ArrayList<>());
        return possibilities;
    }
    for (String element : elements) {
        var subElements = new ArrayList<>(elements);
        subElements.remove(element);
        var subPossibilities = permutations(subElements);
        for (List<String> subPos : subPossibilities) {
            subPos.addFirst(element);
        }
        possibilities.addAll(subPossibilities);
    }
    return possibilities;
}

class DinnerTable {

    private static final Pattern PATTERN = Pattern
            .compile("^(\\w+) would (gain|lose) (\\d{1,3}) happiness units by sitting next to (\\w+).$");

    private final Map<String, Map<String, Integer>> guests = new HashMap<>();

    void parse(String line) {
        var matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'");
        }
        var who = matcher.group(1);
        var effect = switch (matcher.group(2)) {
            case "gain" -> 1;
            case "lose" -> -1;
            default -> throw new IllegalStateException("Failed to parse effect '" + matcher.group(2) + "'");
        };
        var happiness = effect * Integer.parseInt(matcher.group(3));
        var other = matcher.group(4);
        var neighbors = guests.computeIfAbsent(who, k -> new HashMap<>());
        neighbors.put(other, happiness);
    }

    int bestArrangement() {
        var arrangements = permutations(guests.keySet());
        var bestArrangement = 0;
        for (var arrangement : arrangements) {
            arrangement.addFirst(arrangement.getLast());
            arrangement.addLast(arrangement.get(1));
            var totalHappiness = 0;
            for (int i = 1; i < arrangement.size() - 1; i++) {
                var effects = guests.get(arrangement.get(i));
                totalHappiness += effects.get(arrangement.get(i - 1));
                totalHappiness += effects.get(arrangement.get(i + 1));
            }
            bestArrangement = Math.max(bestArrangement, totalHappiness);
        }
        return bestArrangement;
    }

}

void main() {
    var dinnerTable = new DinnerTable();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(dinnerTable::parse);
    IO.println(dinnerTable.bestArrangement());
}
