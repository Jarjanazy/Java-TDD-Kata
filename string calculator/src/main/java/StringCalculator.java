import java.util.*;
import java.util.regex.Matcher;
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
        else return checkNumbersAndAdd(new ArrayList<>(Collections.singletonList(defaultDelimiter)), s);
    }

    private Integer extractDelimiterAndAdd(String s) {
        List<String> delimiters = extractDelimiters(s);
        String string = extractString(s);
        return checkNumbersAndAdd(delimiters, string);
    }

    private List<String> extractDelimiters(String s) {
        if(hasDelimitersInBrackets(s)) return extractDelimitersFromBrackets(s);
        return new ArrayList<>(Collections.singletonList(s.substring(2, 3)));
    }

    private List<String> extractDelimitersFromBrackets(String s) {
        List<String> delimiters = new ArrayList<>();
        Matcher m = Pattern.compile("\\[.*?\\]").matcher(s);
        while (m.find()) { delimiters.add(getDelimiterFromWithinTheBracket(m)); }
        return delimiters;
    }

    private String getDelimiterFromWithinTheBracket(Matcher m) {
        String delimiter = m.group();
        return delimiter.substring(1, delimiter.length()-1);
    }

    private boolean hasDelimitersInBrackets(String s) {
        return s.matches(".*\\[.*\\]((.*|\\n)*)");
    }

    private Integer checkNumbersAndAdd(List<String> delimiters, String s) {
        List<String> numbers = new ArrayList<>();
        extractNumbersAsStrings(delimiters, s, numbers);

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

    private void extractNumbersAsStrings(List<String> delimiters, String s, List<String> numbers) {
        if(stringCantBeSplit(s, delimiters)) numbers.add(s);

        else{
            String currentDelimiter = delimiters.remove(0);

            List<String> stringsByDelimiter = splitStringByDelimiter(currentDelimiter, s);

            stringsByDelimiter.forEach(stringByDelimiter -> extractNumbersAsStrings(delimiters, stringByDelimiter, numbers));
        }
    }

    private boolean stringCantBeSplit(String s, List<String> delimiters) {
        return delimiters.stream().noneMatch(d -> s.split(Pattern.quote(d)).length > 1);
    }

    private List<String> splitStringByDelimiter(String delimiter, String s) {
        String usefulString = s.replace("\n", delimiter);
        return Arrays.asList(usefulString.split(Pattern.quote(delimiter))); // Pattern.quote to avoid problems with regex special keywords
    }


    private String extractString(String s) {
        if (hasDelimitersInBrackets(s)) return s.substring(s.indexOf("\n") + 1);
        return s.substring(4);
    }
}
