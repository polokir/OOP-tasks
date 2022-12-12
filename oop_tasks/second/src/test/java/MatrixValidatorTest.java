import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixValidatorTest {
    private static final MatrixValidator validator = new MatrixValidator();

    @Test
    public void isValidMatrix(){
        //given
        List<Double> validA = new ArrayList<>(Arrays.asList(1.0, 3.0, 1.0, 1.0));
        List<Double> validC = new ArrayList<>(Arrays.asList(2.0, 2.0, 4.0, 2.0, 3.0));
        List<Double> validB = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
        List<Double> validF = new ArrayList<>(Arrays.asList(1.0, 2.0, 1.0, 1.0, 1.0));
        //when
        TridiagonalMatrix validMatrix = new TridiagonalMatrix(validA, validC, validB, validF);
        //then
        assertTrue(validator.isTridiagonal(validMatrix));
        //given
        List<Double> notValidB = new ArrayList<>(Arrays.asList(10.0, 10.0, 10.0, 10.0));
        //when
        TridiagonalMatrix notValidMatrix = new TridiagonalMatrix(validA, validC, notValidB, validF);
        //then
        assertFalse(validator.isTridiagonal(notValidMatrix));
    }
}