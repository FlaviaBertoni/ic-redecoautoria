package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.Conexao;
import model.AreaConhecimento;

public class AreaConhecimentoDAO {
	
	public void insereAreaConhecimento(AreaConhecimento area)
	{
		DocenteDAO docenteDAO = new DocenteDAO();
		Conexao objConexao = new Conexao();
		
		String sql="INSERT INTO rede.areaconhecimento(grande_areaConhecimento, sub_areaConhecimento, area_areaConhecimento, especialidade_areaConhecimento, id_docente_areaConhecimento) "
				+ "VALUES(?,?,?,?,?)";
		PreparedStatement stmt = null;
		try
		{
			stmt = objConexao.getConn().prepareStatement(sql);
			stmt.setString(1, area.getGrandeArea());
			stmt.setString(2, area.getSubArea());
			stmt.setString(3, area.getArea());
			stmt.setString(4, area.getEspecialdiade());
			stmt.setInt(5, docenteDAO.selecionaIdDocente());
			
			stmt.execute();
			stmt.close();
			objConexao.getConn().close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
