package Parse;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import model.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import DAO.AreaConhecimentoDAO;
import DAO.CoautorDAO;
import DAO.DocenteDAO;
import DAO.FormacaoDAO;
import DAO.ProducaoDAO;


public class LeitorXML {

	static String nome;
	private Document doc;
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	
	private String autor;

	public void leituraDocentesXML() throws ParserConfigurationException, SAXException, IOException
	{
		DocenteDAO docenteDAO = new DocenteDAO();
		Docente docente = new Docente();
			
		// Verifica o nome da raiz
		Element raiz = doc.getDocumentElement();
		raiz.getElementsByTagName(raiz.getNodeName());

		// Nome Completo do Docente
		NodeList listaDadosGeraisDocente = doc.getElementsByTagName("DADOS-GERAIS");
                Node nodeDadosGerais = listaDadosGeraisDocente.item(0);

        if (nodeDadosGerais.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nodeDadosGerais;
            docente.setNomeCompleto(eElement.getAttribute("NOME-COMPLETO"));
        }
        
        autor = docente.getNomeCompleto();
       // Endereço Profissional
        NodeList listaEndenrecoProfissional = doc.getElementsByTagName("ENDERECO-PROFISSIONAL");
        Node nodeEnderecoProfissional = listaEndenrecoProfissional.item(0);

        if (nodeEnderecoProfissional.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nodeEnderecoProfissional;
            docente.setEnderecoProfissional(eElement.getAttribute("NOME-INSTITUICAO-EMPRESA"));
        }
        
        // Resumo
        NodeList listaResumo = doc.getElementsByTagName("RESUMO-CV");
        Node nodeResumo = listaResumo.item(0);

        if (nodeResumo.getNodeType() == Node.ELEMENT_NODE) {            
            Element eElement = (Element) nodeResumo;
            docente.setResumo(eElement.getAttribute("TEXTO-RESUMO-CV-RH"));
        }
		
        docenteDAO.insereDocente(docente);
	}
	
	//______________________________________________________________________________________________
	
	public void leituraFormacaoXML() throws ParserConfigurationException, SAXException, IOException{

		// Verifica o nome da raiz
		Element raiz = doc.getDocumentElement();
		raiz.getElementsByTagName(raiz.getNodeName());
		
		// ----------------------------------------------------------------------------------------
		// 
		NodeList listaFormacao = doc.getElementsByTagName("FORMACAO-ACADEMICA-TITULACAO");
        NodeList childFormacao = listaFormacao.item(0).getChildNodes();
		// -------------------------------------------------------------------
        
        //Vai percorrer todos as tags filhas, uma por uma, e analisar qual delas é
        for (int i = 0; i < childFormacao.getLength(); i++) {

        		Node nodeChild = childFormacao.item(i);
        		FormacaoAcademica formacao = new FormacaoAcademica();
              
                if (nodeChild.getNodeName().contains("CURSO-TECNICO-PROFISSIONALIZANTE")) {
                	formacao.setTitulacao("Curso Técnico Profissionalizante");
                	parseCourseFormacao(nodeChild, formacao);
                } 
                
                if (nodeChild.getNodeName().contains("GRADUACAO")) {
                	formacao.setTitulacao("Graduação");
                	parseCourseFormacao(nodeChild, formacao);
                } 
                
                if (nodeChild.getNodeName().contains("ESPECIALIZACAO")) {
                	formacao.setTitulacao("Especialização");
                	parseCourseFormacao(nodeChild, formacao);
                }
                
                if (nodeChild.getNodeName().contains("MESTRADO")) {
                	formacao.setTitulacao("Mestrado");;
                	parseCourseFormacao(nodeChild, formacao);
                }
                
                if (nodeChild.getNodeName().contains("MESTRADO-PROFISSIONALIZANTE")) {
                	formacao.setTitulacao("Mestrado Profissionalizante");
                	parseCourseFormacao(nodeChild, formacao);
                }
                
                if (nodeChild.getNodeName().contains("DOUTORADO")) {
                	formacao.setTitulacao("Doutorado");
                	parseCourseFormacao(nodeChild, formacao);
                }
                
                if (nodeChild.getNodeName().contains("POS-DOUTORADO")) {
                	formacao.setTitulacao("Pós-Doutorado");
                	parseCourseFormacao(nodeChild, formacao);
                }
                
                if (nodeChild.getNodeName().contains("LIVRE-DOCENCIA")) {
                	formacao.setTitulacao("Livre-docência"); 
                 	parseCourseFormacao(nodeChild, formacao);
                }
                          
        }
    }
	
