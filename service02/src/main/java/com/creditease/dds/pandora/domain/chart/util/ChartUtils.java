package com.creditease.dds.pandora.domain.chart.util;

import com.creditease.dds.pandora.domain.chart.em.ChartEnum;
import org.apache.commons.collections.CollectionUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;



public class ChartUtils {
	private static String NO_DATA_MSG = "";
	private static Font FONT = new Font("宋体", Font.PLAIN, 14);


	public static Color[] CHART_COLORS = {
			new Color(31,129,188), new Color(92,92,97), new Color(144,237,125), new Color(255,188,117),
			new Color(153,158,255), new Color(255,117,153), new Color(253,236,109), new Color(128,133,232),
			new Color(158,90,102),new Color(255, 204, 102) };// 颜色

	/**
	 * 中文主题样式 解决乱码
	 */
	public static void setChartTheme() {
		// 设置中文主题样式 解决乱码
		StandardChartTheme chartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		chartTheme.setExtraLargeFont(FONT);
		// 设置图例的字体
		chartTheme.setRegularFont(FONT);
		// 设置轴向的字体
		chartTheme.setLargeFont(FONT);
		chartTheme.setSmallFont(FONT);
		chartTheme.setTitlePaint(new Color(51, 51, 51));
		chartTheme.setSubtitlePaint(new Color(85, 85, 85));

		chartTheme.setLegendBackgroundPaint(Color.WHITE);// 设置标注
		chartTheme.setLegendItemPaint(Color.BLACK);//
		chartTheme.setChartBackgroundPaint(Color.WHITE);
		// 绘制颜色绘制颜色.轮廓供应商
		// paintSequence,outlinePaintSequence,strokeSequence,outlineStrokeSequence,shapeSequence

		Paint[] OUTLINE_PAINT_SEQUENCE = new Paint[] { Color.WHITE };
		// 绘制器颜色源
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS, OUTLINE_PAINT_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
				DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		chartTheme.setDrawingSupplier(drawingSupplier);

		chartTheme.setPlotBackgroundPaint(Color.WHITE);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框
		chartTheme.setLabelLinkPaint(new Color(8, 55, 114));// 链接标签颜色
		chartTheme.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);

		chartTheme.setAxisOffset(new RectangleInsets(5, 12, 5, 12));
		chartTheme.setDomainGridlinePaint(new Color(192, 192, 192));// X坐标轴垂直网格颜色
		chartTheme.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色

		chartTheme.setBaselinePaint(Color.WHITE);
		chartTheme.setCrosshairPaint(Color.BLUE);// 不确定含义
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染

		chartTheme.setItemLabelPaint(Color.black);
		chartTheme.setThermometerPaint(Color.white);// 温度计


