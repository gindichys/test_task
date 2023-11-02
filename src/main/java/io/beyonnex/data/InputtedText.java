package io.beyonnex.data;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.sort;

public class InputtedText {

    private static final String REGEXP = "[^\\p{L}0-9]";

    private final String originalInput;

    private final String normalisedText;

    private final String key;

    public InputtedText(String text) {
        this.originalInput = text;
        this.normalisedText = normaliseText(text);
        this.key = createKey(this.normalisedText);
    }

    private String normaliseText(String text) {
        return Arrays.stream(text.toLowerCase().split(REGEXP))
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.joining(" "));
    }

    private String createKey(String text) {
        char[] chars = text.replaceAll(" ", "").toCharArray();
        sort(chars);
        return Arrays.toString(chars);
    }

    public String getOriginalInput() {
        return originalInput;
    }

    public String getNormalisedText() {
        return normalisedText;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        int result = normalisedText.hashCode();
        result = 31 * result + key.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InputtedText text = (InputtedText) o;

        if (!normalisedText.equals(text.normalisedText)) {
            return false;
        }
        return key.equals(text.key);
    }
}
