import java.util.regex.Pattern;

record Ingredient(
        String name,
        int capacity,
        int durability,
        int flavor,
        int texture,
        int calories) {

    private static final Pattern PATTERN = Pattern.compile(
            "^(\\w+): capacity (-?\\d{1,3}), durability (-?\\d{1,3}), flavor (-?\\d{1,3}), texture (-?\\d{1,3}), calories (-?\\d{1,3})$");

    static Ingredient parse(String line) {
        var matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'");
        }
        return new Ingredient(
                matcher.group(1),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4)),
                Integer.parseInt(matcher.group(5)),
                Integer.parseInt(matcher.group(6)));
    }

    Ingredient takeSpoons(int spoons) {
        return new Ingredient(
                name + "@" + spoons,
                capacity * spoons,
                durability * spoons,
                flavor * spoons,
                texture * spoons,
                calories * spoons);
    }

    Ingredient combine(Ingredient other) {
        return new Ingredient(
                name + "+" + other.name,
                capacity + other.capacity,
                durability + other.durability,
                flavor + other.flavor,
                texture + other.texture,
                calories + other.calories);
    }

    int totalScore() {
        return Math.max(0, capacity)
                * Math.max(0, durability)
                * Math.max(0, flavor)
                * Math.max(0, texture);
    }
}

// FIXME : Not the best implementation possible!
List<List<Integer>> variations(List<Integer> proportions) {
    var result = new ArrayList<List<Integer>>();
    for (var i = 0; i < proportions.size(); i++) {
        for (var j = 0; j < proportions.size(); j++) {
            if (i == j) {
                continue;
            }
            var variant = new ArrayList<>(proportions);
            variant.set(i, variant.get(i) + 1);
            variant.set(j, variant.get(j) - 1);
            if (variant.get(j) >= 0) {
                result.add(variant);
            }
        }
    }
    return result;
}

int applyProportions(List<Ingredient> ingredients, List<Integer> proportions) {
    var experiment = new ArrayList<>(ingredients);
    for (var i = 0; i < experiment.size(); i++) {
        experiment.set(i, experiment.get(i).takeSpoons(proportions.get(i)));
    }
    return experiment.stream()
            .reduce(Ingredient::combine)
            .map(Ingredient::totalScore)
            .orElse(0);
}

void main() {
    var ingredients = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .map(Ingredient::parse)
            .toList();
    // FIXME : This is hard-coded!
    var current = List.of(25, 25, 25, 25);
    var currTotal = applyProportions(ingredients, current);
    while (true) {
        var next = variations(current).stream()
                .max((a, b) -> {
                    var aTotal = applyProportions(ingredients, a);
                    var bTotal = applyProportions(ingredients, b);
                    return Integer.compare(aTotal, bTotal);
                })
                .get();
        var nextTotal = applyProportions(ingredients, current);
        if (nextTotal < currTotal) {
            break;
        }
        current = next;
        currTotal = nextTotal;
    }
    IO.println(currTotal);
}
