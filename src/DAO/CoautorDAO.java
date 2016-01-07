package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.Conexao;
import model.Coautor;

public class CoautorDAO {
	
	public void insereCoautor(Coautor coautor)
	{
		Conexao objConexao = new Conexao();
		
		String sql="INSERT INTO rede.coautor(nome_coautor, id_coautor) "
				+ "VALUES(?,?)";
		PreparedStatement stmt = null;
		try
		{
			stmt = objConexao.getConn().prepareStatement(sql);
			stmt.setString(1, coautor.getNome());
			stmt.setInt(2, coautor.getId());
			
			stmt.execute();
			stmt.close();
			objConexao.getConn().close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public int verificaCoautor(String nome)
	{
		Conexao objConexao = new Conexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id=0;
		
		try
		{
			stmt = objConexao.getConn().prepareStatement("SELECT id_coautor FROM coautor WHERE nome_coautor LIKE ?");
			stmt.setString(1, nome);
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
				id = rs.getInt("id_coautor");
				
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
	
	public int selecionaCoautor()
	{
		int id=0;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Conexao objConexao = new Conexao();
		
		try
		{
			stmt = objConexao.getConn().prepareStatement("SELECT MAX(id_coautor) AS id FROM coautor");
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
