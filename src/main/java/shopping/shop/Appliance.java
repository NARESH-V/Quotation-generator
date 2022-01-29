package shopping.shop;


import java.io.PrintStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class Appliance {
	//Class for Appliances with their attributes
	String serial_num;
	String brand;
	String product;
	int available_units;
	int price;
	String[] color;
	
	Appliance( String[] product,String available_units,String price,String[]colors )
	{
		this.serial_num = product[0];
		this.brand =  product[1].toUpperCase();
		this.product = product[2].toUpperCase();
		this.available_units = Integer.parseInt( available_units );
		this.price = Integer.parseInt( price );
		this.color = colors.clone();	
	}
	
	Appliance(){}
	
	static int no = 1;
	//display product details
	void displayDetails(Appliance myproduct)throws IOException
	{
		PrintStream myout =  new PrintStream( new FileOutputStream( FileDescriptor.out ) );
		
		myout.print( "\n****PRODUCT " + no + " INFO**** \n" );
		myout.print( "\nSerial No.      : " + myproduct.serial_num );
		myout.print( "\nBrand           : " + myproduct.brand );
		myout.print( "\nProduct         : " + myproduct.product );
		myout.print( "\nPrice           : " + myproduct.price+" $" );
		myout.print( "\nAvailable Units : " + myproduct.available_units );
		myout.print( "\nColors          : " );
		for( String s : myproduct.color )
			myout.print( s+" " );
		myout.print( "\n\n" );
		no++;
	}
	

}
