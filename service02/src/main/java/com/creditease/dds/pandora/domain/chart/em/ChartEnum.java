package com.creditease.dds.pandora.domain.chart.em;


import com.creditease.dds.pandora.domain.chart.strategy.AbstractChart;
import com.creditease.dds.pandora.domain.chart.strategy.chart.BarChart;
import com.creditease.dds.pandora.domain.chart.strategy.chart.PieChart;
import com.creditease.dds.pandora.domain.chart.strategy.chart.PolarChart;
import com.creditease.dds.pandora.domain.chart.strategy.chart.ScatterChart;

/**
 * 图形种类
 * @author haihongjia
 *
 */
public enum ChartEnum {

	BAR(0,new BarChart()),//chartDirection 1.柱状图---2.条形图
	LINE(1,new BarChart()),//折线图
	STACKEDBAR(2,new BarChart()),//chartDirection 1.堆积柱状图---2.堆积条形图
	WATERFALL(3,new BarChart()),//瀑布图
	DUALAXIS(4,new BarChart()),//双轴图
	AREA(5,new BarChart()),//面积图
	POLAR(6,new PolarChart()),//雷达图

	SCATTER(7,new ScatterChart()),//散点图

	PIE(8,new PieChart()),//饼图
	RING(9,new PieChart()),//环形图
	
	
	;
	
	private int type;
	private AbstractChart chart;
	
	private ChartEnum(int type,AbstractChart chart) {
		this.type = type;
		this.chart = chart;
	}
	
	public static ChartEnum getChartEnum(int type){
            for (ChartEnum item: ChartEnum.values()) {
                if (item.getType()==type) {
                    return item;
                }
            }
        return null;
    }

	public static AbstractChart match(int type){

		for (ChartEnum item: ChartEnum.values()) {
			if (item.getType()==type) {
				return item.getChart();
			}
		}
		return null;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public AbstractChart getChart() {
		return chart;
	}

	public void setChart(AbstractChart chart) {
		this.chart = chart;
	}
	
	

	
	

	
	
	
	
		



		
	
	 
}