	private void parseCourseFormacao(Node nodeChild, FormacaoAcademica formacao){
    	
		FormacaoDAO formacaoDAO = new FormacaoDAO();
		
		if (nodeChild.getNodeName().contains("LIVRE-DOCENCIA"))
		{
	        formacao.setCurso(nodeChild.getAttributes().getNamedItem("TITULO-DO-TRABALHO").getTextContent());
	    } else if (!nodeChild.getNodeName().contains("LIVRE-DOCENCIA")) {
	    	if(nodeChild.getAttributes().getNamedItem("NOME-CURSO")!=null){
	    		formacao.setCurso(nodeChild.getAttributes().getNamedItem("NOME-CURSO").getTextContent());
	    	}
	    }
	     
	     formacao.setInstituicao(nodeChild.getAttributes().getNamedItem("NOME-INSTITUICAO").getTextContent());
	     
	     NodeList maisInfoList = nodeChild.getChildNodes();
	    
	    for (int j = 0; j < maisInfoList.getLength(); j++) {
	
	    Node childMaisInfo = maisInfoList.item(j);
	
	    if (childMaisInfo.getNodeName().contains("PALAVRAS-CHAVE")) {
	
	    	formacao.setPalavraChave1(childMaisInfo.getAttributes().getNamedItem("PALAVRA-CHAVE-1").getTextContent());
	    	formacao.setPalavraChave2(childMaisInfo.getAttributes().getNamedItem("PALAVRA-CHAVE-2").getTextContent()); 	
	    	formacao.setPalavraChave3(childMaisInfo.getAttributes().getNamedItem("PALAVRA-CHAVE-3").getTextContent());
	    	formacao.setPalavraChave4(childMaisInfo.getAttributes().getNamedItem("PALAVRA-CHAVE-4").getTextContent());
	    	formacao.setPalavraChave5(childMaisInfo.getAttributes().getNamedItem("PALAVRA-CHAVE-5").getTextContent());
	    	formacao.setPalavraChave6(childMaisInfo.getAttributes().getNamedItem("PALAVRA-CHAVE-6").getTextContent());
	
	    }
	 }
	 formacaoDAO.insereFormacao(formacao);
  }
	
	public void leituraAreaAtuacao() throws ParserConfigurationException, SAXException, IOException
	{
		// Verifica o nome da raiz
		Element raiz = doc.getDocumentElement();
		raiz.getElementsByTagName(raiz.getNodeName());
				
		// ----------------------------------------------------------------------------------------
		// 
		NodeList listaArea = doc.getElementsByTagName("AREAS-DE-ATUACAO");
		NodeList childArea = listaArea.item(0).getChildNodes();
		// -------------------------------------------------------------------
		
		for (int i = 0; i < childArea.getLength(); i++) {

    		Node nodeChild = childArea.item(i);
    		
    		if (nodeChild.getNodeName().contains("AREA-DE-ATUACAO"))
    		{
    			AreaConhecimentoDAO areaDAO = new AreaConhecimentoDAO();
    			AreaConhecimento area = new AreaConhecimento();
    			area.setGrandeArea(nodeChild.getAttributes().getNamedItem("NOME-GRANDE-AREA-DO-CONHECIMENTO").getTextContent());
            	area.setSubArea(nodeChild.getAttributes().getNamedItem("NOME-DA-SUB-AREA-DO-CONHECIMENTO").getTextContent());  
            	area.setArea(nodeChild.getAttributes().getNamedItem("NOME-DA-AREA-DO-CONHECIMENTO").getTextContent());
            	area.setEspecialdiade(nodeChild.getAttributes().getNamedItem("NOME-DA-ESPECIALIDADE").getTextContent());
            	areaDAO.insereAreaConhecimento(area);
    		}
		}    
	}
	
	
	public void leituraProducaoBibliograficaXML() throws ParserConfigurationException, SAXException, IOException
	{
		artigoXML();
		trabalhoXML();
	}
	
