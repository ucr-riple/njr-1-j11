package darwInvest.output;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import darwInvest.environment.Settings;

public class TimeFrameSelected implements MouseListener {

	private TimeFrameSelector selector;
	private BarChart barChart;
	private VolumeChart volChart;
	private Performance performance;
	private Settings settings;
	
	public TimeFrameSelected(Settings settings) {
		this.settings = settings;
	}
	
	/**
	 * @param selector the selector
	 */
	public void setSelector(TimeFrameSelector selector) {
		this.selector = selector;
	}
	
	/**
	 * @param barChart the bar chart
	 */
	public void setBarChart(BarChart barChart) {
		this.barChart = barChart;
	}
	
	/**
	 * @param volChart the volume char
	 */
	public void setVolumeChart(VolumeChart volChart) {
		this.volChart = volChart;
	}
	
	/**
	 * @param performance the performance object
	 */
	public void setPerformance(Performance performance) {
		this.performance = performance;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		double prop = selector.getXCoord(x);
		long time = selector.proportionToTime(prop);
		time = Math.max(time, 0);
		time = Math.min(time, settings.ticker.getLastTime() - settings.testLength);
		
		if(selector != null)
			selector.setActiveTime(time);
		
		if(barChart != null) {
			barChart.setRange(time, time+settings.testLength);
			barChart.repaint();
		}
		
		if(volChart != null) {
			volChart.setRange(time, time+settings.testLength);
			volChart.repaint();
		}
		
		if(performance != null) {
			performance.setRange(time, time+settings.testLength);
			performance.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
