import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.api.Trigger;

public class NewPopulationTrigger implements Trigger {

	// you only need to change the fire function here
	@Override
	public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {

		Statement stmt = conn.createStatement();

		String sql = "UPDATE COUNTRY " + "SET CURRENT_POPULATION = POPULATION + IFNULL((SELECT AMOUNT"
				+ "  FROM NEWPOPULATION" + "  WHERE COUNTRY_CODE=COUNTRY.CODE),0);";
		try {
			stmt.executeUpdate(sql);
			System.out.println("Current_Population updated by trigger successfully");
		} catch (Exception e) {
			System.out.println("Error! Current_Population column update is failed by trigger!");
		}
	}

	@Override
	public void init(Connection arg0, String arg1, String arg2, String arg3, boolean arg4, int arg5)
			throws SQLException {
		// TODO Auto-generated method stub

	}

}