package ru.nikita.labs.util;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@UtilityClass
public class Crypto {
    private static final String PEPPER = "P3pP3rS3c$EtC0dE";

    public static final String SHA_256 = "SHA-256";
    public static final String SHA_1_PRNG = "SHA1PRNG";

    public String sha256Hex(String str) {
        return convertByteToHex(getByteHash(PEPPER + str));
    }

    public String sha256Hex(String str, String salt) {
        return convertByteToHex(getByteHash(PEPPER + str + salt));
    }

    public String getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance(SHA_1_PRNG);
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return convertByteToHex(salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertByteToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static byte[] getByteHash(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            return digest.digest(str.getBytes(
                    StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
