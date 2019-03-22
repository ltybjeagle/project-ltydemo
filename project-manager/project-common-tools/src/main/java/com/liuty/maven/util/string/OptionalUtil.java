package com.liuty.maven.util.string;

import java.util.Optional;

/**
 * Optional tools
 */
public class OptionalUtil {

    /**
     * 字符串转数字
     * @param s
     * @return
     */
    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }
}
