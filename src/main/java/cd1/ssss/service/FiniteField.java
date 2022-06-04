package cd1.ssss.service;

/**
 * @author Jordan
 */
public interface FiniteField {
    int add(int a, int b);
    int minus(int a, int b);
    int multiply(int a, int b);
    int divide(int a, int b);
    int randElement(boolean excludeZero);
}
