package com.creditease.dds.pandora.domain.chart.strategy.chart;

import com.creditease.dds.pandora.domain.chart.em.ChartPlotEnum;
import com.creditease.dds.pandora.domain.chart.em.ChartPositionEnum;
import com.creditease.dds.pandora.domain.chart.em.ChartSlopeEnum;
import com.creditease.dds.pandora.domain.chart.strategy.AbstractChart;
import com.creditease.dds.pandora.domain.chart.util.ChartModel;
import com.creditease.dds.pandora.domain.chart.util.ChartUtils;
import com.creditease.dds.pandora.domain.chart.util.DataModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.ui.RectangleEdge;



/**
 * 柱状(条形)图、折现图、堆积柱状(条形)图、双轴图、瀑布图
 * @author haihongjia
 */
public class BarChart extends AbstractChart {
	
	
	@Override
	protected Object createDataset(DataModel dataModel) {
		
		DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(dataModel.getCategories(),dataModel.getSeriesLeft());
	 
		return dataset;
	}
	

	@Override
	protected Object createJfreeChart(AbstractDataset dataset,ChartModel model) {
		
		String title = model.getTitle();
		String xaxis = model.getXaxis();
		String yaxis = model.getYaxisLeft();
		int direction = model.getChartDirection();
		PlotOrientation plot =  ChartPlotEnum.match(direction);
		
		JFreeChart chart = null;
		 switch (model.getType()){
         case 0:
        	 chart = ChartFactory.createBarChart(title,xaxis,yaxis,(CategoryDataset) dataset,plot,true,false,false);
             break;
         case 1:
        	 chart = ChartFactory.createLineChart(title,xaxis,yaxis,(CategoryDataset) dataset,plot,true,false,false);
        	 break;
         case 2:
        	 chart = ChartFactory.createStackedBarChart(title,xaxis,yaxis,(CategoryDataset) dataset,plot,true,false,false);
        	 break;
         case 3:
        	 chart = ChartFactory.createWaterfallChart(title,xaxis, yaxis, (CategoryDataset) dataset,plot, true, true, false);
        	 break;
         case 4:
        	 chart = ChartFactory.createBarChart(title,xaxis,yaxis,(CategoryDataset) dataset,plot,true,false,false);
        	 break;
		 case 5:
			 chart = ChartFactory.createAreaChart(title,xaxis,yaxis,(CategoryDataset) dataset,plot,true,false,false);
			 break;
         default:
             break;
		 }
		
		return chart;
		 
	}
	
	
	
	@Override
	protected void processChart(DataModel dataModel,JFreeChart chart,ChartModel model) {
		
		
		CategoryPlot categoryPlot = chart.getCategoryPlot();
		String[] colors = model.getColors();
		int type = model.getType();
		
		switch (type){
        case 4:
        	ChartUtils.setDualaxisRenderer(categoryPlot, colors,dataModel,model,true);//双轴图特殊渲染
        	break;
        default:
        	ChartUtils.setCommonRender(categoryPlot, colors,type,true);
            break;
		 }
		
		// 特殊设置
		RectangleEdge rec = ChartPositionEnum.match(model.getPosition());//图例位置
		CategoryLabelPositions slope = ChartSlopeEnum.match(model.getSlope());//倾斜设置
		ChartUtils.setCommonPros(chart, rec,slope);
		
	
	}
	



}
