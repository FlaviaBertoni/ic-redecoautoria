package grafo;

import java.text.Normalizer;
import java.util.ArrayList;

import model.RedeCoautoria;
import DAO.RedeCoautoriaDAO;

public class Grafo {
	
	private int idRep=0;
	
	public Grafo(String nomeGrafo)
	{
		criarNos();
		criarArestas();
		criarArquivo(nomeGrafo);
	}
	  
	private void criarNos()
	{
		RedeCoautoriaDAO redeDAO = new RedeCoautoriaDAO();
		ArrayList<RedeCoautoria> alRede = redeDAO.redeSocialCoautoria();
		
		GeradorGraphXML.generateNodes(1000000000, "IFSP");
		
		for(RedeCoautoria rc: alRede)
		{
			if(idRep != rc.getIdDocente())
			{
				GeradorGraphXML.generateNodes(rc.getIdDocente(), removeAcentos(rc.getDocente()));
				idRep = rc.getIdDocente();
			}
		}
		
		ArrayList<RedeCoautoria> alCoautoresSemRep = redeDAO.coautoresRede();
		
		for(RedeCoautoria rc: alCoautoresSemRep)
		{
			if(redeDAO.redeIdDocente(rc.getCoautor())==0)
			{
				GeradorGraphXML.generateNodes(rc.getIdCoautor()*100, removeAcentos(rc.getCoautor()));
			}
		}
	}
	
	private void criarArestas()
	{
		RedeCoautoriaDAO redeDAO = new RedeCoautoriaDAO();
		ArrayList<RedeCoautoria> alRede = redeDAO.redeSocialCoautoria();
                ArrayList aux = new ArrayList();
                idRep=0;
		
		//Verificar a exist�ncia do coautor como docente, caso exista retornar o id do docente e fazer desta forma a aresta
		for(RedeCoautoria rc: alRede)
		{
                        boolean rep=false;
			if(redeDAO.redeIdDocente(rc.getCoautor()) == 0)
			{
                                GeradorGraphXML.generateEdges(rc.getIdDocente(), rc.getIdCoautor()*100);
			}else
			{
                            for(int i = 0; i <aux.size(); i++){
                                if(aux.get(i).toString().equals(""+rc.getIdDocente()+"-"+redeDAO.redeIdDocente(rc.getCoautor()))){rep=true;}
                            }
                            aux.add(""+rc.getIdDocente()+"-"+redeDAO.redeIdDocente(rc.getCoautor()));
                            aux.add(""+redeDAO.redeIdDocente(rc.getCoautor())+"-"+rc.getIdDocente());
                            if(rep==false){
                            GeradorGraphXML.generateEdges(rc.getIdDocente(), redeDAO.redeIdDocente(rc.getCoautor()));}
			}
                        
                     if(idRep != rc.getIdDocente())
                     {
			idRep = rc.getIdDocente();
                        GeradorGraphXML.generateEdges(rc.getIdDocente(), 1000000000);
                     }
		}
	}
	
	private void criarArquivo(String nomeDoGrafo) 
	{
        GeradorGraphXML.fechaArquivo();
        GeradorGraphXML.salvarXML(nomeDoGrafo);
	}
	
	public String removeAcentos(String str)
	{
		  str = Normalizer.normalize(str, Normalizer.Form.NFD);
		  str = str.replaceAll("[^\\p{ASCII}]", "");
		  return str;
	}
}
