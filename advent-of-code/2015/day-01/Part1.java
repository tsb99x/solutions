void main() {
    var floor = IO.readln()
            .codePoints()
            .map(ch -> switch (ch) {
                case '(' -> 1;
                case ')' -> -1;
                default -> 0;
            })
            .sum();
    IO.println(floor);
}
