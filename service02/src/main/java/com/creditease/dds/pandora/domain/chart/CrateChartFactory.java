package com.creditease.dds.pandora.domain.chart;


import com.creditease.dds.pandora.domain.chart.em.ChartEnum;
import com.creditease.dds.pandora.domain.chart.strategy.AbstractChart;
import com.creditease.dds.pandora.domain.chart.util.ChartModel;
import com.creditease.dds.pandora.domain.chart.util.DataModel;

public class CrateChartFactory {
	
	public static void crateChart(DataModel dataModel, String filePath, ChartModel chartModel) {
		
		AbstractChart chart=ChartEnum.match(chartModel.getType());
		Boolean bool = chart.buildChart(dataModel,filePath,chartModel);
		System.out.println("图表图片生成结果："+bool);
		
	}
	
}
