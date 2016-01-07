package model;

public class RedeCoautoria {
	
	private String docente;
	private String relacionamento;
	private String coautor;
	private int idDocente, idCoautor;
	
	public String getDocente() {
		return docente;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public String getRelacionamento() {
		return relacionamento;
	}
	public void setRelacionamento(String relacionamento) {
		this.relacionamento = relacionamento;
	}
	public String getCoautor() {
		return coautor;
	}
	public void setCoautor(String coautor) {
		this.coautor = coautor;
	}
	public int getIdDocente() {
		return idDocente;
	}
	public void setIdDocente(int idDocente) {
		this.idDocente = idDocente;
	}
	public int getIdCoautor() {
		return idCoautor;
	}
	public void setIdCoautor(int idCoautor) {
		this.idCoautor = idCoautor;
	}
}
