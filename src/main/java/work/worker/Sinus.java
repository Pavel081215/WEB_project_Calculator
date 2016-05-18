package work.worker;


public class Sinus implements ScientificCalculator {
    @Override
    public double Calculation(double variable) {
        return Math.sin(Math.toRadians(variable));
    }
}
