import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/*
FIXME : This runtime stats are horrendous!
TODO: Replace length-limiting BFS with sans-seen BFS?

time java Part2v4.java < input.txt
207

________________________________________________________
Executed in   55.63 secs    fish           external
   usr time   16.37 mins  702.00 micros   16.37 mins
   sys time    0.77 mins  240.00 micros    0.77 mins
 */

class Chemistry {

    private static final Pattern REPLACEMENT = Pattern.compile(
            "^(\\w+) => (\\w+)$");

    private final Map<Pattern, List<String>> replacements = new HashMap<>();
    private String molecule;

    void parseReplacement(String line) {
        var matcher = REPLACEMENT.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
        }
        var newKey = Pattern.compile(matcher.group(2), Pattern.LITERAL);
        replacements.computeIfAbsent(newKey, _ -> new ArrayList<>())
                .add(matcher.group(1));
    }

    void parseMolecule(String line) {
        molecule = line;
    }

    private List<String> simplify(Collection<String> molecules) {
        return molecules.parallelStream()
                .flatMap(oldMolecule -> replacements.entrySet().stream()
                        .flatMap(replacement -> replacement.getValue().stream()
                                .map(alt -> replacement.getKey()
                                        .matcher(oldMolecule)
                                        .replaceFirst(alt))
                                .filter(s -> !s.equals(oldMolecule))
                                .filter(s -> s.length() <= oldMolecule.length())))
                .distinct()
                .toList();
    }

    int convergeToE() {
        var iterations = 0;
        var newMolecules = List.of(molecule);
        while (!newMolecules.contains("e")) {
            newMolecules = simplify(newMolecules);
            var minLength = newMolecules.stream()
                    .mapToInt(String::length)
                    .min()
                    .orElseThrow();
            newMolecules = newMolecules.stream()
                    .filter(s -> s.length() == minLength)
                    .toList();
            iterations++;
        }
        return iterations;
    }

}

void main() {
    var chemistry = new Chemistry();
    Stream.generate(IO::readln)
            .takeWhile(s -> !s.isBlank())
            .forEach(chemistry::parseReplacement);
    chemistry.parseMolecule(IO.readln());
    IO.println(chemistry.convergeToE());
}
