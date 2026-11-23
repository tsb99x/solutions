int sizeDiff(String line) {
    var decoded = line.replace("\\\"", "\"")
            .replaceAll("\\\\x[0-9a-fA-F]{2}", "*")
            .replace("\\\\", "\\")
            .replaceAll("(^\")|(\"$)", "");
    return line.length() - decoded.length();
}

void main() {
    var res = Stream.generate(IO::readln)
            .takeWhile(Objects::nonNull)
            .mapToInt(this::sizeDiff)
            .sum();
    IO.println(res);
}
