package challenges;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EasyInput {

    public static void main(String[] args) throws IOException {
        var con = System.console();
        var msg = String.format(
                "your name is %s, you are %s years old, and your username is %s%n",
                con.readLine("your name > "),
                con.readLine("your age > "),
                con.readLine("your username > "));

        con.format(msg);
        Files.writeString(Paths.get("log.txt"), msg, CREATE, APPEND);
    }

}
