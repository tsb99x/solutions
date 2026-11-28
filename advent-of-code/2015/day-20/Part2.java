void main() {
    var house = IntStream.iterate(1, i -> i + 1)
            .filter(n -> {
                var presentsCount = IntStream.rangeClosed(1, (int) Math.sqrt(n))
                        .filter(x -> n % x == 0)
                        .mapMulti((x, next) -> {
                            next.accept(x);
                            var div = n / x;
                            if (div != x) {
                                next.accept(div);
                            }
                        })
                        .filter(x -> n / x <= 50)
                        .sum();
                presentsCount *= 11;
                return presentsCount >= 36_000_000;
            })
            .findFirst()
            .orElseThrow();
    IO.println(house);
}
