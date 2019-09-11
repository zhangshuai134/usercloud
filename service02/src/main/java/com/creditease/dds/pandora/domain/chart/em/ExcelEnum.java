package com.creditease.dds.pandora.domain.chart.em;

public interface ExcelEnum {
	
	public enum excelType{
		
		 XLS(1,"xls"),
		 XLSX(2,"xlsx")
		;
		
		private int code;
		private String type;
		
		private excelType(int code,String type) {
			this.code = code;
			this.type =type;
		}
		

		public int getCode() {
			return code;
		}

		public String getType() {
			return type;
		}
		



		
	}
	
	 
}
