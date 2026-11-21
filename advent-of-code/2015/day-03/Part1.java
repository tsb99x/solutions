record House(
        int x,
        int y) {
}

class Santa {

    private int x = 0;
    private int y = 0;

    House move(int ch) {
        switch (ch) {
            case '>' -> x++;
            case '<' -> x--;
            case '^' -> y--;
            case 'v' -> y++;
            default -> {
                /* NOOP */ }
        }
        return new House(x, y);
    }

}

void main() {
    var santa = new Santa();
    var houses = IO.readln()
            .codePoints()
            .mapToObj(santa::move)
            .distinct()
            .count();
    IO.println(houses);
}
