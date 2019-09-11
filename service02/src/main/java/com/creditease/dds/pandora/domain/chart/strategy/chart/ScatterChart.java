package com.creditease.dds.pandora.domain.chart.strategy.chart;

import com.creditease.dds.pandora.domain.chart.em.ChartPlotEnum;
import com.creditease.dds.pandora.domain.chart.em.ChartPositionEnum;
import com.creditease.dds.pandora.domain.chart.strategy.AbstractChart;
import com.creditease.dds.pandora.domain.chart.util.ChartModel;
import com.creditease.dds.pandora.domain.chart.util.ChartUtils;
import com.creditease.dds.pandora.domain.chart.util.DataModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;



/**
 * 散点图
 * @author haihongjia
 */
public class ScatterChart extends AbstractChart {

	@Override
	protected Object createDataset(DataModel dataModel) {
		
		XYSeriesCollection dataset = ChartUtils.createScatterDataset(dataModel);
		
		return dataset;

	}

	@Override
	protected Object createJfreeChart(AbstractDataset dataset, ChartModel model) {
		
		String title = model.getTitle();
		String xaxis = model.getXaxis();
		String yaxis = model.getYaxisLeft();
		
		int direction = model.getChartDirection();
		PlotOrientation plot =  ChartPlotEnum.match(direction); // 图表方向
		
		JFreeChart chart = ChartFactory.createScatterPlot(title,xaxis, yaxis, (XYDataset) dataset,plot, true, true, false);
		 
		return chart;
	}

	@Override
	protected void processChart(DataModel dataModel,JFreeChart chart, ChartModel model) {
			
			XYPlot plot = chart.getXYPlot();
			String[] colors = model.getColors();
			RectangleEdge rec = ChartPositionEnum.match(model.getPosition());//图例位置
			ChartUtils.setCommonPros(chart,rec);
			ChartUtils.setScapoRenderer(plot,colors,true);
		
	}


}
