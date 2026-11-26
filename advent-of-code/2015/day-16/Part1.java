class AuntsIndex {

    private static final Pattern SUE_IDX = Pattern.compile("^Sue (\\d{1,3}): ");
    private static final Pattern SUE_COM = Pattern.compile("(\\w+): (\\d{1,3})");

    private final Map<Integer, Map<String, Integer>> aunts = new HashMap<>();

    void parse(String line) {
        var matcher = SUE_IDX.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Failed to parse line '" + line + "'!");
        }
        var index = Integer.parseInt(matcher.group(1));
        var params = SUE_COM.matcher(line)
                .results()
                .collect(Collectors.toMap(
                        r -> r.group(1),
                        r -> Integer.parseInt(r.group(2))));
        aunts.put(index, params);
    }

    private boolean check(Map<String, Integer> params, String name, int value) {
        var param = params.get(name);
        return param == null || param == value;
    }

    int findAunt() {
        return aunts.entrySet()
                .stream()
                .filter(kv -> {
                    var params = kv.getValue();
                    return check(params, "children", 3)
                            && check(params, "cats", 7)
                            && check(params, "samoyeds", 2)
                            && check(params, "pomeranians", 3)
                            && check(params, "akitas", 0)
                            && check(params, "vizslas", 0)
                            && check(params, "goldfish", 5)
                            && check(params, "trees", 3)
                            && check(params, "cars", 2)
                            && check(params, "perfumes", 1);
                })
                .findFirst()
                .orElseThrow()
                .getKey();
    }

}

void main() {
    var cabinet = new AuntsIndex();
    Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .forEach(cabinet::parse);
    IO.println(cabinet.findAunt());
}
