package com.creditease.dds.pandora.domain.chart.strategy.chart;

import java.awt.Color;

import com.creditease.dds.pandora.domain.chart.em.ChartPositionEnum;
import com.creditease.dds.pandora.domain.chart.strategy.AbstractChart;
import com.creditease.dds.pandora.domain.chart.util.ChartModel;
import com.creditease.dds.pandora.domain.chart.util.ChartUtils;
import com.creditease.dds.pandora.domain.chart.util.DataModel;
import com.creditease.dds.pandora.domain.chart.util.MySpiderPlot;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.ui.RectangleEdge;



/**
 * 雷达图
 * @author haihongjia
 */
public class PolarChart extends AbstractChart {

	@Override
	protected Object createDataset(DataModel dataModel) {
		
		DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(dataModel.getCategories(),dataModel.getSeriesLeft());
		
		return dataset;
	}

	@Override
	protected Object createJfreeChart(AbstractDataset dataset, ChartModel model) {
		
		String title = model.getTitle();
		
		MySpiderPlot spiderPlot = new MySpiderPlot((CategoryDataset)dataset);
        JFreeChart chart = new JFreeChart(title, TextTitle.DEFAULT_FONT,spiderPlot, false);
        LegendTitle legendtitle = new LegendTitle(spiderPlot);
        chart.addSubtitle(legendtitle);
        
		return chart;
	}

	@Override
	protected void processChart(DataModel dataModel,JFreeChart chart, ChartModel model) {
		
		String[] colors = model.getColors();
		for(int i=0;i<colors.length;i++) {
				((MySpiderPlot) chart.getPlot()).setSeriesPaint(i,Color.decode(colors[i]));
		}
		RectangleEdge rec = ChartPositionEnum.match(model.getPosition());//图例位置
		chart.getLegend().setPosition(rec);//设置图例位置
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));//设置图例无边框，默认黑色边框
		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderVisible(false);
		chart.getPlot().setOutlinePaint(Color.WHITE);
	}
	
}
