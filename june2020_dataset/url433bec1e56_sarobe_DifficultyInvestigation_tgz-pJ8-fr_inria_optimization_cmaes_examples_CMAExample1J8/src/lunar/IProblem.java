package lunar;

import controller.IController;
import controller.ShipController;
import entity.spaceship.Spaceship;

import java.awt.*;
import java.util.List;

public interface IProblem<T> {

    int nDim();
    double fitness(double[] x); // for strategies that minimise
    T getInstance(double[] x);

    void demonstrationInit(List<Spaceship> ships, List<ShipController> conts, double[][] pop);
    void demonstrate(List<Spaceship> ships, List<ShipController> conts, double[][] pop);

    void visualiseExtraInformation(Graphics g);
}

