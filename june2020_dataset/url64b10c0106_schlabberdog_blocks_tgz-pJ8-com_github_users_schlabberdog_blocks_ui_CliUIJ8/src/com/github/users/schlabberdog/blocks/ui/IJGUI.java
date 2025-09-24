package com.github.users.schlabberdog.blocks.ui;

import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.board.BoardSave;
import com.github.users.schlabberdog.blocks.board.moves.IMove;
import com.github.users.schlabberdog.blocks.solver.ISolverDelegate;
import com.github.users.schlabberdog.blocks.solver.Solver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class IJGUI implements ISolverDelegate, Thread.UncaughtExceptionHandler {
    private BoardView boardView;
    private JButton stepButton;
    private JPanel root;
    private JLabel checkCountLabel;
    private JLabel stackLabel;
    private JButton fastForwardButton;
    private JButton nextButton;
    private JLabel numSolutionsLabel;
    private JLabel solImprovLabel;
    private JLabel bestPathLabel;
    private JSpinner pathStopLength;
    private JSpinner stackLimiterSpinner;
	private JLabel worstStackLabel;
	private JCheckBox avoidWorseCheckbox;
	private JLabel timeTakenLabel;

	private final Board board;
    private final Solver solver;
	private Timer timer;

	private final BoardSave initialState;
	private List<IMove> bestSolution = null;

	private Stopwatch stopwatch;
    private JFrame frame;

	private Thread solverThread;
	private boolean isSolverRunning = false;
	private int stopAtCount = 0;

    private IJGUI(Board b,Solver s) {
	    this.board = b;
	    this.solver = s;

	    initialState = board.getSave();

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doStep();
            }
        });
        fastForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				startSolve();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doNext();
            }
        });

        pathStopLength.setModel(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
        stackLimiterSpinner.setModel(new SpinnerNumberModel(0,0, Integer.MAX_VALUE,1));

	    timer = new Timer(50,new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent actionEvent) {
				validateButtons();
		    }
	    });

		stopwatch = new Stopwatch();

        avoidWorseCheckbox.setSelected(solver.shouldAvoidWorseStacks());

    }

    public synchronized void validateButtons() {

        boardView.repaint();

	    checkCountLabel.setText(  String.format("%,d", solver.getCheckCount()));
        stackLabel.setText(       String.format("%,d", solver.getStackDepth()));
        numSolutionsLabel.setText(String.format("%,d", solver.getSolutionCount()));
        solImprovLabel.setText(   String.format("%,d", solver.getSolutionImprovedCount()));
	    worstStackLabel.setText(  String.format("%,d", solver.getWorstStack()));
        bestPathLabel.setText(    String.format("%,d", solver.getBestPathLength()));

	    long timeTaken = stopwatch.getElapsedTime();
	    long millis = timeTaken%1000;
	    timeTaken = (timeTaken - millis) / 1000;
	    long seconds = timeTaken%60;
	    timeTaken = (timeTaken - seconds) / 60;
	    long minutes = timeTaken % 60;
	    timeTaken = (timeTaken - minutes) / 60; //== hours

	    timeTakenLabel.setText(String.format("%02d:%02d:%02d.%03d", timeTaken, minutes, seconds, millis));
    }


    public synchronized void doNext() {
		//zuerst den button wieder deaktivieren
		nextButton.setEnabled(false);
		pathStopLength.setEnabled(false);

		if(isSolverRunning) {
			//ggf. veränderten wert erneut kopieren
			stopAtCount = ((Number)pathStopLength.getValue()).intValue();
			//die uhr weiterlaufen lassen
			stopwatch.start();
			//dann den solver auch weiter laufen lassen
			solverThread.interrupt();
		}
    }

    public synchronized void startSolve() {
        //gui deaktivieren
        fastForwardButton.setEnabled(false);
        stackLimiterSpinner.setEnabled(false);
        avoidWorseCheckbox.setEnabled(false);
		pathStopLength.setEnabled(false);
		//uhr zurücksetzen
		stopwatch.reset();
        //werte kopieren
        solver.setStackDepthLimit(((Number) stackLimiterSpinner.getValue()).intValue());
        solver.setAvoidWorseStacks(avoidWorseCheckbox.isSelected());
		stopAtCount = ((Number)pathStopLength.getValue()).intValue();
        //für den solver benutzen wir einen eigenen Thread
		solverThread = new Thread(new Runnable() {
            @Override
            public void run() {
				solver.solve();
            }
        });
		solverThread.setUncaughtExceptionHandler(this);
		solverThread.start();

    }

    public synchronized void doStep() {

    }

    private void createUIComponents() {
        boardView = new BoardView(board);
    }

    public static IJGUI Create(Board board, Solver solver) {
        IJGUI gui = new IJGUI(board,solver);

        solver.setDelegate(gui);

        JFrame frame = new JFrame("IJGUI");
        frame.setContentPane(gui.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);

        gui.frame = frame;

        return gui;
    }

	@Override
	public synchronized void solverStarted(Solver solver) {
		timer.start();
		stopwatch.start();
		isSolverRunning = true;
	}

	@Override
	public void solutionImproved(Solver solver, int solSize) {
		//System.out.println("Better solution: "+solSize);
		bestSolution = solver.getStepList();

		//jetzt können wir den solver trappen, wenn er halten soll
		if(stopAtCount > 0 && solver.getBestPathLength() <= stopAtCount) {
			//zuerst mal die uhr anhalten
			stopwatch.stop();
			//lösung im GUI thread anzeigen
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					//anzeige der lösung starten
					IJSolutionBrowser.Create(board.copy(),initialState,bestSolution);
					//weiter button aktivieren
					nextButton.setEnabled(true);
					//verändern des halte-limits erlauben
					pathStopLength.setEnabled(true);
					//gui neu zeichnen
					validateButtons();
				}
			});
			//solver anhalten
			try {
				//noinspection InfiniteLoopStatement
				while(true) {
					Thread.sleep(1000); //jaja, wait() ist vieeeel cooler...
				}
			} catch (InterruptedException e) {
				//die exception ist beabsichtigt, damit es weitergeht
			}
		}
	}

	@Override
	public synchronized void solverDone(Solver solver) {
		stopRun();

		//der solver thread ruft das hier auf, GUI aktionen müssen aber im GUI thread passieren
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//einmal müssen wir evtl. von hand noch nacharbeiten
				validateButtons();
				//lösung anzeigen
				if(bestSolution != null) {
					IJSolutionBrowser.Create(board.copy(),initialState,bestSolution);
				}
				else {
					JOptionPane.showMessageDialog(frame,"Mit den gegebenen Einstellungen konnte keine Lösung gefunden werden!","Keine Lösung gefunden",JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	private void stopRun() {
		//jetzt brauchen wir den timer nicht mehr
		timer.stop();
		//uhr anhalten, wir sind am ende
		stopwatch.stop();

		isSolverRunning = false;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		if(thread != solverThread) {
			//Kennen wir uns?
			throwable.printStackTrace(System.err);
			return;
		}
		//auf jeden fall machen wir jetzt nix mehr...
		stopRun();
		//stracktrace in string umwandeln
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		final String stackTrace = sw.toString()
				.replaceAll("&","&amp;")
				.replaceAll("<","&lt;")
				.replaceAll(">","&gt;")
				.replaceAll("\n","<br>")
				.replaceAll("\r","")
				.replaceAll("\t", "  ");
		//dialog anzeigen
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//einmal müssen wir evtl. von hand noch nacharbeiten
				validateButtons();
				//error
				JOptionPane.showMessageDialog(
						frame,
						"<html>Der Solver ist beim Finden einer Lösung abgestürzt.<br><br><pre>"+stackTrace+"</pre></html>",
						"Solver abgestürzt",
						JOptionPane.ERROR_MESSAGE
				);
			}
		});
	}
}
