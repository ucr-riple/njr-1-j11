package main;

import common.Params;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    public static MainView main;

    SliderPanel numEvalsSlider;
    SliderPanel popSizeSlider;
    SliderPanel valueRangeSlider;
    SliderPanel uprightAngularToleranceSlider;
    SliderPanel lunarGravitySlider;
    SliderPanel frictionSlider;
    SliderPanel randomSeedSlider;
    SliderPanel numLandingPadsSlider;
    SliderPanel landingPadSizeSlider;
    SliderPanel survivableVelocitySlider;
    SliderPanel startingFuelSlider;
    SliderPanel stepsSlider;
    SliderPanel thrustSlider;
    SliderPanel proximitySlider;
    SliderPanel velocitySlider;
    SliderPanel fuelSlider;
    SliderPanel angleSlider;

    JButton toggleRun;
    JButton toggleDemo;

    public ControlPanel(MainView mainParam) {
        main = mainParam;
        setLayout(new BorderLayout());

        JPanel slidersPanel = new JPanel();
        slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.PAGE_AXIS));

        numEvalsSlider = new SliderPanel("No. of Evals", ParamEnums.NUM_EVALS, Params.numEvalsMin, Params.numEvalsMax, Params.numEvals);
        popSizeSlider = new SliderPanel("Population Size", ParamEnums.POP_SIZE, Params.popSizeMin, Params.popSizeMax, Params.popSize);
        valueRangeSlider = new SliderPanel("Size of Solution Space", ParamEnums.VALUE_RANGE, (int)Params.valueRangeMin, (int)Params.valueRangeMax, (int)Params.valueRange);
        uprightAngularToleranceSlider = new SliderPanel("Landing Angle Tolerance", ParamEnums.ANGLE_TOL, (int)(Params.uprightAngularToleranceMin*100), (int)(Params.uprightAngularToleranceMax*100), (int)(Params.uprightAngularTolerance*100) );
        lunarGravitySlider = new SliderPanel("Lunar Gravity", ParamEnums.GRAVITY, (int)Params.lunarGravityMin, (int)Params.lunarGravityMax, (int)Params.lunarGravity);
        frictionSlider = new SliderPanel("Friction", ParamEnums.FRICTION, (int)(Params.frictionMin*100), (int)(Params.frictionMax*100), (int)(Params.friction*100) );
        randomSeedSlider = new SliderPanel("Random Seed (Landscape)", ParamEnums.RANDOM_SEED, Params.randomSeedMin, Params.randomSeedMax, Params.randomSeed);
        numLandingPadsSlider = new SliderPanel("No. of Landing Pads", ParamEnums.NUM_PADS, Params.numLandingPadsMin, Params.numLandingPadsMax, Params.numLandingPads);
        landingPadSizeSlider = new SliderPanel("Size of Landing Pads", ParamEnums.PAD_SIZE, Params.landingPadSizeMin, Params.landingPadSizeMax, Params.landingPadSize);
        survivableVelocitySlider = new SliderPanel("Velocity Survival Threshold", ParamEnums.SURVIVE_VEL, (int)Params.survivableVelocityMin, (int)Params.survivableVelocityMax, (int)Params.survivableVelocity);
        startingFuelSlider = new SliderPanel("Starting Fuel", ParamEnums.FUEL, (int)Params.startingFuelMin, (int)Params.startingFuelMax, (int)Params.startingFuel);
        stepsSlider = new SliderPanel("Maximum Timesteps", ParamEnums.STEPS, Params.stepsMin, Params.stepsMax, Params.steps);
        thrustSlider = new SliderPanel("Maximum Thrust", ParamEnums.THRUST, (int)Params.thrustLimitMin, (int)Params.thrustLimitMax, (int)Params.thrustLimit);
        proximitySlider = new SliderPanel("Proximity Weight", ParamEnums.W_PROXIMITY, (int)(Params.weightMin*100), (int)(Params.weightMax*100), (int)(Params.proximityWeight*100) );
        velocitySlider = new SliderPanel("Velocity Weight", ParamEnums.W_VELOCITY, (int)(Params.weightMin*100), (int)(Params.weightMax*100), (int)(Params.velocityWeight*100) );
        fuelSlider = new SliderPanel("Fuel Weight", ParamEnums.W_FUEL, (int)(Params.weightMin*100), (int)(Params.weightMax*100), (int)(Params.fuelWeight*100) );
        angleSlider = new SliderPanel("Landing Angle Weight", ParamEnums.W_ANGLE, (int)(Params.weightMin*100), (int)(Params.weightMax*100), (int)(Params.angleWeight*100) );
        
        slidersPanel.add(numEvalsSlider);
        slidersPanel.add(popSizeSlider);
        slidersPanel.add(valueRangeSlider);
