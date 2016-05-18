package work.worker;

public class Logarithm implements ScientificCalculator {

    @Override
    public double Calculation(double variable) {
        return Math.log(variable);
    }
}
