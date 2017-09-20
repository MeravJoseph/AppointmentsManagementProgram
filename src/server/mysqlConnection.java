package server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;

public class mysqlConnection extends Application{

	public static void main(String[] args) 
	{
		try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {/* handle the error*/}
        
        try 
        {
        	Connection conn =
        			DriverManager.getConnection("jdbc:mysql://localhost/test","student","student");

        	PreparedStatement pstmt;
    		try 
    		{
	            System.out.println("SQL connection succeed");
	            int pNum = 3; //patient number
	            pstmt = conn.prepareStatement("SELECT * FROM doctors WHERE num = ?;");
				pstmt.setInt(1, pNum);
				ResultSet rs = pstmt.executeQuery();
		 		
				while(rs.next())
		 		{
					 // Print out the flight num and the price
					 System.out.println("doctor num " + rs.getString(1)+ " first name is " +rs.getString(2)+" " + rs.getString(3));
				} 
    		} catch (SQLException e) {e.printStackTrace();}
            
            
            
            
            System.out.println("\nQuestion 2a:");
           // changeFlightPrice(conn, 2, 666);
            
            System.out.println("\nQuestion 2b:");
           // updatePricesRsSet(conn, 1000, 50);
 
            System.out.println("\nQuestion 2c:");
          //  updatePricesPstmt(conn, 1000, 50);
            
            conn.close();
            
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
        
        
   	}
	
	
	public static void changeFlightPrice(Connection con, int flightNum, float flightPrice)
	{
		PreparedStatement pstmt;
		try 
		{
			//update the flight's price
			pstmt = con.prepareStatement("UPDATE patients SET price = ? WHERE num = ?;");
			pstmt.setFloat(1, flightPrice);
			pstmt.setInt(2, flightNum);
			pstmt.executeUpdate();
			
			
			pstmt = con.prepareStatement("SELECT * FROM patients WHERE num = ?;");
			pstmt.setInt(1, flightNum);
			ResultSet rs = pstmt.executeQuery();
	 		
			while(rs.next())
	 		{
				 // Print out the flight num and the price
				 System.out.println("flight num " + rs.getString(1)+" new price is " +rs.getString(5));
			} 
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	
	// update prices using result set
	public static void updatePricesRsSet(Connection con, int flightDistance, float priceAddition)
	{
		PreparedStatement pstmt;
		try 
		{
			
			pstmt = con.prepareStatement("SELECT num,distance,price FROM flights WHERE distance > ?;", 
										  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1, flightDistance);
			ResultSet rs = pstmt.executeQuery();
	 		
			while(rs.next())
	 		{
				 System.out.print("flight num " + rs.getInt(1)+ " distance is " +rs.getString(2) + " previous price is " + rs.getString(3));
				 rs.updateFloat("price", (rs.getFloat(3) + priceAddition));
				 rs.updateRow();
				 // Print out the flight num, the distance and the prices (previous and new)
				 System.out.println(" new price is " + rs.getString(3));

			} 
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	
	
	// update prices using prepared statement
	public static void updatePricesPstmt(Connection con, int flightDistance, float priceAddition)
	{
		PreparedStatement pstmt;
		try 
		{
			
			pstmt = con.prepareStatement("SELECT * FROM flights WHERE distance > ?;");
			pstmt.setInt(1, flightDistance);
			ResultSet rs = pstmt.executeQuery();
	 		
			while(rs.next())
	 		{
				 // Print out the flight num, the distance and the prices (previous and new)
				 System.out.println("flight num " + rs.getInt(1)+ ", it's distance is " +rs.getString(4) + ", previous price is " + rs.getString(5));
				 // changeFlightPrice updates the prices through preparedStatement
				 changeFlightPrice(con, rs.getInt(1), (rs.getFloat(5) + priceAddition));
			} 
		} catch (SQLException e) {e.printStackTrace();}
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
