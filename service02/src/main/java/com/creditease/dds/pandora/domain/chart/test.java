package com.creditease.dds.pandora.domain.chart;

import com.creditease.dds.pandora.domain.chart.util.ChartModel;
import com.creditease.dds.pandora.domain.chart.util.DataModel;
import com.creditease.dds.pandora.domain.chart.util.Serie;

import java.util.ArrayList;
import java.util.List;


public class test {

public static void main(String[] args) {


	//柱状 折线 堆积测试
	String[] categories1 = {"上海", "北京", "武汉"};
	List<Serie> series = new ArrayList<Serie>();
	// 柱子名称：柱子所有的值集合

	//series.add(new Serie("上海", new Double[]{295263.64}));
	//series.add(new Serie("北京", new Double[] { 28719084.9 }));
	//series.add(new Serie("武汉", new Double[] { 2534721.46}));

	series.add(new Serie("Aaa", new Double[]{295263.64,20534721.46,2534721.46}));
	//series.add(new Serie("北京", new Double[] { 200.00 }));
	//series.add(new Serie("武汉", new Double[] { 300.00}));


	//String[] colors = {"#C23531","#2F4554"};
	String[] colors = {"#C23531"};
	DataModel data = new DataModel();
	data.setCategories(categories1);
	data.setSeriesLeft(series);
	//data.setSeriesRight(series2);
	ChartModel chartModel = new ChartModel();

	for (int i = 0; i < 1; i++) {
		chartModel.setTitle("汉字名称11");
		chartModel.setType(0);
		//chartModel.setSlope(1);
		chartModel.setColors(colors);
		chartModel.setPosition(1);
		String filePath = "D:\\数据中台项目\\数据生成图片\\AAAAAAAA.png";
		CrateChartFactory.crateChart(data, filePath, chartModel);
		//}

//		//散点图
//		Map<Object,Map<Number,Number>> map1 = new HashMap<Object,Map<Number,Number>>();
//		Map<Number,Number> map = new HashMap<Number,Number>();
//		map.put(100.0, 2100.0);
//		map.put(200.0, 2200.0);
//		map.put(300.0, 4200.0);
//		map.put(400.0, 5200.0);
//		map.put(500.0, 6200.0);
//		map.put(600.0, 7200.0);
//		map1.put("汉字",map);
//		DataModel data41 = new DataModel();
//		data41.setMaps(map1);
//		String[] colors1 = {"#C23531","#2F4554"};
//		ChartModel chartModel41 = new ChartModel();
//		chartModel41.setType(6);
//		chartModel41.setColors(colors1);
//		String filePath41 ="C:\\Users\\Administrator\\Desktop\\polar.png";
//		CrateChartFactory.crateChart(data41, filePath41, chartModel41);
//
//
		//饼图、环形图测试
		/*String filePath4 ="C:\\Users\\Administrator\\Desktop\\pie.png";

		String[] categories4 = { "Bananas", "Kiwi", "Mixed nuts", "Oranges", "Apples", "Pears", "Clementines", "Reddish (bag)", "Grapes (bunch)", };
		Object[] datas4 = { 8, 3, 1, 6, 8, 4, 4, 1, 1 };

		DataModel data4 = new DataModel();
		data4.setCategories(categories4);
		data4.setDatas(datas4);

		ChartModel chartModel4 = new ChartModel();
		chartModel4.setType(8);
		CrateChartFactory.crateChart(data4, filePath4, chartModel4);*/
//
//
//
//
//		//双轴图
//		String[] categories = { "1999-12-31", "2000-12-31", "2001-12-31", "2002-12-31", "2003-12-31", "2004-12-31", "2005-12-31", "2006-12-31", "2007-12-31",
//				"2008-12-31", "2009-12-31", "2010-12-31", "2011-12-31", "2012-12-31", "2013-12-31" };
//		for (int i = 0; i < categories.length; i++) {
//			categories[i]=categories[i].substring(0, 4);
//		}
//		List<Serie> seriesNetProfit = new ArrayList<Serie>();
//		// 净利润
//		Object[] netProfit = { 529, 495, 106, 128, 156, 193, 255, 335, 549, 125, 132,191, 272, 341, 409 };
//		// 股利支付率
//		Object[] payoutRatio = { "39.01", "40.50", "45.39", "30.46", "27.50", "24.34", "19.90", "19.48", "12.67", "10.40", "10.02", "11.97", "20.51", "30.01","40.21" };
//		seriesNetProfit.add(new Serie("净利润", netProfit));
//		String[] colors2 = {"#C23531","#2F4554"};
//		List<Serie> seriesPayoutRatio = new ArrayList<Serie>();
//		seriesPayoutRatio.add(new Serie("股利支付率", payoutRatio));
//		DataModel model = new DataModel();
//		model.setCategories(categories);
//		model.setSeriesLeft(seriesNetProfit);
//		model.setSeriesRight(seriesPayoutRatio);
//		ChartModel chartModel5 = new ChartModel();
//		chartModel5.setType(4);
//		chartModel5.setColors(colors2);
//		chartModel5.setYaxisLeft("净利润（万元）");
//		chartModel5.setYaxisRight("股利支付率(%)");
//		String filePath5 ="C:\\Users\\Administrator\\Desktop\\dubble.png";
//		CrateChartFactory.crateChart(model, filePath5, chartModel5);
	}
}
}
