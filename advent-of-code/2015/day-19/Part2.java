import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/*
FIXME : This runtime stats are horrendous!

time java Part2.java < input.txt
207

________________________________________________________
Executed in   24.49 secs    fish           external
   usr time  469.67 secs  625.00 micros  469.67 secs
   sys time   18.73 secs  341.00 micros   18.73 secs
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
                .unordered()
                .<String>mapMulti((oldMolecule, next) -> {
                    for (var replacement : replacements.entrySet()) {
                        var matcher = replacement.getKey().matcher(oldMolecule);
                        for (var alt : replacement.getValue()) {
                            var newMolecule = matcher.replaceFirst(alt);
                            if (!newMolecule.equals(oldMolecule)) {
                                next.accept(newMolecule);
                            }
                        }
                    }
                })
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
