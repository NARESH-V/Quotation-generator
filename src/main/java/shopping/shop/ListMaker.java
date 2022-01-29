package shopping.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ListMaker {

	//method to create List
	public Map<String,Appliance> createList()throws IOException 
	{
	    Properties prop = new Properties();
    	prop.load(new FileInputStream("Appliances.properties"));
    	
    	String excelFilePath = prop.getProperty("source");
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        DataFormatter formatter = new DataFormatter();
       
        Map<String,Appliance> Appli= new LinkedHashMap<String,Appliance>();
        Workbook workbook = new XSSFWorkbook( inputStream );
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        iterator.next();
        while ( iterator.hasNext() ) 
        {
            Row nextRow = iterator.next();
           
            String str   = formatter.formatCellValue( nextRow.getCell(0) );
            String units = formatter.formatCellValue( nextRow.getCell(1) );
            String price = formatter.formatCellValue( nextRow.getCell(2) );
            String color = formatter.formatCellValue( nextRow.getCell(3) );
            
            color=color.toUpperCase();
            String[] product=  str.split( "_" );
            String[] colors=  color.split( "/" );   
            
            Appli.put( product[0], new Appliance( product,units,price,colors ) );
        }
        
        workbook.close();
        inputStream.close();
        return Appli;
	}
	
	
	//method to display List
	void displayList (Map<String,Appliance> Appliances ) throws IOException
	{
		OutputStreamWriter streamWriter = new OutputStreamWriter( System.out );
		
		 for ( Map.Entry<String,Appliance> entry : Appliances.entrySet() ) 
		 {
			 Appliance a = entry.getValue();
			 streamWriter.write( String.format( "%-10s %-20s %-30s", a.serial_num, a.brand, a.product ) );
	         streamWriter.write ("\n" );      
		 }
		streamWriter.flush(); 
	}

}
