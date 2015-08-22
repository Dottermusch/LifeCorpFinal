package data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import model.State;

public class StateDB 
{
	public static ArrayList<State> getAllStates()
	{
		final String connectionUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
			Connection conn =
					DriverManager.getConnection(connectionUrl,"TESTUSER","Password123");
			
			String query = "SELECT * FROM TESTUSER.DEMO_STATES ORDER BY STATE_NAME";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			ArrayList<State> states = new ArrayList<State>();
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				State state = new State();
				state.setStateAbbrv(rs.getString("ST"));
				state.setStateName(rs.getString("STATE_NAME"));
				states.add(state);				
			}
			
			rs.close();
			return states;
		} 
		catch (Exception e)
		{
			return null;
		
		}
	}
}
