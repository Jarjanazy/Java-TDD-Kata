import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class StringCalculator {
    private final String defaultDelimiter = ",";

    public int checkLengthAndAdd(String s){
        String trimmedString = s.trim();

        if (trimmedString.isEmpty()) return 0;
        else if (trimmedString.length() == 1) return Integer.parseInt(trimmedString);

        return checkDelimiterAndAddNumbers(trimmedString);
    }



    private Integer checkDelimiterAndAddNumbers(String s) {
        if (s.startsWith("//"))  {
            String delimiter = s.substring(2, 3);
            String usefulString = extractUsefulString(s);
            return add(delimiter, usefulString);
        }
        else return add(defaultDelimiter, s);
    }

    private Integer add(String delimiter, String s) {
        List<String> numbers = extractNumberAsStrings(delimiter, s);

        Optional<Integer> sum = numbers.stream().map(Integer::parseInt).reduce(Integer::sum);

        if (sum.isPresent()) return sum.get();
        else throw new RuntimeException("Something bad happened");
    }

    private List<String> extractNumberAsStrings(String delimiter, String s) {
        String usefulString = s.replace("\n", delimiter);
        return Arrays.asList(usefulString.split(Pattern.quote(delimiter))); // Pattern.quote to avoid problems with regex special keywords
    }


    private String extractUsefulString(String s) {
        return s.substring(4);

    }

}
