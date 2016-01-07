package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
Connection con;
	
	public Conexao(){
		try
		{
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rede", "root","");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return con;
	}

	public void setConn(Connection conn) {
		this.con = conn;
	}

}
