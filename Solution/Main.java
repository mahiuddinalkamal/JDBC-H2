import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) throws Exception {

		Class.forName("org.h2.Driver");

		String DBurl = "jdbc:h2:tcp://localhost:9092/~/test";

		if (args != null && args.length > 0)
			DBurl = args[0];

		Connection conn = DriverManager.getConnection(DBurl, "sa", "");
		Statement stmt = conn.createStatement();

		// Creating Table
		String sql = "CREATE TABLE IF NOT EXISTS NEWPOPULATION " + "(COUNTRY_CODE varchar(4) PRIMARY KEY, "
				+ " AMOUNT INT)";
		try {
			stmt.executeUpdate(sql);
			System.out.println("Table NEWPOPULATION created successfully");
		} catch (Exception e) {
			System.out.println("Error! Table creation failed!");
		}

		// Altering Table
		sql = "ALTER TABLE COUNTRY ADD CURRENT_POPULATION INT";
		try {
			stmt.executeUpdate(sql);
			System.out.println("Table Country altered successfully");
		} catch (Exception e) {
			System.out.println("Error! Column already exists!");
		}

		// Inserting 100 tuples into NEWPOPULATION
		sql = "DELETE FROM NEWPOPULATION;" + "INSERT INTO NEWPOPULATION (COUNTRY_CODE , AMOUNT)"
				+ "SELECT CODE, (SELECT FLOOR(RAND()*(1000-1+1))+1)" + "FROM COUNTRY ORDER BY CODE LIMIT 100;";
		try {
			stmt.executeUpdate(sql);
			System.out.println("100 tuples inserted into NEWPOPULATION successfully");
		} catch (Exception e) {
			System.out.println("Error! Data insertion failed!");
		}

		// Updating Current_Population Column
		sql = "UPDATE COUNTRY " + "SET CURRENT_POPULATION = POPULATION + IFNULL((SELECT AMOUNT" + " FROM NEWPOPULATION"
				+ "  WHERE COUNTRY_CODE=COUNTRY.CODE),0);";
		try {
			stmt.executeUpdate(sql);
			System.out.println("Column Current_Population updated successfully");
		} catch (Exception e) {
			System.out.println("Error! Current_Population update failed!");
		}
		conn.close();
	}
}
