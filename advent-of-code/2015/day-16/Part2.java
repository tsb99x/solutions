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

    private boolean checkExact(Map<String, Integer> params, String name, int value) {
        var param = params.get(name);
        return param == null || param == value;
    }

    private boolean checkGreaterThan(Map<String, Integer> params, String name, int value) {
        var param = params.get(name);
        return param == null || param > value;
    }

    private boolean checkFewerThan(Map<String, Integer> params, String name, int value) {
        var param = params.get(name);
        return param == null || param < value;
    }

    int findAunt() {
        return aunts.entrySet()
                .stream()
                .filter(kv -> {
                    var params = kv.getValue();
                    return checkExact(params, "children", 3)
                            && checkGreaterThan(params, "cats", 7)
                            && checkExact(params, "samoyeds", 2)
                            && checkFewerThan(params, "pomeranians", 3)
                            && checkExact(params, "akitas", 0)
                            && checkExact(params, "vizslas", 0)
                            && checkFewerThan(params, "goldfish", 5)
                            && checkGreaterThan(params, "trees", 3)
                            && checkExact(params, "cars", 2)
                            && checkExact(params, "perfumes", 1);
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
