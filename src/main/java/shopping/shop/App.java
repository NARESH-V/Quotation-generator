/**
 *  @author NARESH V
 *  
 */

package shopping.shop;

import java.util.*; 
import org.apache.log4j.LogManager;  
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.*;
 

public class App 
{
	  
	private static final Logger logger = LogManager.getLogger( App.class );
	static PrintStream myout =  new PrintStream( new FileOutputStream( FileDescriptor.out ) ); 
	static Scanner sc = new Scanner( System.in );
	static Map<String,Appliance> Appliances ;
	static Appliance obj = new Appliance();
	static Set<String> choices;
	static Iterator<String> iterator;
	static Map<String,Integer> requiredUnits;
	static String key = "";
	
	
    public static void main( String[] args )throws IOException
    {
    	  
    	PropertyConfigurator.configure("log4j.properties");

    	//creating and displaying the list of products
    	ListMaker myproducts = new ListMaker();
        Appliances = myproducts.createList();
        logger.debug("List of products Created", null);
        
        myout.print( String.format( "%70s", "WELCOME TO LET's SHOP\n" ) );
        myout.print( String.format( "\n%-10s %-20s %-30s\n\n","S.No","BRAND","APPLIANCES" ) );
        
        myproducts.displayList(Appliances);
       
        
        //getting user input
        myout.print( "\n\nSELECT YOUR PRODUCTS\t\t*(Enter -1 to end selection)\n" );
        choices = new TreeSet<String>();
        logger.debug( "Getting user inputs-product number :", null );
        getProductNumber();
   	   	
   	      
   	   	//displaying product details
        logger.debug( "displaying product details", null );
   	   	displayProductDetails( );
   	   	
   	   	
   	   	//getting no.of units
   	   	myout.print("\n\nEnter Required No.Of Units\n");
   	    requiredUnits = new TreeMap<String,Integer>();
   	    logger.debug( "getting user input - required units", null );
   	    gettingRequiredUnits();
   	    
   	    //printing Quotation
   	    myout.print("\n\nDO you need to print the Quotation ?\n");
   	    myout.print("\n1) YES\n2) NO\n");
   	    logger.debug( "getting user input - need for quotation", null );
   	    int option = sc.nextInt();
   	    
   	    if( option == 1)
   	    {	
   	    	logger.debug( "user input - YES", null );
   	    	Quotation quotation = new Quotation();
   	    	quotation.createQuotation( Appliances,requiredUnits );
   	    	logger.debug( "Quotation printed", null );
   	    	myout.print("\n\nQuotation Printed\n");
   	    }
   	    else
   	    	logger.debug( "user input - NO", null );
   	    	
   	    
   	   	
   	    
   	    myout.print("\n\nTHANK YOU....!!!\n");
   	   	myout.close();
   	   	sc.close();
    }
    
    //method to validate input - product number
    static void validate( String s , Map<String,Appliance> Appliances )throws invalidProductException
    {
    	if( Appliances.get(s) == null )
    		throw new invalidProductException( "Product Not Found" );
    		
    }
    
    //method to validate input - required units
    static void validate( int required , int available )throws invalidProductException
    {
    	if( required > available )
    		throw new invalidProductException( "please check the availability of units" );
    		
    }
    
    //method to get product number from the user
    static void getProductNumber()
    {
    	String option= "" ;
   	   	while( true )
   	   	{
   	   		option = sc.nextLine();
   	   		if( option.contentEquals( "-1" ) )
   	   			break;
   	   		try
   	   		{
   	   			validate( option , Appliances );
   	   			logger.debug( "input : " + option , null );
   	   			choices.add( option ); 
   	   		}
   	   		catch( Exception e )
   	   		{
   	   			myout.print( e + "\n" );
   	   			logger.error( e + "-  " + option );
   	   			
   	   		}
   	   	}
    }
    
    //displaying the details of the selected products
    static void displayProductDetails( )throws IOException
    {
	
   	   	iterator = choices.iterator();
   	   	while( iterator.hasNext() )
   	   	{
   	   		key = iterator.next();
   	   		obj.displayDetails( Appliances.get( key ) );
   	   	}
    }
    
    //getting required units for each product
    static void gettingRequiredUnits()
    {
    	iterator = choices.iterator();
   	   	int required = 0;
   	   	boolean flag = false;
   	   	
   	   	while( iterator.hasNext() )
   	   	{
   	   		if( !flag )
   	   		key = iterator.next();
   	   		
   	   		flag = false;
   	   		myout.print( "\n" + key + " - No.Of Units : " );
   	   		try{
   	   				required = sc.nextInt();
   	   				validate( required,Appliances.get( key ).available_units );
   	   				logger.debug( "user input : " + required , null );
   	   				requiredUnits.put( key , required );
   	   			}
   	   		catch(Exception e)
   	   			{
   	   				myout.print( e + "\n" );
   	   				logger.error( e + "- less availability of units " );
   	   				flag = true;
   	   			}
   	   	}   	
    }
}

