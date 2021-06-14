package com.henriquephil.wishlist.util;

import java.util.Random;

public class RandomHexIdGenerator {
    public static String generate(int size) {
        byte[] arr = new byte[size];
        new Random().nextBytes(arr);
        StringBuilder hex = new StringBuilder();
        for (byte b : arr) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
