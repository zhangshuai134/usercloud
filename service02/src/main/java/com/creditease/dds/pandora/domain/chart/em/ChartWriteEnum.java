package com.creditease.dds.pandora.domain.chart.em;

/**
 * 图形方向
 * @author haihongjia
 *
 */
public enum ChartWriteEnum {
	
	PICTURE(1,"写入到图片"),
    EXCEL(2,"写入到Excel")

	;
	private int type;
	private String name;
	
	private ChartWriteEnum(int type,String name) {
		this.type = type;
		this.name = name;
	}

	
	
	 // 普通方法  
    public static String getName(String name) {  
        for (ChartWriteEnum c : ChartWriteEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.name;  
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



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	} 

	
	
	
		



		
	
	 
}
