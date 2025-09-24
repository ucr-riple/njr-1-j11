package darwInvest.output;

import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import darwInvest.environment.Settings;
import darwin.population.Individual;

public class Output implements darwin.problemObject.Output {

	@Override
	public JComponent getOutput(Individual individual, Object environment) {
		Settings settings = (Settings) environment;
		
		JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
		
        TimeFrameSelected selected = new TimeFrameSelected(settings);
        
        TimeFrameSelector select = new TimeFrameSelector(individual, settings, 400, selected);
        selected.setSelector(select);
        
        BarChart barChart = new BarChart(settings.ticker, settings.testBeginning + settings.lookback,
        		settings.testBeginning + settings.lookback + settings.testLength);
        selected.setBarChart(barChart);
        
        VolumeChart volChart = new VolumeChart(settings.ticker, settings.testBeginning + settings.lookback,
        		settings.testBeginning + settings.lookback + settings.testLength);
        selected.setVolumeChart(volChart);
        
        Performance performance = new Performance(individual, settings, settings.testBeginning + settings.lookback,
        		settings.testBeginning + settings.lookback + settings.testLength);
        selected.setPerformance(performance);
        
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        topPanel.add(select);
        topPanel.add(barChart);
        topPanel.add(volChart);
        
        panel.add(topPanel);
        panel.add(performance);
        
        return panel;
	}
	
	
}