	public void trabalhoXML()
	{
		ProducaoDAO producaoDAO = new ProducaoDAO();
		CoautorDAO coautorDAO = new CoautorDAO();
		
		// Verifica o nome da raiz
		Element raiz = doc.getDocumentElement();
		raiz.getElementsByTagName(raiz.getNodeName());
		
		NodeList listaProducoes = doc.getElementsByTagName("TRABALHO-EM-EVENTOS");
		
		for (int i=0; i <listaProducoes.getLength();i++)
		{
			Producao producao = new Producao();
			
			// ---------------------------------------------------------------------------------------------------
			// Titulo e ano
			NodeList listaArtigosPublicados = doc.getElementsByTagName("DADOS-BASICOS-DO-TRABALHO");
	        Node nodeArtigosPublicados = listaArtigosPublicados.item(i);

	        if (nodeArtigosPublicados.getNodeType() == Node.ELEMENT_NODE){
	            Element eElement = (Element) nodeArtigosPublicados;
	            producao.setTituloTrabalho(eElement.getAttribute("TITULO-DO-TRABALHO"));
	            producao.setAno(Integer.parseInt(eElement.getAttribute("ANO-DO-TRABALHO")));
	        }
	        
	        // ---------------------------------------------------------------------------------------------------
	        NodeList listDetalhamentoArtigos = doc.getElementsByTagName("DETALHAMENTO-DO-TRABALHO");
	        Node nodeDetalhamento = listDetalhamentoArtigos.item(i);

	        if (nodeDetalhamento.getNodeType() == Node.ELEMENT_NODE){
	            Element eElement = (Element) nodeDetalhamento;
	            producao.setDetalhamento(eElement.getAttribute("TITULO-DOS-ANAIS-OU-PROCEEDINGS"));
	        }

	        //COAUTORES --------------------------------------------------------
	        NodeList childArtigos = listaProducoes.item(i).getChildNodes();

	        for (int j = 0; j < childArtigos.getLength(); j++) {

	            Coautor coautor = new Coautor();
	            
	            Node nodeChild = childArtigos.item(j);

	            if (nodeChild.getNodeName().contains("AUTORES")) {              
	            	
	            	if(nodeChild.getAttributes().getNamedItem("NOME-COMPLETO-DO-AUTOR").getTextContent().equals(autor)){}
	            	else
	            	{
		            	nome = nodeChild.getAttributes().getNamedItem("NOME-COMPLETO-DO-AUTOR").getTextContent();
		            	coautor.setNome(nome);
		            	
		            	if(coautorDAO.verificaCoautor(nome) != 0){}
		            	else
		            	{
		            		coautorDAO.insereCoautor(coautor);
		            	}
	            	}
	            }                 
	        }
	        producaoDAO.insereProducao(producao, coautorDAO.verificaCoautor(nome));
		}
	}
	
	public void artigoXML()
	{
		ProducaoDAO producaoDAO = new ProducaoDAO();
		CoautorDAO coautorDAO = new CoautorDAO();
		
		// Verifica o nome da raiz
		Element raiz = doc.getDocumentElement();
		raiz.getElementsByTagName(raiz.getNodeName());
		
		NodeList listaProducoes = doc.getElementsByTagName("ARTIGO-PUBLICADO");
		
		for (int i=0; i <listaProducoes.getLength();i++)
		{
			Producao producao = new Producao();
			
			// ---------------------------------------------------------------------------------------------------
			// Titulo e ano
			NodeList listaArtigosPublicados = doc.getElementsByTagName("DADOS-BASICOS-DO-ARTIGO");
	        Node nodeArtigosPublicados = listaArtigosPublicados.item(i);

	        if (nodeArtigosPublicados.getNodeType() == Node.ELEMENT_NODE){
	            Element eElement = (Element) nodeArtigosPublicados;
	            producao.setTituloTrabalho(eElement.getAttribute("TITULO-DO-ARTIGO"));
	            producao.setAno(Integer.parseInt(eElement.getAttribute("ANO-DO-ARTIGO")));
	        }
	        
	        // ---------------------------------------------------------------------------------------------------
	        NodeList listDetalhamentoArtigos = doc.getElementsByTagName("DETALHAMENTO-DO-ARTIGO");
	        Node nodeDetalhamento = listDetalhamentoArtigos.item(i);

	        if (nodeDetalhamento.getNodeType() == Node.ELEMENT_NODE){
	            Element eElement = (Element) nodeDetalhamento;
	            producao.setDetalhamento(eElement.getAttribute("TITULO-DO-PERIODICO-OU-REVISTA"));
	        }
	        
	        
	        //COAUTORES --------------------------------------------------------
	        NodeList childArtigos = listaProducoes.item(i).getChildNodes();

	        for (int j = 0; j < childArtigos.getLength(); j++) {

	            Coautor coautor = new Coautor();
	            
	            Node nodeChild = childArtigos.item(j);

	            if (nodeChild.getNodeName().contains("AUTORES")) {              
	            	
	            	if(nodeChild.getAttributes().getNamedItem("NOME-COMPLETO-DO-AUTOR").getTextContent().equals(autor)){}
	            	else
	            	{
		            	nome = nodeChild.getAttributes().getNamedItem("NOME-COMPLETO-DO-AUTOR").getTextContent();
		            	coautor.setNome(nome);
		            	
		            	if(coautorDAO.verificaCoautor(nome) != 0){}
		            	else
		            	{
		            		coautorDAO.insereCoautor(coautor);
		            	}
	            	}
	            }                 
	        }
	        producaoDAO.insereProducao(producao, coautorDAO.verificaCoautor(nome));
		}
	}
	
 }
    
