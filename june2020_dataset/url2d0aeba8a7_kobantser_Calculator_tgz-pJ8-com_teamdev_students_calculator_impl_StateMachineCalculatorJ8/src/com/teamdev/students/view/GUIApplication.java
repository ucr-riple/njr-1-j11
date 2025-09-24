package com.teamdev.students.view;

import com.teamdev.students.calculator.EvaluationException;
import com.teamdev.students.calculator.MathExpressionCalculator;
import com.teamdev.students.calculator.impl.StateMachineCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class GUIApplication {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 100;

    private final JLabel expressionLabel;
    private final JLabel resultLabel;

    private final JTextArea mathExpression;
    private final JTextArea result;
    private final JButton calculateButton;
    private final JButton cancelButton;

    private final JPanel content;
    private final LayoutManager layout;
    private final JFrame frame;

    private final MathExpressionCalculator calculator;

    public GUIApplication() {
        calculator = new StateMachineCalculator();

        mathExpression = new JTextArea();

        result = new JTextArea("0");
        result.setEditable(false);

        expressionLabel = new JLabel("Input mathematical expression:");
        expressionLabel.setHorizontalAlignment(CENTER);

        resultLabel = new JLabel("Result of the expression:");
        resultLabel.setHorizontalAlignment(CENTER);

        calculateButton = new JButton("calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = mathExpression.getText();
                try {
                    result.setText(calculator.evaluate(expression).toString());
                } catch (EvaluationException exception) {
                    result.setText(exception.getMessage());
                    mathExpression.setCaretPosition(exception.getErrorIndex());
                    mathExpression.requestFocusInWindow();
                } catch(Exception exception) {
                    result.setText(exception.getMessage());
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mathExpression.setText("");
                result.setText("0");
            }
        });
        content = new JPanel();
        layout = new GridLayout(3, 2);
        content.setLayout(layout);

        content.add(expressionLabel);
        content.add(mathExpression);
        content.add(resultLabel);
        content.add(result);
        content.add(calculateButton);
        content.add(cancelButton);

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(content);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUIApplication();
    }
}
