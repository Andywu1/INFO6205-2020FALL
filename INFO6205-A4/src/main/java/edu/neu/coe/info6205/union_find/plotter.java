package edu.neu.coe.info6205.union_find;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class plotter extends JFrame {

	    private String graph_title = "";
	    private String x_label = "";
	    private String y_label = "";
	    private ArrayList<XYSeries> seriesList = new ArrayList<XYSeries>();

	    public plotter(String title, String x, String y) {
	        this.graph_title = title;
	        this.x_label = x;
	        this.y_label = y;
	        ;

	    }

	    public void addSeries(XYSeries series){
	        seriesList.add(series);
	    }


	    public void initUI() {

	        XYSeriesCollection dataset = new XYSeriesCollection();
	        Iterator<XYSeries> it = seriesList.iterator();

	        while(it.hasNext()){
	            dataset.addSeries(it.next());
	        }


	        JFreeChart chart = createChart(dataset);

	        ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        chartPanel.setBackground(Color.white);
	        add(chartPanel);

	        pack();
	        setTitle(graph_title);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    }

	    private JFreeChart createChart(XYDataset dataset) {
	        JFreeChart chart = ChartFactory.createXYLineChart(
	                graph_title,
	                x_label,
	                y_label,
	                dataset,
	                PlotOrientation.VERTICAL,
	                true,
	                true,
	                false
	        );

	        XYPlot plot = chart.getXYPlot();

	        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

	        renderer.setSeriesPaint(3, Color.BLUE);

	        plot.setRenderer(renderer);
	        plot.setBackgroundPaint(Color.GRAY);

	        plot.setRangeGridlinesVisible(true);
	        plot.setRangeGridlinePaint(Color.BLACK);

	        plot.setDomainGridlinesVisible(true);
	        plot.setDomainGridlinePaint(Color.DARK_GRAY);

	        chart.getLegend().setFrame(BlockBorder.NONE);


	        chart.setTitle(new TextTitle(graph_title,
	                        new Font("Helvetica", java.awt.Font.BOLD, 18)
	                )
	        );

	        return chart;
	    }
}
