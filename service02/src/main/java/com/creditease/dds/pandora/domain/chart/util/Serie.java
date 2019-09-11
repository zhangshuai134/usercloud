package com.creditease.dds.pandora.domain.chart.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Serie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;// 名字
	
	private List<Object> data;// 数据值

	public Serie() {

	}

	/**
	 * 
	 * @param name
	 *            名称（线条名称）
	 * @param data
	 *            数据（线条上的所有数据值）
	 */
	public Serie(String name, List<Object> data) {

		this.name = name;
		this.data = data;
	}

	/**
	 * 
	 * @param name
	 *            名称（线条名称）
	 * @param array
	 *            数据（线条上的所有数据值）
	 */
	public Serie(String name, Object[] array) {
		this.name = name;
		if (array != null&&array.length>0) {
			data = new ArrayList<Object>(array.length);
			for (int i = 0; i < array.length; i++) {
				data.add(array[i]);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

}
