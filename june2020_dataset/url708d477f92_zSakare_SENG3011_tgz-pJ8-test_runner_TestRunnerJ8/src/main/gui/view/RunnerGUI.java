package main.gui.view;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import main.gui.controller.Controller;
import main.utils.Strategy;


public class RunnerGUI {

	private Controller controller;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					RunnerGUI window = new RunnerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RunnerGUI() {
		controller = new Controller();
		initialize();
		System.out.println("Welcome to ASX Strategy Evaluator");
	}

	static private String selectedString(ItemSelectable is) {
	    Object selected[] = is.getSelectedObjects();
	    return ((selected.length == 0) ? "null" : (String)selected[0]);
	  } 
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JTextArea txtpnOutput = new JTextArea();
		scrollPane.setViewportView(txtpnOutput);
		txtpnOutput.setEditable(false);
		redirectSystemStreams(txtpnOutput);
		
		JLabel lblChooseStrategy = new JLabel("Choose Strategy:");
		GridBagConstraints gbc_lblChooseStrategy = new GridBagConstraints();
		gbc_lblChooseStrategy.insets = new Insets(0, 0, 5, 5);
		gbc_lblChooseStrategy.gridx = 1;
		gbc_lblChooseStrategy.gridy = 1;
		frame.getContentPane().add(lblChooseStrategy, gbc_lblChooseStrategy);
		
		JButton btnLoadInputFile = new JButton("Load Input File");
		btnLoadInputFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				String fileName = JOptionPane.showInputDialog(null, "Please enter a valid filename. E.g. sircaInput.csv.", "Choose a File", JOptionPane.QUESTION_MESSAGE);
				controller.setOrderbook(fileName);
			}
		});
		
		GridBagConstraints gbc_btnLoadInputFile = new GridBagConstraints();
		gbc_btnLoadInputFile.anchor = GridBagConstraints.SOUTH;
		gbc_btnLoadInputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadInputFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadInputFile.gridx = 0;
		gbc_btnLoadInputFile.gridy = 2;
		frame.getContentPane().add(btnLoadInputFile, gbc_btnLoadInputFile);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(3);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Random", "Momentum", "Mean Reversion"}));
		comboBox.setToolTipText("Choose Strategy");
        ActionListener actionListener = new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent actionEvent) {
                ItemSelectable is = (ItemSelectable)actionEvent.getSource();
                String strategy = selectedString(is);
                controller.setStrategy(strategy);
                String volume = JOptionPane.showInputDialog(null,"Enter a Volume to trade.", "Volume", JOptionPane.QUESTION_MESSAGE);
                controller.setVolume(volume);
                
                if (controller.getStrategy() == Strategy.MOMENTUM || controller.getStrategy() == Strategy.MEAN_REVERSION) {
                	setLookbackPeriod();
                	setThreshold();
                }
            }

			private void setThreshold() {
				try {
					String threshold = JOptionPane.showInputDialog(null, "Enter a trading threshold. Default is 0.0000001.", "Threshold", JOptionPane.QUESTION_MESSAGE);
					controller.setThreshold(threshold);
				} catch (NumberFormatException e) {
					System.err.println("Please enter an appropriate number.");
					setThreshold();
				}
			}

			private void setLookbackPeriod() {
				try {
					String lookbackPeriod = JOptionPane.showInputDialog(null, "Enter a lookback period. Default is 1000.", "Lookback Period", JOptionPane.QUESTION_MESSAGE);
					controller.setLookbackPeriod(lookbackPeriod);
				} catch (NumberFormatException e) {
					System.err.println("Please enter an appropriate number.");
					setLookbackPeriod();
				}
			}
        };   
        comboBox.addActionListener(actionListener);

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 2;
		frame.getContentPane().add(comboBox, gbc_comboBox);
		
		
		
		JButton btnPerformanceReport = new JButton("Performance Report");
		btnPerformanceReport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.evaluate();
				TradeGraphPlotter plot = new TradeGraphPlotter("Algorithmic Trades", 
						"Algorithmic Trade Plot", 
						controller.getEvaluator().getTradePair());
				plot.setVisible(true);
				plot.setBounds(0, 0, 1024, 720);
			}
		});
		GridBagConstraints gbc_btnPerformanceReport = new GridBagConstraints();
		gbc_btnPerformanceReport.anchor = GridBagConstraints.SOUTH;
		gbc_btnPerformanceReport.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPerformanceReport.insets = new Insets(0, 0, 5, 0);
		gbc_btnPerformanceReport.gridx = 2;
		gbc_btnPerformanceReport.gridy = 2;
		frame.getContentPane().add(btnPerformanceReport, gbc_btnPerformanceReport);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    System.exit(0);

			}
		});
		GridBagConstraints gbc_btnQuit = new GridBagConstraints();
		gbc_btnQuit.insets = new Insets(0, 0, 0, 5);
		gbc_btnQuit.anchor = GridBagConstraints.SOUTH;
		gbc_btnQuit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnQuit.gridx = 0;
		gbc_btnQuit.gridy = 3;
		frame.getContentPane().add(btnQuit, gbc_btnQuit);
		
		JButton btnExecuteStrategy = new JButton("Execute Strategy");
		btnExecuteStrategy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.runStrategy();
			}
		});
		GridBagConstraints gbc_btnExecuteStrategy = new GridBagConstraints();
		gbc_btnExecuteStrategy.anchor = GridBagConstraints.SOUTH;
		gbc_btnExecuteStrategy.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExecuteStrategy.insets = new Insets(0, 0, 0, 5);
		gbc_btnExecuteStrategy.gridx = 1;
		gbc_btnExecuteStrategy.gridy = 3;
		frame.getContentPane().add(btnExecuteStrategy, gbc_btnExecuteStrategy);
	}
	
	private void updateTextArea(final String text, final JTextArea textArea) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				textArea.append(text);
			}
		});
	}

	private void redirectSystemStreams(final JTextArea textArea) {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b), textArea);
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len), textArea);
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}
}

