int sizeDiff(String line) {
    var encoded = line.replace("\\", "\\\\")
            .replace("\"", "\\\"");
    encoded = "\"" + encoded + "\"";
    return encoded.length() - line.length();
}

void main() {
    var res = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .mapToInt(this::sizeDiff)
            .sum();
    IO.println(res);
}
