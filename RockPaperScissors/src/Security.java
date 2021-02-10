import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Security {
    String key;
    public void createKey(byte [] bytes){
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        key = String.format("%032X", new BigInteger(1, bytes));
    }

    public void HMAC(String moveComputer, byte [] byteKey) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteKey, "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);

        byte [] mac_data = sha256_HMAC.doFinal(moveComputer.getBytes(StandardCharsets.UTF_8));
        System.out.println("HMAC: " + String.format("%032X", new BigInteger(1, mac_data)));
    }

}
