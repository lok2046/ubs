package ubs;

import java.util.regex.Pattern;

public class StringAccumulator {

    private static final String DELIMITER_COMMA = ",";
    private static final String DELIMITER_NEWLINE = "\\n";
    private static final String DELIMITER_PIPE = "\\|";
    private static final String EXCEPTION_MSG_NEGATIVES_NOT_ALLOWED = "negatives not allowed";
    private static final String PREFIX_CUSTOM_DELIMITER = "//";

    public int add(String input) throws Exception {

        int sum = 0;

        if (input == null || input.isEmpty()) {
            return sum;
        }

        String numbers = input;
        final StringBuilder delimiterSB = new StringBuilder().append(DELIMITER_COMMA).append('|').append(DELIMITER_NEWLINE);

        if (input.startsWith(PREFIX_CUSTOM_DELIMITER)) {
            final String tokens[] = input.substring(PREFIX_CUSTOM_DELIMITER.length()).split(DELIMITER_NEWLINE, 2);
            final String customDelimiters = tokens[0];
            numbers = tokens[1];

            for (String customDelimiter : customDelimiters.split(DELIMITER_PIPE)) {
                delimiterSB.append('|').append(Pattern.quote(customDelimiter));
            }
        }

        final String[] numberTokens = numbers.split(delimiterSB.toString());
        final StringBuilder negativesSB = new StringBuilder();

        for (String numberToken : numberTokens) {
            final long number = Long.parseLong(numberToken);
            if (number < 0) {
                if (negativesSB.length() > 0) {
                    negativesSB.append(DELIMITER_COMMA);
                }
                negativesSB.append(number);
                continue;
            } else if (number <= 1000) {
                sum += number;
            }
        }

        if (negativesSB.length() > 0) {
            throw new Exception(EXCEPTION_MSG_NEGATIVES_NOT_ALLOWED + negativesSB.toString());
        }

        return sum;
    }
}
