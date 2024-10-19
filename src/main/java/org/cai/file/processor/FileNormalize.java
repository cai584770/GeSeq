package org.cai.file.processor;

/**
 * @author cai584770
 * @date 2024/10/16 20:16
 * @Version
 */
public class FileNormalize {

    public static String normalize(String data) {
        return data.chars()
                .mapToObj(c -> (char) c)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString().replaceAll("(.{63})", "$1\n");
    }

    public static String remove(String data) {
        return data.replaceAll("\\s", "");
    }

    public static String insertNewlines(String text, int interval) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            result.append(text.substring(index, Math.min(index + interval, text.length())));
            if (index + interval < text.length()) {
                result.append("\n");
            }
            index += interval;
        }
        return result.toString();
    }


}
