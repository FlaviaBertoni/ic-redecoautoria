package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.Conexao;
import model.Producao;

public class ProducaoDAO {
	
	public void insereProducao(Producao producao, int idCoautor)
	{
		int id;
		DocenteDAO docenteDAO = new DocenteDAO();
		CoautorDAO coautorDAO = new CoautorDAO();
		Conexao objConexao = new Conexao();
		
		if(idCoautor != 0){ id=idCoautor;}
		else{ id = coautorDAO.selecionaCoautor();}
		
		String sql="INSERT INTO rede.producao(titulo_producao, ano_producao, detalhamento_producao, id_docente_producao, id_coautor_producao) "
				+ "VALUES(?,?,?,?,?)";
		PreparedStatement stmt = null;
		try
		{
			stmt = objConexao.getConn().prepareStatement(sql);
			stmt.setString(1, producao.getTituloTrabalho());
			stmt.setInt(2, producao.getAno());
			stmt.setString(3, producao.getDetalhamento());
			stmt.setInt(4, docenteDAO.selecionaIdDocente());
			stmt.setInt(5, id);
			
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
