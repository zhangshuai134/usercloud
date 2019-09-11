package com.creditease.dds.pandora.domain.chart.em;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.RectangleEdge;

/**
 * 图例位置
 * @author haihongjia
 *
 */
public enum ChartPositionEnum {
	
	RIGHT(1,RectangleEdge.RIGHT),//右
	LEFT(2,RectangleEdge.LEFT),//左
	TOP(3,RectangleEdge.TOP),//上
	BOTTOM(4,RectangleEdge.BOTTOM)//下

	;
	private int type;
	private RectangleEdge position ;
	
	private ChartPositionEnum(int type,RectangleEdge position) {
		this.type = type;
		this.position = position;
	}

	
	

	 // 普通方法  
   public static RectangleEdge match(int type) {  
       for (ChartPositionEnum c : ChartPositionEnum.values()) {  
           if (c.type==type) {  
               return c.getPosition();
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
	
	public RectangleEdge getPosition() {
		return position;
	}
	
	public void setPosition(RectangleEdge position) {
		this.position = position;
	}





	 
}
