package DAO;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexao.Conexao;
import model.FormacaoAcademica;

public class FormacaoDAO {
	
	public void insereFormacao(FormacaoAcademica formacao)
	{
		DocenteDAO docenteDAO = new DocenteDAO();
		Conexao objConexao = new Conexao();
		
		String sql="INSERT INTO rede.formacao(curso_formacao, nome_formacao, instituicao_formacao, palavraChave_1_formacao, palavraChave_2_formacao, palavraChave_3_formacao, palavraChave_4_formacao, palavraChave_5_formacao, palavraChave_6_formacao, id_docente_formacao) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		try
		{
			stmt = objConexao.getConn().prepareStatement(sql);
			stmt.setString(1, formacao.getCurso());
			stmt.setString(2, formacao.getTitulacao());
			stmt.setString(3, formacao.getInstituicao());
			stmt.setString(4, formacao.getPalavraChave1());
			stmt.setString(5, formacao.getPalavraChave2());
			stmt.setString(6, formacao.getPalavraChave3());
			stmt.setString(7, formacao.getPalavraChave4());
			stmt.setString(8, formacao.getPalavraChave5());
			stmt.setString(9, formacao.getPalavraChave6());
			stmt.setInt(10, docenteDAO.selecionaIdDocente());
			
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
