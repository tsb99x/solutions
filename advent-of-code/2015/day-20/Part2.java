// FIXME : THE most straight-forward solution possible, uses A LOT of CPU!
void main() {
    var house = IntStream.iterate(1, i -> i + 1)
            .parallel()
            .filter(n -> {
                var presentsCount = n * 11 + IntStream.rangeClosed(1, n / 2)
                        .filter(x -> n % x == 0 && n / x <= 50)
                        .map(x -> x * 11)
                        .sum();
                return presentsCount >= 36_000_000;
            })
            .findFirst()
            .orElseThrow();
    IO.println(house);
}
