import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

void main() throws NoSuchAlgorithmException {
    var md5 = MessageDigest.getInstance("MD5");
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
        var str = "ckczppom" + Integer.toString(i);
        var bytes = md5.digest(str.getBytes());
        var res = HexFormat.of().formatHex(bytes);
        if (res.startsWith("00000")) {
            IO.println(i);
            return;
        }
        md5.reset();
    }
    IO.println("Failed to find MD5 with leading zeroes!");
}
