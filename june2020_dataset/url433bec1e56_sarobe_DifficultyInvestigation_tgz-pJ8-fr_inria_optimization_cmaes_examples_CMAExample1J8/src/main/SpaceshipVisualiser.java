package main;

import common.Params;
import lunar.IProblem;
import lunar.LunarProblem;
import main.MainView;
import entity.spaceship.Spaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * Created by Samuel Roberts, 2012
 */
public class SpaceshipVisualiser extends JComponent {

    private List<Spaceship> ships;
    private IProblem problem;
    private Font statFont = new Font("sans serif", Font.BOLD, 16);
    private int ticks;
    private boolean demonstrating = false;

    public SpaceshipVisualiser() {
    }

    public void startDemo(List<Spaceship> ships, IProblem problem) {
        this.ships = ships;
        this.problem = problem;
        ticks = 0;
        demonstrating = true;
    }

    public void stopDemo() {
        demonstrating = false;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Params.worldWidth, Params.worldHeight);

        if(demonstrating) {
            problem.visualiseExtraInformation(g);

            synchronized(MainView.class) {
                for(Spaceship ship : ships) {
                    ship.draw(g2d);
                }
            }
            g2d.setColor(Color.WHITE);
//            g2d.setFont(statFont);
//            g2d.drawString("Steps: " + ticks, 10, 20);
        }
    }

    public void tick() {
        ticks++;
    }

    public Dimension getPreferredSize() {
        return new Dimension(Params.worldWidth, Params.worldHeight);
    }
}