//        slidersPanel.add(uprightAngularToleranceSlider);
        slidersPanel.add(lunarGravitySlider);
        slidersPanel.add(frictionSlider);
        slidersPanel.add(randomSeedSlider);
        slidersPanel.add(numLandingPadsSlider);
        slidersPanel.add(landingPadSizeSlider);
        slidersPanel.add(survivableVelocitySlider);
        slidersPanel.add(startingFuelSlider);
        slidersPanel.add(stepsSlider);
        slidersPanel.add(thrustSlider);
        slidersPanel.add(proximitySlider);
        slidersPanel.add(velocitySlider);
        slidersPanel.add(fuelSlider);
        slidersPanel.add(angleSlider);

        add(slidersPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        toggleDemo = new JButton("Show Demo");
        toggleDemo.addActionListener(new ButtonListener(main, ButtonEnums.DEMO));
        toggleRun = new JButton("Start");
        toggleRun.addActionListener(new ButtonListener(main, ButtonEnums.RUN));

        toggleDemo.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleRun.setAlignmentX(Component.CENTER_ALIGNMENT);

//        JComboBox strategyBox = new JComboBox(Params.strategies);
//        strategyBox.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                JComboBox cb = (JComboBox)e.getSource();
//                String selection = (String)cb.getSelectedItem();
//                main.selectedStrategy(selection);
//            }
//        });
//        strategyBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(toggleRun);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(toggleDemo);
        buttonPanel.add(Box.createVerticalGlue());
//        buttonPanel.add(strategyBox);
        buttonPanel.add(Box.createVerticalStrut(Params.screenHeight - 300));

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(buttonPanel, BorderLayout.EAST);

        allowChanges();
    }

    public void allowChanges() {
        numEvalsSlider.setEnabled(true);
        popSizeSlider.setEnabled(true);
        valueRangeSlider.setEnabled(true);
        uprightAngularToleranceSlider.setEnabled(true);
        lunarGravitySlider.setEnabled(true);
        frictionSlider.setEnabled(true);
        randomSeedSlider.setEnabled(true);
        numLandingPadsSlider.setEnabled(true);
        landingPadSizeSlider.setEnabled(true);
        survivableVelocitySlider.setEnabled(true);
        startingFuelSlider.setEnabled(true);
        stepsSlider.setEnabled(true);
        velocitySlider.setEnabled(true);
        proximitySlider.setEnabled(true);
        fuelSlider.setEnabled(true);
        angleSlider.setEnabled(true);
        thrustSlider.setEnabled(true);
        numEvalsSlider.slider.setEnabled(true);
        popSizeSlider.slider.setEnabled(true);
        valueRangeSlider.slider.setEnabled(true);
        uprightAngularToleranceSlider.slider.setEnabled(true);
        lunarGravitySlider.slider.setEnabled(true);
        frictionSlider.slider.setEnabled(true);
        randomSeedSlider.slider.setEnabled(true);
        numLandingPadsSlider.slider.setEnabled(true);
        landingPadSizeSlider.slider.setEnabled(true);
        survivableVelocitySlider.slider.setEnabled(true);
        startingFuelSlider.slider.setEnabled(true);
        stepsSlider.slider.setEnabled(true);
        thrustSlider.slider.setEnabled(true);
        velocitySlider.slider.setEnabled(true);
        proximitySlider.slider.setEnabled(true);
        fuelSlider.slider.setEnabled(true);
        angleSlider.slider.setEnabled(true);

        toggleDemo.setEnabled(false);
        toggleDemo.setText("Show Demo");
    }

    public void blockChanges() {
        numEvalsSlider.setEnabled(false);
        popSizeSlider.setEnabled(false);
        valueRangeSlider.setEnabled(false);
        uprightAngularToleranceSlider.setEnabled(false);
        lunarGravitySlider.setEnabled(false);
        frictionSlider.setEnabled(false);
        randomSeedSlider.setEnabled(false);
        numLandingPadsSlider.setEnabled(false);
        landingPadSizeSlider.setEnabled(false);
        survivableVelocitySlider.setEnabled(false);
        startingFuelSlider.setEnabled(false);
        stepsSlider.setEnabled(false);
        velocitySlider.setEnabled(false);
        proximitySlider.setEnabled(false);
        fuelSlider.setEnabled(false);
        angleSlider.setEnabled(false);
        thrustSlider.setEnabled(false);
        numEvalsSlider.slider.setEnabled(false);
        popSizeSlider.slider.setEnabled(false);
        valueRangeSlider.slider.setEnabled(false);
        uprightAngularToleranceSlider.slider.setEnabled(false);
        lunarGravitySlider.slider.setEnabled(false);
        frictionSlider.slider.setEnabled(false);
        randomSeedSlider.slider.setEnabled(false);
        numLandingPadsSlider.slider.setEnabled(false);
        landingPadSizeSlider.slider.setEnabled(false);
        survivableVelocitySlider.slider.setEnabled(false);
        startingFuelSlider.slider.setEnabled(false);
        stepsSlider.slider.setEnabled(false);
        thrustSlider.slider.setEnabled(false);
        velocitySlider.slider.setEnabled(false);
        proximitySlider.slider.setEnabled(false);
        fuelSlider.slider.setEnabled(false);
        angleSlider.slider.setEnabled(false);

        toggleDemo.setEnabled(true);
        toggleDemo.setText("Show Demo");
    }

    public void updateValues() {
        numEvalsSlider.slider.setValue(Params.numEvals);
        popSizeSlider.slider.setValue(Params.popSize);
        valueRangeSlider.slider.setValue((int)Params.valueRange);
        uprightAngularToleranceSlider.slider.setValue((int)(Params.uprightAngularTolerance*100) );
        lunarGravitySlider.slider.setValue((int)Params.lunarGravity);
        frictionSlider.slider.setValue((int)(Params.friction*100) );
        randomSeedSlider.slider.setValue(Params.randomSeed);
        numLandingPadsSlider.slider.setValue(Params.numLandingPads);
        landingPadSizeSlider.slider.setValue(Params.landingPadSize);
        survivableVelocitySlider.slider.setValue((int)Params.survivableVelocity);
        startingFuelSlider.slider.setValue((int)Params.startingFuel);
        stepsSlider.slider.setValue(Params.steps);
        thrustSlider.slider.setValue((int)Params.thrustLimit);
        proximitySlider.slider.setValue((int)(Params.proximityWeight*100) );
        velocitySlider.slider.setValue((int)(Params.velocityWeight*100) );
        fuelSlider.slider.setValue((int)(Params.fuelWeight*100) );
        angleSlider.slider.setValue((int)(Params.angleWeight*100) );
    }

    public Dimension getPreferredSize() {
        return new Dimension(410, Params.screenHeight);
    }
}

