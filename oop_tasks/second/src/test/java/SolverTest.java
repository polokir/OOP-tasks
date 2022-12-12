import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolverTest {
    private static final MatrixValidator matrixValidator = new MatrixValidator();

    @Test
    public void solve(){
        //given
        Solver solver = new Solver(matrixValidator);
        List<Double> a = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
        List<Double> c = new ArrayList<>(Arrays.asList(2.0, 2.0, 2.0, 2.0, 2.0));
        List<Double> b = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
        List<Double> f = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0));
        //when
        TridiagonalMatrix matrix = new TridiagonalMatrix(a, c, b, f);
        //then
        assertTrue(matrixValidator.isTridiagonal(matrix));

        //given
        List<Double> expectedResult = new ArrayList<>(Arrays.asList(0.5, 0.0, 0.5, 0.0, 0.5));
        //when
        List<Double> result = solver.solve(matrix);
        //then
        assertEquals(expectedResult, result);
    }
}