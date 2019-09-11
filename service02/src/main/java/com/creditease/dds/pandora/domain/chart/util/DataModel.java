package com.creditease.dds.pandora.domain.chart.util;

import java.util.List;
import java.util.Map;

public class DataModel{
	
	private String[] categories;
	
	private Object[] datas;
	
	private List<Serie> seriesLeft;//Y轴-左侧数据
	private List<Serie> seriesRight;//Y轴-右侧数据
	
	private Map<Object,Map<Number,Number>> maps;
	
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public Object[] getDatas() {
		return datas;
	}
	public void setDatas(Object[] datas) {
		this.datas = datas;
	}
	public List<Serie> getSeriesLeft() {
		return seriesLeft;
	}
	public void setSeriesLeft(List<Serie> seriesLeft) {
		this.seriesLeft = seriesLeft;
	}
	public List<Serie> getSeriesRight() {
		return seriesRight;
	}
	public void setSeriesRight(List<Serie> seriesRight) {
		this.seriesRight = seriesRight;
	}
	public Map<Object, Map<Number, Number>> getMaps() {
		return maps;
	}
	public void setMaps(Map<Object, Map<Number, Number>> maps) {
		this.maps = maps;
	}
	
	

	
}
