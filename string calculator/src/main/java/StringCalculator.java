import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class StringCalculator {
    private final String defaultDelimiter = ",";
    private int calledCount = 0;

    public int checkLengthAndAdd(String s){
        calledCount++;
        String trimmedString = s.trim();

        if (trimmedString.isEmpty()) return 0;
        else if (trimmedString.length() == 1) return Integer.parseInt(trimmedString);

        return checkDelimiterAndAddNumbers(trimmedString);
    }

    public int getCalledCount(){
        return calledCount;
    }


    private Integer checkDelimiterAndAddNumbers(String s) {
        if (s.startsWith("//")) return extractDelimiterAndAdd(s);
        else return checkNumbersAndAdd(defaultDelimiter, s);
    }

    private Integer extractDelimiterAndAdd(String s) {
        String delimiter = s.substring(2, 3);
        String string = extractString(s);
        return checkNumbersAndAdd(delimiter, string);
    }

    private Integer checkNumbersAndAdd(String delimiter, String s) {
        List<String> numbers = extractNumberAsStrings(delimiter, s);

        if (hasNegativeNumbers(numbers)) throwErrorWithNegativeValuesMessage(numbers);

        return add(numbers);
    }

    private void throwErrorWithNegativeValuesMessage(List<String> numbers) {
        StringBuilder baseMessage = new StringBuilder("negatives not allowed");
        numbers.
                stream().
                map(Integer::parseInt).
                filter(i -> i < 0).
                forEach(i -> baseMessage.append(" ").append(i));
        throw new RuntimeException(baseMessage.toString());
    }

    private boolean hasNegativeNumbers(List<String> numbers) {
        return numbers.
                stream().
                map(Integer::parseInt).
                anyMatch(i -> i < 0);
    }

    private Integer add(List<String> numbers) {
        Optional<Integer> sum = numbers.
                stream().
                map(Integer::parseInt).
                filter(i -> i <= 1000).
                reduce(Integer::sum);
        if (sum.isPresent()) return sum.get();
        else throw new RuntimeException("Something bad happened");
    }

    private List<String> extractNumberAsStrings(String delimiter, String s) {
        String usefulString = s.replace("\n", delimiter);
        return Arrays.asList(usefulString.split(Pattern.quote(delimiter))); // Pattern.quote to avoid problems with regex special keywords
    }

    private String extractString(String s) {
        return s.substring(4);
    }
}
