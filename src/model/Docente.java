package model;

public class Docente {
	
	private int id;
	private int idDC;
	private String nomeCompleto;
	private String enderecoProfissional;
	private String resumo;
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getEnderecoProfissional() {
		return enderecoProfissional;
	}
	public void setEnderecoProfissional(String enderecoProfissional) {
		this.enderecoProfissional = enderecoProfissional;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdDC() {
		return idDC;
	}
	public void setIdDC(int idDC) {
		this.idDC = idDC;
	}
	
}