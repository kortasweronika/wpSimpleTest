import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PepperGenerate {
    public static void main(String[] args) {
        SecureRandom sr = null;
        try {
            sr = java.security.SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] pepper = new byte[32];
        sr.nextBytes(pepper);
        String base64 = java.util.Base64.getEncoder().encodeToString(pepper);
        System.out.println(base64);
    }
}