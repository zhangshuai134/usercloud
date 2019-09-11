package com.creditease.dds.pandora.domain.chart.strategy.chart;

import com.creditease.dds.pandora.domain.chart.em.ChartEnum;
import com.creditease.dds.pandora.domain.chart.em.ChartPositionEnum;
import com.creditease.dds.pandora.domain.chart.strategy.AbstractChart;
import com.creditease.dds.pandora.domain.chart.util.ChartModel;
import com.creditease.dds.pandora.domain.chart.util.ChartUtils;
import com.creditease.dds.pandora.domain.chart.util.DataModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;



/**
 * 饼图、环形图
 * @author haihongjia
 *
 */
public class PieChart extends AbstractChart {


	@Override
	protected Object createDataset(DataModel dataModel) {
		
		DefaultPieDataset dataset = ChartUtils.createPieDataset(dataModel);
		
		return dataset;
	}

	@Override
	protected Object createJfreeChart(AbstractDataset dataset,ChartModel model) {
		
		String tile = model.getTitle();
		
		JFreeChart chart = null;
		 switch (ChartEnum.getChartEnum(model.getType())){
		 	    case PIE:
		 	    	chart = ChartFactory.createPieChart(tile, (PieDataset) dataset,true, true, false);
				    break;
				case RING:
					chart = ChartFactory.createRingChart(tile, (PieDataset) dataset,true, true, false);
	       	        break;
                 default:
					 chart = ChartFactory.createPieChart(tile, (PieDataset) dataset,true, true, false);
					 break;
		 }
		
		return chart;
	}

	@Override
	protected void processChart(DataModel dataModel,JFreeChart chart,ChartModel model) {
		
		RectangleEdge rec = ChartPositionEnum.match(model.getPosition());//图例位置
		ChartUtils.setPieRender(chart,rec);
		
	}
	

}
