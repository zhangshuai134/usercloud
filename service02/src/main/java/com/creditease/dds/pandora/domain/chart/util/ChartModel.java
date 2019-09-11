package com.creditease.dds.pandora.domain.chart.util;


/**
 * @author haihongjia
 *
 */
public class ChartModel {

	private int imageWidth = 800;//图片宽度
	private int imageHeight = 400;//图片高度
	private int type = 0;//图表类型
	private String title = "";//图表标题
	private String xaxis = "";//x轴标签
	private String yaxisLeft = "";//y轴左侧标签
	private String yaxisRight = "";//y轴右侧标签
	private int chartDirection = 1;//图表方向,默认垂直方向
	private int slope = 3;//图表X轴倾斜度
	private int position = 1;//图例位置
	private int writeType = 1;//写出方式，默认写出到图片
	private String[] colors = {};//颜色数组 ---顺序与数据保持一致
	
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getXaxis() {
		return xaxis;
	}
	public void setXaxis(String xaxis) {
		this.xaxis = xaxis;
	}
	
	public String getYaxisLeft() {
		return yaxisLeft;
	}
	public void setYaxisLeft(String yaxisLeft) {
		this.yaxisLeft = yaxisLeft;
	}
	public String getYaxisRight() {
		return yaxisRight;
	}
	public void setYaxisRight(String yaxisRight) {
		this.yaxisRight = yaxisRight;
	}
	public int getChartDirection() {
		return chartDirection;
	}
	public void setChartDirection(int chartDirection) {
		this.chartDirection = chartDirection;
	}
	
	public int getSlope() {
		return slope;
	}
	public void setSlope(int slope) {
		this.slope = slope;
	}
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getWriteType() {
		return writeType;
	}
	public void setWriteType(int writeType) {
		this.writeType = writeType;
	}
	public String[] getColors() {
		return colors;
	}
	public void setColors(String[] colors) {
		this.colors = colors;
	}
	
	
	

	

	

}
