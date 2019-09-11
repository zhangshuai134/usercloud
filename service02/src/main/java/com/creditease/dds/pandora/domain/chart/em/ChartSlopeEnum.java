package com.creditease.dds.pandora.domain.chart.em;

import org.jfree.chart.axis.CategoryLabelPositions;

/**
 * 图表X轴倾斜度
 * @author haihongjia
 *
 */
public enum ChartSlopeEnum {
	
	STANDARD(0,CategoryLabelPositions.STANDARD),
	DOWN45(1,CategoryLabelPositions.DOWN_45),
	DOWN90(2,CategoryLabelPositions.DOWN_90),
    UP45(3,CategoryLabelPositions.UP_45),
    UP90(4,CategoryLabelPositions.UP_90)
    
	;
	private int type;
	private CategoryLabelPositions pos;
	
	private ChartSlopeEnum(int type,CategoryLabelPositions pos) {
		this.type = type;
		this.pos = pos;
	}

	
	
	 // 普通方法  
    public static CategoryLabelPositions match(int type) {  
        for (ChartSlopeEnum c : ChartSlopeEnum.values()) {  
            if (c.type==type) {  
                return c.getPos();  
            }  
        }  
        return null;  
    }


    public static ChartSlopeEnum getChartSlopeEnumByDegree(int degree){
		int x=degree%180;
		if(x==0){
			return ChartSlopeEnum.STANDARD;
		}else if(x<90){
			return ChartSlopeEnum.UP45;
		}else if(x==90){
			return ChartSlopeEnum.UP90;
		}else if(x>90){
			return ChartSlopeEnum.DOWN45;
		}
		return ChartSlopeEnum.STANDARD;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public CategoryLabelPositions getPos() {
		return pos;
	}



	public void setPos(CategoryLabelPositions pos) {
		this.pos = pos;
	}



	
	
		



		
	
	 
}
