package com.creditease.dds.pandora.domain.chart.em;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.RectangleEdge;

/**
 * 图形方向
 * @author haihongjia
 *
 */
public enum ChartPlotEnum {
	
	VERTICAL(1,PlotOrientation.VERTICAL),//水平
	HORIZONTAL(2,PlotOrientation.HORIZONTAL)//垂直

	;
	private int type;
	private PlotOrientation plot ;
	
	
	private ChartPlotEnum(int type,PlotOrientation plot) {
		this.type = type;
		this.plot = plot;
	}

	
	

	 // 普通方法  
   public static PlotOrientation match(int type) {  
       for (ChartPlotEnum c : ChartPlotEnum.values()) {  
           if (c.type==type) {  
               return c.getPlot();  
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
	
	
	
	
	public PlotOrientation getPlot() {
		return plot;
	}
	
	
	
	
	public void setPlot(PlotOrientation plot) {
		this.plot = plot;
	}



	 
}
