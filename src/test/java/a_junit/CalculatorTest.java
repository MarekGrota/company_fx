package a_junit;

import org.junit.jupiter.api.*;

public class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    private static void beforeAll() {

        System.out.println("BEFORE ALL.");
    }

    @AfterAll
    private static void afterAll() {

        System.out.println("AFTER ALL.");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("BEFORE EACH.");
        calculator = new Calculator();
    }

    @AfterEach
    public void afterEach() {
        System.out.println("AFTER EACH.");
    }

    @Test
    //@Disabled
    public void test() {

        // Act
        Integer sum = calculator.add(2,2);

        // Assert
        Assertions.assertEquals(4, sum);
    }

    @Test
    public void test2() {

        // Act
        Integer sum = calculator.add(1,1);

        // Assert
        Assertions.assertEquals(2, sum);
    }
}
