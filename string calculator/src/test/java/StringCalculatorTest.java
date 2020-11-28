import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

    StringCalculator stringCalculator;
    @BeforeEach
    void init(){ stringCalculator = new StringCalculator(); }

    @Test
    void whenGivenStringIsEmpty_Return0(){
        int result1 = stringCalculator.checkLengthAndAdd("");
        assertEquals(0, result1);

        int result2 = stringCalculator.checkLengthAndAdd(" ");
        assertEquals(0, result2);
    }

    @Test
    void whenGivenStringHasOneNumber_ReturnIt(){
        int result = stringCalculator.checkLengthAndAdd("1");
        assertEquals(1, result);

        int result2 = stringCalculator.checkLengthAndAdd(" 1 ");
        assertEquals(1, result2);
    }

    @Test
    void whenGivenStringHasTwoNumbers_AddAndReturnThem(){
        int result = stringCalculator.checkLengthAndAdd("1,2");
        assertEquals(3, result);
        int result2 = stringCalculator.checkLengthAndAdd(" 5,5 ");
        assertEquals(10, result2);
    }

    @Test
    void whenGivenStringHasMoreThanTwoNumber_AddAndReturnThem(){
        int result = stringCalculator.checkLengthAndAdd("10,20,30");
        assertEquals(60, result);

        int result2 = stringCalculator.checkLengthAndAdd("1,10,100,1000");
        assertEquals(1111, result2);
    }

    @Test
    void whenGivenStringHasNewLinesAsDelimiter_AddAndReturnNumbers(){
        int result = stringCalculator.checkLengthAndAdd("4\n6,10");
        assertEquals(20, result);
        int result2 = stringCalculator.checkLengthAndAdd("3\n20\n50,7");
        assertEquals(80, result2);
    }

    @Test
    void whenGivenStringHasCustomDelimiters_AddAndReturnNumbers(){
        int result = stringCalculator.checkLengthAndAdd("//;\n10;0;6");
        assertEquals(16, result);
        int result2 = stringCalculator.checkLengthAndAdd("//.\n2.4.5");
        assertEquals(11, result2);
    }

    @Test
    void whenGivenStringHasCustomDelimitersAndNewLine_addAndReturnNumbers(){
        int result = stringCalculator.checkLengthAndAdd("//|\n9|9\n9\n20");
        assertEquals(47, result);
    }

    @Test
    void whenGivenStringHasNegativeNumbers_ThrowError(){
        RuntimeException thrown  = assertThrows(RuntimeException.class, () -> stringCalculator.checkLengthAndAdd("10,-1,5"));
        assertTrue(thrown.getMessage().contains("negatives not allowed -1"));

        RuntimeException thrown2 = assertThrows(RuntimeException.class, () -> stringCalculator.checkLengthAndAdd("//|\n9|22|-100"));
        assertTrue(thrown2.getMessage().contains("negatives not allowed -100"));
    }

    @Test
    void whenGivenStringHasMultipleNegativeNumbers_ThrowErrorAndShowThemAll(){
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> stringCalculator.checkLengthAndAdd("-2,-6,-200"));
        assertTrue(thrown.getMessage().contains("negatives not allowed -2 -6 -200"));

        RuntimeException thrown2 = assertThrows(RuntimeException.class, () -> stringCalculator.checkLengthAndAdd("//|\n-99|-22|-100"));
        assertTrue(thrown2.getMessage().contains("negatives not allowed -99 -22 -100"));

    }

    @Test
    void whenAddMethodIsCalled3Times_DetectIt(){
        stringCalculator.checkLengthAndAdd("1,2,3,4,5,6");
        stringCalculator.checkLengthAndAdd("10,20,30,40");
        stringCalculator.checkLengthAndAdd("7,9");
        assertEquals(3, stringCalculator.getCalledCount());
    }

    @Test
    void whenAddMethodIsCalled5Times_DetectIt(){
        stringCalculator.checkLengthAndAdd("1,2,3,4,5,6");
        stringCalculator.checkLengthAndAdd("10,20,30,40");
        stringCalculator.checkLengthAndAdd("7,9");
        stringCalculator.checkLengthAndAdd("2,9");
        stringCalculator.checkLengthAndAdd("5,9");
        assertEquals(5, stringCalculator.getCalledCount());
    }

    @Test
    void whenGivenStringHasNumberBiggerThan1000_IgnoreIt(){
        int result = stringCalculator.checkLengthAndAdd("3,5,1009");
        assertEquals(8, result);

        int result2 = stringCalculator.checkLengthAndAdd("//.\n10.20.2000");
        assertEquals(30, result2);
    }

}
