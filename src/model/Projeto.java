package model;

public class Projeto {
	
	private String tipo; // Projeto de pesquisa ou projeto de desenvolvimento
	private String titulo; // nome do projeto
	private String descricao;
	private int anoInicio;
	private int anoFim;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getAnoInicio() {
		return anoInicio;
	}
	public void setAnoInicio(int anoInicio) {
		this.anoInicio = anoInicio;
	}
	public int getAnoFim() {
		return anoFim;
	}
	public void setAnoFim(int anoFim) {
		this.anoFim = anoFim;
	}
	
	// OBESERVA��ES
	// private String situacao; // talvez seja necess�rio (em andamento, finalizado, etc)
	// Financiadores? Necess�rio? Criar uma classe? 
	// Alunos envolvidos???? Necess�rio?
	
	
	// INTEGRANTES
	// Os integrantes do projetos s�o pertencentes da classe docente
	
	

}
