import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.*;

public class Application {

    public static void main(String[] args) throws IOException {
        new Application().run();
    }

    public void run() throws IOException {
        final var scanner = new Scanner(System.in);
        System.out.print("name > ");
        final var name = scanner.nextLine();
        System.out.print("years > ");
        final var years = scanner.nextLine();
        System.out.print("username > ");
        final var username = scanner.nextLine();

        var result =
                "your name is '" + name + "', " +
                "you are '" + years + "' years old, " +
                "and your username is '" + username + "'";

        System.out.println(result);

        var logPath = Paths.get("log.txt");
        Files.writeString(logPath, result + '\n', CREATE, APPEND);
    }
}
