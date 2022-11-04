package context;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBContext {
	public static Connection getConnection() throws Exception {
		DataSource ds = getDataSource();

		return ds.getConnection();
	}

	public static DataSource getDataSource() throws Exception {
		InitialContext initContext = new InitialContext();

		Context env = (Context) initContext.lookup("java:comp/env");

		return (DataSource) env.lookup("jdbc/prj321xwebshop");
	}
}
