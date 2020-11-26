import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StringCalculator {

    public int checkAndAdd(String s){
        String trimmedString = s.trim();

        if (trimmedString.isEmpty()) return 0;
        else if (trimmedString.length() == 1) return Integer.parseInt(trimmedString);

        return add(trimmedString);
    }

    private Integer add(String s) {
        List<String> numbers = Arrays.asList(s.split(","));
        Optional<Integer> sum = numbers.stream().map(Integer::parseInt).reduce(Integer::sum);

        if (sum.isPresent()) return sum.get();
        else throw new RuntimeException("Something bad happened");
    }

}
