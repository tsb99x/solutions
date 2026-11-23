import java.util.ArrayList;

void group(List<List<String>> acc, String x) {
    if (!acc.isEmpty()) {
        var last = acc.getLast();
        if (last.getLast().equals(x)) {
            last.add(x);
            return;
        }
    }
    var seq = new ArrayList<String>();
    seq.add(x);
    acc.add(seq);
}

String lookAndSay(String source) {
    return source.codePoints()
            .mapToObj(Character::toString)
            .collect(
                    ArrayList<List<String>>::new,
                    this::group,
                    List::addAll)
            .stream()
            .collect(
                    StringBuilder::new,
                    (acc, l) -> acc.append(l.size()).append(l.getFirst()),
                    StringBuilder::append)
            .toString();
}

void main() {
    var str = "1321131112";
    for (int i = 0; i < 40; i++) {
        str = lookAndSay(str);
    }
    IO.println(str.length());
}
