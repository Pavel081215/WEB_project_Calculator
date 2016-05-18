package work.worker;

public class Cosinus implements ScientificCalculator {
    @Override
    public double Calculation(double variable) {
        return Math.cos(Math.toRadians(variable));
    }
}