class ButtonListener implements ActionListener {

    MainView main;
    ButtonEnums type;

    public ButtonListener(MainView main, ButtonEnums type) {
        this.main = main;
        this.type = type;
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        switch(type) {
            case DEMO:
                if(main.running) {
                    button.setText("Restart Demo");
                    main.startDemo();
                }
                break;
            case RUN:
                if(main.running) {
                    button.setText("Start");
                    main.stopRun();
                } else {
                    button.setText("Stop");
                    main.startRun();
                }
                break;
        }
    }
}


class SliderPanel extends JPanel implements ChangeListener {

    String name;
    ParamEnums type;
    JSlider slider;
    JLabel label;

    public SliderPanel(String name, ParamEnums type, int min, int max, int current) {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder(name));
        this.name = name;
        this.type = type;
        slider = new JSlider(JSlider.HORIZONTAL, min, max, current);
        if(type == ParamEnums.ANGLE_TOL || type == ParamEnums.FRICTION
                || type == ParamEnums.W_PROXIMITY || type == ParamEnums.W_VELOCITY || type == ParamEnums.W_FUEL || type == ParamEnums.W_ANGLE) {
            double realValue = (double)slider.getValue()/100;
            label = new JLabel(String.format("%.2f", realValue));
        } else {
            label = new JLabel(""+slider.getValue());
        }
        slider.addChangeListener(this);
        add(slider);
        add(label);
    }

    public Dimension getPreferredSize() {
        return new Dimension(300, 50);
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
//        if (!source.getValueIsAdjusting()) {
            int value = source.getValue();
            label.setText(value + "");
            // switch based on the type of THIS SLIDER PANEL
            // not the type of the slider detected (which is still from this slider panel, but anyway)
            double realValue = 0;
            switch(type) {
                case NUM_EVALS:
                    Params.numEvals = value;
                    break;
                case POP_SIZE:
                    Params.popSize = value;
                    break;
                case VALUE_RANGE:
                    Params.valueRange = value;
                    break;
                case ANGLE_TOL:
                    realValue = (double)slider.getValue()/100;
                    label.setText(String.format("%.2f", realValue));
                    Params.uprightAngularTolerance = realValue;
                    break;
                case GRAVITY:
                    Params.lunarGravity = value;
                    break;
                case FRICTION:
                    realValue = (double)slider.getValue()/100;
                    label.setText(String.format("%.2f", realValue));
                    Params.friction = realValue;
                    break;
                case RANDOM_SEED:
                    Params.randomSeed = value;
                    break;
                case NUM_PADS:
                    Params.numLandingPads = value;
                    break;
                case PAD_SIZE:
                    Params.landingPadSize = value;
                    break;
                case SURVIVE_VEL:
                    Params.survivableVelocity = value;
                    break;
                case FUEL:
                    Params.startingFuel = value;
                    break;
                case STEPS:
                    Params.steps = value;
                    break;
                case THRUST:
                    Params.thrustLimit = value;
                    break;
                case W_PROXIMITY:
                    realValue = (double)slider.getValue()/100;
                    label.setText(String.format("%.2f", realValue));
                    Params.proximityWeight = realValue;
                    break;
                case W_VELOCITY:
                    realValue = (double)slider.getValue()/100;
                    label.setText(String.format("%.2f", realValue));
                    Params.velocityWeight = realValue;
                    break;
                case W_FUEL:
                    realValue = (double)slider.getValue()/100;
                    label.setText(String.format("%.2f", realValue));
                    Params.fuelWeight = realValue;
                    break;
                case W_ANGLE:
                    realValue = (double)slider.getValue()/100;
                    label.setText(String.format("%.2f", realValue));
                    Params.angleWeight = realValue;
                    break;
                default:
                    System.out.println("Error in slider state change!");
            }
        }
//    }
}