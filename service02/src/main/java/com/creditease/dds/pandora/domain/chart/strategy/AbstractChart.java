package com.creditease.dds.pandora.domain.chart.strategy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.creditease.dds.pandora.domain.chart.em.ChartWriteEnum;
import com.creditease.dds.pandora.domain.chart.em.ExcelEnum;
import com.creditease.dds.pandora.domain.chart.util.ChartModel;
import com.creditease.dds.pandora.domain.chart.util.ChartUtils;
import com.creditease.dds.pandora.domain.chart.util.DataModel;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;



/**
 * 构建图表---模板方法模式
 * @author haihongjia
 */
public abstract class AbstractChart<T> {
	
	
	public Boolean buildChart(DataModel dataModel,String filePath,ChartModel chartModel){
		chartModel.setSlope(3);
		//组装数据
		AbstractDataset dataset = (AbstractDataset) createDataset(dataModel);
		
		//构造chart 
		ChartUtils.setChartTheme();//主题
		JFreeChart chart =  (JFreeChart) createJfreeChart(dataset,chartModel);
		
		//处理chart中文显示、渲染等问题  
		processChart(dataModel,chart,chartModel);
		
		//默认写入图片
		Boolean res = writeChartToResult(chart,filePath,chartModel);
		
		return res;
	}
	
	
    protected abstract T createDataset(DataModel dataModel);
    
    
    protected abstract T createJfreeChart(AbstractDataset dataset,ChartModel model);
    
      
    protected abstract void processChart(DataModel dataModel,JFreeChart chart,ChartModel model);
    
    private Boolean writeChartToResult(JFreeChart chart,String filePath,ChartModel model) {
    	Boolean res = false;
    	int writeType = model.getWriteType();
    	if(ChartWriteEnum.PICTURE.getType()==writeType) {
    		res =  writeChartToPicture(chart,filePath,model);
    	}else if(ChartWriteEnum.EXCEL.getType()==writeType){
    		res =  writeChartToExcel(chart,filePath,model);
    	}
		return res;
    }
    
    private Boolean writeChartToPicture(JFreeChart chart,String filePath,ChartModel model)  {
    	
    	boolean retuenStr = true;
		int width = model.getImageWidth();
		int height = model.getImageHeight();
		FileOutputStream fos_png = null;
		try {
			fos_png=new FileOutputStream(filePath);
			ChartUtilities.writeChartAsPNG(fos_png, chart, width, height);
			fos_png.close();
		} catch (Exception e) {
			System.out.println("图表生成图片异常："+e);
			retuenStr = false;
			return retuenStr;
		}
		/*try {
			int width = model.getImageWidth();
			int height = model.getImageHeight();
			ChartUtilities.saveChartAsPNG(file, chart, width, height);

		} catch (IOException e) {
			System.out.println("图表生成图片异常："+e);
			retuenStr = false;
			return retuenStr;

		}*/
		
		return retuenStr;
    }
    
   
    private Boolean writeChartToExcel(JFreeChart chart,String filePath,ChartModel model)  {
    	
    	boolean retuenStr = true;
    	
        try {
        	
        	Workbook wb = createWb(filePath);
        	Sheet sheet = wb.getSheetAt(0);
        	
        	ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        	FileOutputStream fileOut = null;
			ChartUtilities.writeChartAsPNG(byteArrayOut, chart, model.getImageWidth(), model.getImageHeight());
			//创建一个不带透明色的BufferedImage对象
			BufferedImage bufferImg = new BufferedImage(400, 200,  BufferedImage.TYPE_INT_RGB);
			ImageIO.write(bufferImg, "png", byteArrayOut);
			
			createWb(byteArrayOut,wb,sheet,filePath);
      
			fileOut =  new FileOutputStream(filePath);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println("图表写入该文件"+filePath+"异常："+e);
			retuenStr = false;
			return retuenStr;
		} 
		
		return retuenStr;
    }

	
    private Workbook createWb(String filePath) throws FileNotFoundException, IOException {
    	Workbook wb = null;
    	String fileName = ChartUtils.getFileName(filePath);
    	if(fileName.endsWith(ExcelEnum.excelType.XLS.getType())) {
    		wb = new HSSFWorkbook(new FileInputStream(filePath));
    	}else if(fileName.endsWith(ExcelEnum.excelType.XLSX.getType())) {
    		wb = new XSSFWorkbook(new FileInputStream(filePath));
    	}
    	return wb;
    }
    
    private void createWb(ByteArrayOutputStream byteArrayOut,Workbook wb,Sheet sheet,String filePath) {
    	
    	String fileName = ChartUtils.getFileName(filePath);
    	
		if(fileName.endsWith(ExcelEnum.excelType.XLS.getType())) {
			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			// 八个参数，前四个表示图片离起始单元格和结束单元格边缘的位置，
			// 后四个表示起始和结束单元格的位置，如下表示从第2列到第12列，从第1行到第15行,需要注意excel起始位置是0
			XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) 2, (short) 1, (short) 12, (short) 15);
			anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
			// 插入图片
			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
    	}else if(fileName.endsWith(ExcelEnum.excelType.XLSX.getType())) {
    		XSSFDrawing createDrawingPatriarch = (XSSFDrawing) sheet.createDrawingPatriarch();
    		XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) 2, (short) 1, (short) 12, (short) 15);
    		anchor.setAnchorType(AnchorType.DONT_MOVE_AND_RESIZE);
    		createDrawingPatriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));
    	}
    }
    
    
    
}
