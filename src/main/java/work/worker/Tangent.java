package work.worker;

public class Tangent implements ScientificCalculator {
    @Override
    public double Calculation(double variable) {
        return Math.tan(Math.toRadians(variable));
    }
}
