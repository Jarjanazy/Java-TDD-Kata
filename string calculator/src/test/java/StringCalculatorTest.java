import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

    StringCalculator stringCalculator;
    @BeforeEach
    void init(){ stringCalculator = new StringCalculator(); }

    @Test
    void whenGivenStringIsEmpty_Return0(){
        int result1 = stringCalculator.checkAndAdd("");
        assertEquals(0, result1);

        int result2 = stringCalculator.checkAndAdd(" ");
        assertEquals(0, result2);
    }

    @Test
    void whenGivenStringHasOneNumber_ReturnIt(){
        int result = stringCalculator.checkAndAdd("1");
        assertEquals(1, result);

        int result2 = stringCalculator.checkAndAdd(" 1 ");
        assertEquals(1, result2);
    }

    @Test
    void whenGivenStringHasTwoNumbers_AddAndReturnThem(){
        int result = stringCalculator.checkAndAdd("1,2");
        assertEquals(3, result);
        int result2 = stringCalculator.checkAndAdd(" 5,5 ");
        assertEquals(10, result2);
    }


}
