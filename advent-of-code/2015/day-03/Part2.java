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
        }
        return new House(x, y);
    }

}

void main() {
    var santa = new Santa();
    var robo = new Santa();
    var selection = Arrays.asList(santa, robo);
    var houses = IO.readln()
            .codePoints()
            .mapToObj(ch -> {
                var house = selection.get(0).move(ch);
                Collections.rotate(selection, 1);
                return house;
            })
            .distinct()
            .count();
    IO.println(houses);
}
