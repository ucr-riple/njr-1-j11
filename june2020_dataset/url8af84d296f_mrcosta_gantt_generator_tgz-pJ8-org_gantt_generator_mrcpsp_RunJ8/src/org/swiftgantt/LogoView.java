package org.swiftgantt;

import javax.swing.UIManager;

import org.gantt.generator.mrcpsp.Gantt;
import org.swiftgantt.ui.LogoViewUI;

/**
 * The <code>LogoView</code> doesn't do anything except showing the product name "SwiftGantt" and version,
 * if you want to add your logo or something else, you can create your own displaying to replace
 * the default. To implement that, you could create a new swing component that shows what you want,
 * then add this component to the left corner view of the <code>GanttChart</code>. like:<BR>
 * <code>
 * &nbsp;&nbsp;&nbsp;&nbsp;ganttChart.setCorner(JScrollPane.UPPER_LEFT_CORNER, myLogoView);<BR>
 * </code>
 *
 * @author Yuxing Wang
 */
public class LogoView extends BaseView {
	//改为更换LogoViewUI可以尝试一下

	private static final long serialVersionUID = 1L;
	protected LogoViewUI lvUI = null;
	private String text = Gantt.fileName;

	public LogoView(GanttChart ganttChart) {
		super(ganttChart);
		this.ganttChart = ganttChart;
		initialize();
	}

	/**
	 * This method initializes this
	 *
	 */
	private void initialize() {
		lvUI = (LogoViewUI) UIManager.getUI(this);//Get UI by ID "TimeScaleUI".
		super.setUI(lvUI);

//		this.setLayout(null);
//        this.setSize(new Dimension(200, 150));
//        this.setFont(new Font("Colonna MT", Font.PLAIN, 26 ));//this.getFont().getStyle()
//        this.setOpaque(true);
//        this.setHorizontalAlignment(JLabel.CENTER);
//        this.setVerticalAlignment(JLabel.CENTER);
//        Color backColor = this.ganttChart.getConfig().getGanttChartBackColor();

//        java.net.URL imageURL = this.getClass().getResource("Logo.png");
//        URL imageURL = this.getClass().getResource("Logo_2.png");
//        ImageIcon icon = new ImageIcon(imageURL, "The SwiftGantt Logo");
//        this.setIcon(icon);
	}

	/**
	 * The Class ID for <code>UIManager</code>.
	 */
	@Override
	public String getUIClassID() {
		return "LogoViewUI";
	}

	public GanttChart getGanttChart() {
		return ganttChart;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
