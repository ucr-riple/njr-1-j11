package org.washcom.cardgames.war;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author Joe
 */
public class WarHistogram extends Application {
    
    public static final int NBR_OF_GAMES = 250_000;
    public static final int GROUP_SIZE = 50;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("War Histogram");
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Battles per Game");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("% Occurrence");
        BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle(String.format("%,d Games of War", NBR_OF_GAMES));
        bc.setCategoryGap(0.05d);

        WarGameSimulator sim = new WarGameSimulator(NBR_OF_GAMES, false);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(String.format("Empirical Results: \u03BC=%.3f \u03C3=%.3f", sim.getAverageGameLength(), sim.getGameLengthStdDev()));
        
        for (Map.Entry<Integer, Integer> point : generateSimulationData(sim).entrySet()) {
            String groupName = describeGroup(point.getKey());
            double pct = new BigDecimal(point.getValue()).divide(new BigDecimal(NBR_OF_GAMES)).doubleValue();
            series1.getData().add(new XYChart.Data(groupName, pct));
        }
        
        Scene scene = new Scene(bc, 1200, 600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }
    
    private Map<Integer, Integer> generateSimulationData(WarGameSimulator sim) {
        Map<Integer, Integer> data = new TreeMap<>();
        for (Integer length : sim.getGameLengths()) {
            int group = length / GROUP_SIZE;
            Integer count = data.get(group);
            data.put(group, (count == null ? 0 : count) + 1);
        }
        return data;
    }
    
    private static String describeGroup(int groupSize) {
        int product = groupSize * GROUP_SIZE;
        return product + "-" + (GROUP_SIZE + product);
    }
}