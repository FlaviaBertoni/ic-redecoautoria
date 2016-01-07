package controller;

import DAO.RedeCoautoriaDAO;
import Parse.LeitorXML;
import grafo.Grafo;
import grafo.VisualizadorGrafo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import link_prediction.Predicao;
import link_prediction.VizinhosEmComum;
import org.xml.sax.SAXException;
import semantic_web.ConsultasSPARQL;
import prefuse.Display;

public class MetodosRede {
       
    VisualizadorGrafo vg = new VisualizadorGrafo();
	
        public void criarRede()
	{
		new Grafo("RedeDeCoautoria.xml");
	}
	
	public Display mostrarRede()
	{
                return vg.GrafoPrefuse();
	}
        
        public String predictionLink(String nome)
        {
           String resultado="\n";
           VizinhosEmComum vc = new VizinhosEmComum();
           //pesquisa DAO, para ver se existe o nome no banco
           RedeCoautoriaDAO buscaAutor = new RedeCoautoriaDAO();
           if(buscaAutor.redeDocenteCoautor(nome)==0){resultado = "Docente não encontrado.";
           }else if(vc.numeroVizinhosComum(nome).size()==1){ resultado="Nenhuma sugestão de parceria.";
           }else{
               Predicao objP = new Predicao();
               for(Predicao p: objP.predicao(nome)){
                   resultado = resultado+"  "+p.getNomePredicao()+"\n   AA = "+p.getAa()+"\n   RA = "+p.getRa()+"\n   CN = "+p.getCn()+"\n\n";
               }
           }
           
           return resultado;
        }
        
         public void extrairCurrriculo(String nomeArquivo){
            LeitorXML leitor = new LeitorXML();    
            try {
                 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                 DocumentBuilder db;
                db = dbf.newDocumentBuilder(); 
                leitor.setDoc(db.parse(nomeArquivo));
                // Chama o metodo que executa a leitura XML 
                leitor.leituraDocentesXML();
                leitor.leituraFormacaoXML();
                leitor.leituraAreaAtuacao();
                leitor.leituraProducaoBibliograficaXML();
                
                JOptionPane.showMessageDialog(null, "Currículo Extraído com Sucesso!");
            } catch (SAXException | IOException | ParserConfigurationException ex) {
                Logger.getLogger(MetodosRede.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
         public String nomeFile(String docente){
             
             String file="";
             if(docente.equals("GIANCOLI, A. P. M.")){file="curriculo1_output.xml";}
             if(docente.equals("LEME, A.")){ file = "curriculo2_output.xml";}
             if(docente.equals("PANHAN, A. M.")){ file = "curriculo3_output.xml";}
             if(docente.equals("PEDROSA, B. M.")){ file = "curriculo4_output.xml";}
             if(docente.equals("LIMA, C. A. S.")){ file = "curriculo5_output.xml";}
             if(docente.equals("SANTOS, C. E.")){ file = "curriculo6_output.xml";}
             if(docente.equals("OLIVEIRA, C. C.")){ file = "curriculo7_output.xml";}
             if(docente.equals("SILVA, E. A. A.")){ file = "curriculo8_output.xml";}
             if(docente.equals("BRANDI, L. S. N.")){ file = "curriculo9_output.xml";}
             if(docente.equals("DE PAULA, L. B.")){ file = "curriculo10_output.xml";}
             if(docente.equals("VALLE, L. A. C.")){ file = "curriculo11_output.xml";}
             if(docente.equals("CAVALCANTE FILHO, R. S.")){ file = "curriculo12_output.xml";}
             if(docente.equals("SOUZA, T. P. C.")){ file = "curriculo13_output.xml";}
             if(docente.equals("VENDRAMEL, W.")){ file = "curriculo14_output.xml";}
             return file;
         }
         
         public String pesquisaSPAQLIFSPBRA(String docente, String tipo, String pesquisa)
         {
             String resultado;
             ConsultasSPARQL cSPARQL = new ConsultasSPARQL(nomeFile(docente));
             ArrayList alAux = new ArrayList();
             alAux = cSPARQL.consultaDadosGerais();
             resultado = "-------------> DADOS GERAIS \n\n"+"*URI (foaf:person)\n"+alAux.get(0).toString()
                +"\n*Nome (foaf:name)\n"+alAux.get(1).toString()
                +"\n*Resumo em Português (bio:biography)\n"+alAux.get(2)
                +"\n*Resumo em Inglês (bio:biography)\n"+alAux.get(5); 
             
             if(tipo.equals("Produções")){
                 alAux = cSPARQL.consultaProducao(pesquisa);
                 String aux="";
                 for(int i=0; i<alAux.size();i++){
                     aux=aux+"\n"+alAux.get(i).toString();
                 }
                 resultado = resultado+"\n\n-------------> RESULTADO DA PESQUISA"+"\n\n*Título da Produção (dc:Title)\n"+aux;
                }else if(tipo.equals("Autores por Produção")){
                 alAux = cSPARQL.consultaAutoresProducao(pesquisa);
                 String aux="\n";
                 int cab=0;
                 ArrayList cabecario = new ArrayList();
                 cabecario.add("\n*URI (rdf:resoource)\n");
                 cabecario.add("\n*Título da Produção (dc:Title)\n");
                 cabecario.add("\n*URI Autor (dc:creator)\n");
                 cabecario.add("\n*Nome do Autor (foaf:name)\n");
                 
                 for(int i=0; i<alAux.size();i++)
                 {
                    if(cab>3){cab=0; aux = aux + "\n";}
                    aux = aux + cabecario.get(cab).toString() + alAux.get(i).toString();
                    cab++;
                 }

                resultado = resultado+"\n\n-------------> RESULTADO DA PESQUISA"+aux;
             }
             
             return resultado;
         }
         
         public String nodeSelecionado(){return vg.getNodeSelecionado();}
}
