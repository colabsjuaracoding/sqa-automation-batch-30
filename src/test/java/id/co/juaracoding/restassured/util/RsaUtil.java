package id.co.juaracoding.restassured.util;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {

    public static final String RSA_PUBLIC_KEY_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzMX6OysWzaizAsyFuablBfHUASPRRnqMosG/0dVIeOS9C2ckEotm5Z/aKEYUriYwuPqQOCm45VB+tpMChaBGi6tFt4ytu3crvBKvctziIgsR+dy2qZJKWDqyxjCJgso/0S/XDOVKfMYbqJ4Cy/e0nehBaage48nY2h5reEmrEOhMVbh+6izp31bQDYaA20/ouyuBTHwtLZdPsJOmSkVddNh7yHKJZqyHWMExyX+2Dvr4heCtIebanz5t5AHpeHvKLgIcgycfRv4wrKq4Lx3PacJfXmW0QqAO3jSW+i9dF0zAWmAr2PlAUok47zZa11OBeePD15jeJgb2UMwR5p9gsQIDAQAB";

    private RsaUtil() {
    }

    public static String encrypt(String plaintext) {
        return encrypt(plaintext, RSA_PUBLIC_KEY_BASE64);
    }

    public static String encrypt(String plaintext, String publicKeyBase64) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyBase64);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(spec);

            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(
                    "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, oaepSpec);
            byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Gagal enkripsi RSA — cek public key/plaintext", e);
        }
    }
}