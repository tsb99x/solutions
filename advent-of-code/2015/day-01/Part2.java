record State(
        int idx,
        int floor) {
}

void main() {
    IO.readln()
            .codePoints()
            .map(ch -> switch (ch) {
                case '(' -> 1;
                case ')' -> -1;
                default -> 0;
            })
            .boxed()
            .gather(Gatherers.scan(
                    () -> new State(0, 0),
                    (s, move) -> new State(s.idx + 1, s.floor + move)))
            .filter(s -> s.floor < 0)
            .findFirst()
            .ifPresentOrElse(
                    s -> IO.println(s.idx),
                    () -> IO.println("Santa wasn't in the basement!"));
}