		ChartFactory.setChartTheme(chartTheme);
	}


	/**
	 * 构造柱状图类型数据
	 */
	public static DefaultCategoryDataset createDefaultCategoryDataset(String[] categories,List<Serie> series) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		if(CollectionUtils.isEmpty(series)||null==categories||categories.length==0){
			return dataset;
		}

		for (Serie serie : series) {
			String name = serie.getName();
			List<Object> data = serie.getData();
			if (data != null && categories != null && data.size() == categories.length) {
				for (int index = 0; index < data.size(); index++) {
					String value = data.get(index) == null ? "" : data.get(index).toString();
					if (ChartUtils.isPercent(value)) {
						value = value.substring(0, value.length() - 1);
					}else {
						BigDecimal bigDecimal = new BigDecimal(value);
						if (ChartUtils.isNumber(bigDecimal.toString())) {
							dataset.setValue(Double.parseDouble(value), name, categories[index]);
						}
					}
				}
			}

		}
		return dataset;
	}

	/**
	 * 构造饼状图类型数据
	 */
	public static DefaultPieDataset createPieDataset(DataModel dataModel) {
		String[] categories = dataModel.getCategories();
		Object[] datas = dataModel.getDatas();
		DefaultPieDataset dataset = new DefaultPieDataset( );

		if(null==categories||categories.length==0||null==datas||datas.length==0){
			return dataset;
		}

		for (int i = 0; i < categories.length && categories != null; i++) {
			String value = datas[i].toString();
			if (ChartUtils.isPercent(value)) {
				value = value.substring(0, value.length() - 1);
			}else {
				BigDecimal bigDecimal = new BigDecimal(value);
				if (ChartUtils.isNumber(bigDecimal.toString())) {
					dataset.setValue(categories[i], Double.valueOf(value));
				}
			}
		}
		return dataset;
	}


	/**
	 * 构造散点图类型数据
	 */
	public static XYSeriesCollection createScatterDataset(DataModel dataModel) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		Map<Object,Map<Number,Number>> map = dataModel.getMaps();

		if(map==null||map.size()>0){
			return dataset;
		}

		if(map!=null&&map.size()>0) {
			for (Map.Entry<Object,Map<Number,Number>> entry : map.entrySet()) {
				Object key = entry.getKey();
				Map<Number,Number> newMap = entry.getValue();
				XYSeries xyseries = new XYSeries((Comparable) key);
				for (Map.Entry<Number,Number> entry1 : newMap.entrySet()) {
					xyseries.add(entry1.getKey(),entry1.getValue());
				}
				dataset.addSeries(xyseries);
			}
		}
		return dataset;
	}



	/**
	 * 必须设置文本抗锯齿
	 */
	public static void setCommonPros(JFreeChart chart,RectangleEdge rec,CategoryLabelPositions slope) {
		setCommonPros(chart,rec);
		CategoryPlot categoryPlot = chart.getCategoryPlot();
		CategoryAxis domainAxis = categoryPlot.getDomainAxis();//对X轴做操作
		domainAxis.setCategoryLabelPositions(slope);
	}

	public static void setCommonPros(JFreeChart chart,RectangleEdge rec) {
		TextTitle mTextTitle = chart.getTitle();
		mTextTitle.setFont(new Font("宋体", Font.BOLD, 16));//设置标题样式
		chart.setTextAntiAlias(false);//设置文本抗锯齿
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));//设置图例无边框，默认黑色边框
		chart.getLegend().setPosition(rec);//设置图例位置
	}



	/**
	 * 设置柱状、折线、堆积、瀑布样式
	 *
	 * @param plot
	 * @param isShowDataLabels
	 *            是否显示数据标签
	 */
	public static void setCommonRender(CategoryPlot plot, String[] colors,int type,boolean isShowDataLabels) {
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		if(ChartEnum.BAR.getType()==type) {//柱状
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setMaximumBarWidth(0.075);// 设置柱子最大宽度
			renderer.setShadowVisible(false);//去除柱子阴影

			setCommonBarRender(renderer,colors,isShowDataLabels);
		}else if(ChartEnum.LINE.getType()==type) {//折线
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			renderer.setBaseShapesVisible(true);// 数据点绘制形状
			renderer.setStroke(new BasicStroke(1.5F));
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(StandardCategoryItemLabelGenerator.DEFAULT_LABEL_FORMAT_STRING,
					NumberFormat.getInstance()));
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.BOTTOM_CENTER));
			setCommonBarRender(renderer,colors,isShowDataLabels);
		}else if(ChartEnum.STACKEDBAR.getType()==type) {//堆积
			StackedBarRenderer renderer = (StackedBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			plot.setRenderer(renderer);
			renderer.setShadowVisible(false);//去除柱子阴影

			setCommonBarRender(renderer,colors,isShowDataLabels);
		}else if(ChartEnum.WATERFALL.getType()==type) {//瀑布
			WaterfallBarRenderer  renderer = (WaterfallBarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

			if (isShowDataLabels) {
				renderer.setBaseItemLabelsVisible(true);//柱子上方是否显示值
			}

			Color color = null;
			if(colors.length>0) {
				color = Color.decode(colors[0]);
			}else {
				int index = (int) (Math.random() * CHART_COLORS.length);
				color = CHART_COLORS[index];
			}
			renderer.setSeriesPaint(0,color);//指定分类图形的颜色
			renderer.setFirstBarPaint(color);   //第一个柱图的颜色
			renderer.setLastBarPaint(color);  //最后一个柱图的颜色
			renderer.setPositiveBarPaint(color);  //正值柱图的颜色
			renderer.setNegativeBarPaint(color);  //负值柱图的颜色
		}else if(ChartEnum.AREA.getType()==type) {//面积

			AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

			setCommonBarRender(renderer,colors,isShowDataLabels);
		}

	}


	/**
	 * 设置雷达、散点渲染
	 *
	 * @param plot
	 * @param isShowDataLabels
	 */
	public static void setScapoRenderer(XYPlot plot,String[] colors,boolean isShowDataLabels) {

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());

		if (isShowDataLabels) {
			renderer.setBaseItemLabelsVisible(true);//柱子上方是否显示值
		}
		//设置颜色
		for(int i=0;i<colors.length;i++) {
			renderer.setSeriesPaint(i, Color.decode(colors[i])); // 给series1 Bar
		}
	}


	/**
	 * 设置双轴图渲染
	 *
	 * @param plot
	 * @param isShowDataLabels
	 */
	public static void setDualaxisRenderer(CategoryPlot plot,String[] colors,DataModel dataModel, ChartModel model,boolean isShowDataLabels) {

		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setMaximumBarWidth(0.075);// 设置柱子最大宽度
		renderer.setShadowVisible(false);//去除柱子阴影

		setCommonBarRender(renderer,colors,isShowDataLabels);

		List<Serie> seriesRight = dataModel.getSeriesRight();
		if(seriesRight!=null&&seriesRight.size()>0) {
			DefaultCategoryDataset datasetPayoutRatio = createDefaultCategoryDataset(dataModel.getCategories(),dataModel.getSeriesRight());
			plot.setDataset(1, datasetPayoutRatio);
			plot.mapDatasetToRangeAxis(1, 1);

			// 右侧Y轴
			NumberAxis numberaxis = new NumberAxis(model.getYaxisRight());
			plot.setRangeAxis(1, numberaxis);
			// 隐藏Y刻度
			numberaxis.setAxisLineVisible(true);
			numberaxis.setTickMarksVisible(true);
			// 设置折线图样式
			LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();

			for(int i=0;i<colors.length;i++) {
				lineRenderer.setSeriesPaint(i, Color.decode(colors[i]));
			}

			lineRenderer.setBaseShapesVisible(true);// 数据点绘制形状
			lineRenderer.setBaseItemLabelsVisible(true);
			plot.setRenderer(1, lineRenderer);

			plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);// 绘制Z-index, 将折线图在前面
		}

	}


	private static void setCommonBarRender(AbstractCategoryItemRenderer renderer,String[] colors, boolean isShowDataLabels) {
		if (isShowDataLabels) {
			renderer.setBaseItemLabelsVisible(true);//柱子上方是否显示值
		}
		//设置颜色
		for(int i=0;i<colors.length;i++) {
			renderer.setSeriesPaint(i, Color.decode(colors[i])); // 给series1 Bar
		}
	}


	/**
	 * 设置类别图表(CategoryPlot) 不显示图表阴影
	 */
	public static void setXAixs(CategoryPlot plot) {
		Color lineColor = new Color(31, 121, 170);
		plot.getDomainAxis().setAxisLinePaint(lineColor);// X坐标轴颜色
		plot.getDomainAxis().setTickMarkPaint(lineColor);// X坐标轴标记|竖线颜色

	}

	/**
	 * 设置类别图表(CategoryPlot) Y坐标轴线条颜色和样式 同时防止数据无法显示
	 */
	public static void setYAixs(CategoryPlot plot) {
		Color lineColor = new Color(192, 208, 224);
		ValueAxis axis = plot.getRangeAxis();
		axis.setAxisLinePaint(lineColor);// Y坐标轴颜色
		axis.setTickMarkPaint(lineColor);// Y坐标轴标记|竖线颜色
		// 隐藏Y刻度
		axis.setAxisLineVisible(false);
		axis.setTickMarksVisible(false);
		// Y轴网格线条
		plot.setRangeGridlinePaint(new Color(192, 192, 192));
		plot.setRangeGridlineStroke(new BasicStroke(1));

		plot.getRangeAxis().setUpperMargin(0.1);// 设置顶部Y坐标轴间距,防止数据无法显示
		plot.getRangeAxis().setLowerMargin(0.1);// 设置底部Y坐标轴间距

	}


	/**
	 * 设置饼状图渲染
	 */
	public static void setPieRender(JFreeChart chart,RectangleEdge rec) {

		setCommonPros(chart,rec);

		Plot plot = chart.getPlot();
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setInsets(new RectangleInsets(10, 10, 5, 10));
		PiePlot piePlot = (PiePlot) plot;
		piePlot.setInsets(new RectangleInsets(0, 0, 0, 0));
		piePlot.setCircular(true);// 圆形

		// piePlot.setSimpleLabels(true);// 简单标签
		piePlot.setLabelGap(0.01);
		piePlot.setInteriorGap(0.05D);
		piePlot.setLegendItemShape(new Rectangle(10, 10));// 图例形状
		piePlot.setIgnoreNullValues(true);
		piePlot.setLabelBackgroundPaint(null);// 去掉背景色
		piePlot.setLabelShadowPaint(null);// 去掉阴影
		piePlot.setLabelOutlinePaint(null);// 去掉边框
		piePlot.setShadowPaint(null);
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		String format = "{0}:{1}";
		//String format = "{0}:{2}";
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));// 显示标签数据
	}

	/**
	 * 是不是一个%形式的百分比
	 *
	 * @param str
	 * @return
	 */
	public static boolean isPercent(String str) {
		return str != null ? str.endsWith("%") && isNumber(str.substring(0, str.length() - 1)) : false;
	}

	/**
	 * 是不是一个数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str != null ? str.matches("^[-+]?(([0-9]+)((([.]{0})([0-9]*))|(([.]{1})([0-9]+))))$") : false;
	}


	public static String getFileName(String filePath) {
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1);
		return fileName;
	}

}
