package by.epam.lab.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import by.epam.lab.Passenger;

public class FloorView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int SIZE_Y = 40;
	private final static int MARGIN_Y = 5;
	private final static int MARGIN_X = 5;
	private static int numPassengersView = 5;
	private final static int MAX_NUM_PASSENGERS_VIEW = 15;

	public static void setNumPassengersView(int capacity) {
		numPassengersView = capacity < MAX_NUM_PASSENGERS_VIEW ? capacity
				: MAX_NUM_PASSENGERS_VIEW;

	}

	private class PassengerContainerView extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final String SIZE = "Size = ";
		private Passenger[] list;

		public PassengerContainerView(Passenger[] list) {
			super();
			this.list = list;
			this.setMaximumSize(new Dimension(Integer.MAX_VALUE, SIZE_Y - 1));
			this.setMinimumSize(new Dimension(SIZE_Y - MARGIN_X, SIZE_Y
					- MARGIN_Y));
			this.setPreferredSize(getMinimumSize());
			if (list != null)
				this.setToolTipText(SIZE + list.length);
		}

		public synchronized void updateContainer(Passenger[] list) {
			this.list = list;
			if (list != null)
				this.setToolTipText(SIZE + list.length);
		}

		@Override
		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLUE);
			drawContainer(g2);

			int x = 2;
			synchronized (this) {

				if (list != null) // TODO
					for (Passenger passenger : list) {

						x += drawPassenger(g2, passenger, x);
						if (getSize().getWidth() < x) {
							break;
						}
						x += MARGIN_X;

					}
			}

		}

		public void drawContainer(Graphics2D g2) {
			double leftX = getSize().getHeight();
			double rightX = getSize().getWidth();
			g2.setColor(Color.GRAY);
			g2.draw(new Rectangle2D.Double(0, 0, rightX - 1, leftX - 1));
		}

		public int drawPassenger(Graphics2D g2, Passenger passenger, int x) {
			String ID = String.valueOf(passenger.getId());

			int width = (int) Math.round(g2.getFontMetrics()
					.getStringBounds(ID, g2).getWidth());
			int heigth = (int) Math.round(g2.getFontMetrics()
					.getStringBounds(ID, g2).getHeight());
			g2.setColor(Color.RED);
			g2.drawString(ID, x + MARGIN_X, SIZE_Y - 3 * MARGIN_Y);
			g2.draw(new Rectangle2D.Double(x, SIZE_Y - 3 * MARGIN_Y - heigth,
					width + 2 * MARGIN_X, heigth + 1 * MARGIN_Y));

			return width + 2 * MARGIN_X;

		}

	}

	private class ElevatorView extends PassengerContainerView {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int SIZE_X = 35;

		public ElevatorView(Passenger[] list) {
			super(list);
			this.setMaximumSize(new Dimension(SIZE_X * numPassengersView,
					SIZE_Y));
			this.setMinimumSize(new Dimension(SIZE_X, SIZE_Y));
			this.setPreferredSize(getMaximumSize());
			this.setSize(getMinimumSize());

		}

		public void drawContainer(Graphics2D g2) {
			if (super.list != null) {
				double leftX = getSize().getHeight();
				double rightX = getSize().getWidth();
				g2.setColor(Color.GREEN);
				g2.draw(new Rectangle2D.Double(0, 0, rightX - 1, leftX - 1));
			}

		}
	}

	private PassengerContainerView dispath;
	private PassengerContainerView arrival;
	private PassengerContainerView elevator;

	public FloorView(Passenger[] dispathStory, Passenger[] arrivalStory) {
		super();

		setBackground(Color.white);
		this.setMaximumSize(new Dimension(Integer.MAX_VALUE, SIZE_Y));

		dispath = new PassengerContainerView(dispathStory);
		arrival = new PassengerContainerView(arrivalStory);
		elevator = new ElevatorView(null);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(arrival);
		this.add(elevator);
		this.add(dispath);
		this.setVisible(true);
	}

	public void update(Passenger[] dispathStory, Passenger[] arrivalStory) {
		dispath.updateContainer(dispathStory);
		arrival.updateContainer(arrivalStory);
		elevator.updateContainer(null);
	}

	public void setElevatorPassengers(Passenger[] elevatorStory) {
		elevator.updateContainer(elevatorStory);
	}
}
