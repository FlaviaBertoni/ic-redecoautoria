package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexao.Conexao;
import model.RedeCoautoria;

public class RedeCoautoriaDAO {
	
	public ArrayList<RedeCoautoria> redeSocialCoautoria()
	{
		Conexao objConexao = new Conexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = objConexao.getConn().prepareStatement("SELECT DISTINCT id_docente, id_coautor, nome_coautor, nome_docente FROM coautor INNER JOIN producao ON id_coautor= id_coautor_producao INNER JOIN docente ON id_docente= id_docente_producao;");
			rs = stmt.executeQuery();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		ArrayList<RedeCoautoria> alRede = new ArrayList<RedeCoautoria>();
		try
		{
			while(rs.next())
			{
				RedeCoautoria rede = new RedeCoautoria();
				rede.setCoautor(rs.getString("nome_coautor"));
				rede.setIdCoautor(rs.getInt("id_coautor"));
				rede.setIdDocente(rs.getInt("id_docente"));
				rede.setDocente(rs.getString("nome_docente"));
				
				alRede.add(rede);
			}
			
			rs.close();
			stmt.close();
			objConexao.getConn().close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return alRede;
	}
	
	public ArrayList<RedeCoautoria> coautoresRede()
	{
		Conexao objConexao = new Conexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = objConexao.getConn().prepareStatement("SELECT DISTINCT id_coautor, nome_coautor FROM coautor INNER JOIN producao ON id_coautor= id_coautor_producao INNER JOIN docente ON id_docente= id_docente_producao ORDER BY id_coautor ASC");
			rs = stmt.executeQuery();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		ArrayList<RedeCoautoria> alRede = new ArrayList<RedeCoautoria>();
		try
		{
			while(rs.next())
			{
				RedeCoautoria rede = new RedeCoautoria();
				rede.setCoautor(rs.getString("nome_coautor"));
				rede.setIdCoautor(rs.getInt("id_coautor"));
				
				alRede.add(rede);
			}
			
			rs.close();
			stmt.close();
			objConexao.getConn().close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return alRede;
	}
	
	public int redeIdDocente(String nome)
	{
		Conexao objConexao = new Conexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id=0;
		
		try
		{
			stmt = objConexao.getConn().prepareStatement("SELECT id_docente FROM docente INNER JOIN coautor Where nome_docente=?");
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
				id = rs.getInt("id_docente");

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
        
        public int redeDocenteCoautor(String nome)
	{
		Conexao objConexao = new Conexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int id=0;
		
		try
		{
			stmt = objConexao.getConn().prepareStatement("SELECT id_docente, id_coautor FROM docente INNER JOIN coautor Where nome_docente=? or nome_coautor=?");
			stmt.setString(1, nome);
                        stmt.setString(2, nome);
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
				id = rs.getInt("id_docente");
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
}
