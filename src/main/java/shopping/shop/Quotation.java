package shopping.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Quotation {
	
	//method to write quotation
	public void createQuotation( Map<String,Appliance> appliances , Map<String,Integer> requiredUnits )throws FileNotFoundException, IOException 
	{
		Properties prop = new Properties();
    	prop.load(new FileInputStream( "Appliances.properties" ));
    	
    	String excelFilePath = prop.getProperty( "destination" );
		Workbook wb = new XSSFWorkbook(); 
		Sheet sheet = wb.createSheet( "Quotation" );
		
		Set<String> keyset = requiredUnits.keySet();
        int rownum = 0;
        Row row ; Cell cell;
        row = sheet.createRow( rownum++ );
        
        cell = row.createCell(0);
        cell.setCellValue( "PRODUCT CODE" );
        
        cell = row.createCell(1);
        cell.setCellValue( "PRODUCT" );
        
        cell = row.createCell(2);
        cell.setCellValue( "PRICE" );
        
        cell = row.createCell(3);
        cell.setCellValue( "QUANTITY" );
        
        cell = row.createCell(4);
        cell.setCellValue( "TOTAL" );
        
        int grandtotal=0;
        int cellnum;
        for (String key : keyset)
        {
        	row = sheet.createRow(rownum++);
        	cellnum = 0;
        	Appliance obj = appliances.get(key);
        	int units =requiredUnits.get(key);
        	
        	cell = row.createCell(cellnum++);
            cell.setCellValue( (String)obj.serial_num );
            
            cell = row.createCell(cellnum++);
            cell.setCellValue( (String)obj.brand+" - "+obj.product );
            
            cell = row.createCell(cellnum++);
            cell.setCellValue( obj.price+" $" );
            
            cell = row.createCell(cellnum++);
            cell.setCellValue( units+"" );
            
            cell = row.createCell(cellnum++);
            cell.setCellValue( units* obj.price+" $" );
            
            grandtotal += units*obj.price;
            
        }
        
        row = sheet.createRow(rownum++);
        cell = row.createCell(3);
        cell.setCellValue( "GRAND TOTAL" );
        cell = row.createCell(4);
        cell.setCellValue( grandtotal +" $" );
        
		
		
		OutputStream fileOut = new FileOutputStream( new File(excelFilePath) ); 
		wb.write( fileOut ); 
		wb.close();
	}

}

