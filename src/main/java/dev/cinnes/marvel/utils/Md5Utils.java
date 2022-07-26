package dev.cinnes.marvel.utils;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    @SneakyThrows(NoSuchAlgorithmException.class)
    public static String hash(String str) {
        final var digest = MessageDigest.getInstance("MD5");
        return bytesToHex(digest.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

    private static String bytesToHex(byte[] bytes) {
        final var sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}