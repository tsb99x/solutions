import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class Chemistry {

    private static final Pattern REPLACEMENT = Pattern.compile(
            "^(\\w+) => (\\w+)$");
    private static final Pattern PARTS = Pattern.compile(
            "([A-Z][a-z]*)");

    private final Map<String, List<String>> replacements = new HashMap<>();
    private String[] molecule;

    void parseReplacement(String line) {
        var matcher = REPLACEMENT.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
        }
        replacements.computeIfAbsent(matcher.group(1), _ -> new ArrayList<>())
                .add(matcher.group(2));
    }

    void parseMolecule(String line) {
        molecule = PARTS.matcher(line)
                .results()
                .map(MatchResult::group)
                .toArray(String[]::new);
    }

    int countVariations() {
        var variations = new HashSet<String>();
        for (var i = 0; i < molecule.length; i++) {
            var alts = replacements.getOrDefault(molecule[i], List.of());
            for (var alt : alts) {
                var newMolecule = molecule.clone();
                newMolecule[i] = alt;
                var str = String.join("", newMolecule);
                variations.add(str);
            }
        }
        return variations.size();
    }

}

void main() {
    var chemistry = new Chemistry();
    Stream.generate(IO::readln)
            .takeWhile(s -> !s.isBlank())
            .forEach(chemistry::parseReplacement);
    chemistry.parseMolecule(IO.readln());
    IO.println(chemistry.countVariations());
}
