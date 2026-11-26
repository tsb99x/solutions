// FIXME : suboptimal?
List<List<Integer>> combinations(Collection<Integer> containers, int litersToFill) {
    var result = new ArrayList<List<Integer>>();
    var containersLeft = new ArrayList<>(containers);
    for (var container : containers) {
        var newLimit = litersToFill - container;
        if (newLimit < 0) {
            continue;
        }
        containersLeft.remove(container);
        for (var variant : combinations(containersLeft, newLimit)) {
            variant.add(container);
            result.add(variant);
        }
    }
    if (result.isEmpty() && litersToFill == 0) {
        result.add(new ArrayList<>());
    }
    return result;
}

void main() {
    var containers = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .map(Integer::parseInt)
            .toList();
    var uniqueCombinations = combinations(containers, 150);
    var minNumOfContainers = uniqueCombinations.stream()
            .mapToInt(List::size)
            .min()
            .orElseThrow();
    var combinationsWithMinCount = uniqueCombinations.stream()
            .filter(s -> s.size() == minNumOfContainers)
            .toList();
    IO.println(combinationsWithMinCount.size());
}
