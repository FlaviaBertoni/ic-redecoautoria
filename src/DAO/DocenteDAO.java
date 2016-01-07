package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import model.Docente;


public class DocenteDAO {
	
	public void insereDocente(Docente docente)
	{
		Conexao objConexao = new Conexao();
		String sql="INSERT INTO rede.docente(nome_docente, enderecoProfissional_docente, resumo_docente) VALUES(?,?,?);";
		PreparedStatement stmt = null;
		try
		{
			stmt = objConexao.getConn().prepareStatement(sql);
			stmt.setString(1, docente.getNomeCompleto());
			stmt.setString(2, docente.getEnderecoProfissional());
			stmt.setString(3, docente.getResumo());
			stmt.execute();

			stmt.close();
			objConexao.getConn().close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public int selecionaIdDocente()
	{
		int id=0;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Conexao objConexao = new Conexao();
		
		try
		{
			stmt = objConexao.getConn().prepareStatement("SELECT MAX(id_docente) AS id FROM docente");
			rs = stmt.executeQuery();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			while(rs.next())
			{
				id = rs.getInt("id");
			}
			
			rs.close();
			stmt.close();
			objConexao.getConn().close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return id;
	}
}
