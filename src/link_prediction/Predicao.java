/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package link_prediction;

import static java.lang.Math.log10;
import java.util.ArrayList;
import java.util.Iterator;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;

/**
 *
 * @author Byzantine
 */
public class Predicao {
    
    private String nomeAutor;
    private String nomePredicao;
    private int cn;
    private double aa;
    private double ra;

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getNomePredicao() {
        return nomePredicao;
    }

    public void setNomePredicao(String nomePredicao) {
        this.nomePredicao = nomePredicao;
    }

    public int getCn() {
        return cn;
    }

    public void setCn(int cn) {
        this.cn = cn;
    }

    public double getAa() {
        return aa;
    }

    public void setAa(double aa) {
        this.aa = aa;
    }

    public double getRa() {
        return ra;
    }

    public void setRa(double ra) {
        this.ra = ra;
    }
    
    
    public int idDocenteNaRede(String nome)
    {
        Graph graph = grafoRede();
                
                Iterator<Node> it = graph.nodes();
                Node nodeAux;
                int id=0;
                
                while(it.hasNext())
                {
                    nodeAux = it.next();
                    if(nodeAux.get("name").equals(nome)){break;}
                    else{id++;}
                }
                return id;
    }
    
    public Graph grafoRede()
    {
        Graph graph = null;
		try {
			graph = new GraphMLReader().readGraph("RedeDeCoautoria.xml");
		} catch (DataIOException e) {
			System.err.println("Error loading graph. Exiting...");
			System.exit(1);
		}
        return graph;
    }
    
    public ArrayList<Predicao> predicao(String nome){
        int z;
        double auxP=0;
        VizinhosEmComum vc = new VizinhosEmComum();
        ArrayList<VizinhosEmComum> alVC = vc.numeroVizinhosComum(nome);
        ArrayList<Predicao> alP = new ArrayList<Predicao>(); 
        Predicao p = new Predicao();
        Graph graph = p.grafoRede();
        
        //int auxAlVC=1;

        for(int i=1; i<alVC.size();i++){
            //alVC.get(i).getNomePredicao();
            z=0;
            p = new Predicao();
            p.setCn(alVC.get(i).getnVC());
            
            for(int j=0; j<alVC.get(i).getNomesVizinhos().size();j++){
                Node vizinho = graph.getNode(p.idDocenteNaRede(alVC.get(i).getNomesVizinhos().get(j).toString()));
                Iterator<Node> it = vizinho.neighbors();
                while(it.hasNext()){
                    Node aux;
                    aux=it.next();
                    if(aux.get("name").equals("IFSP")){}else{z++;}
                }
            }
            auxP=1/(log10(z));
            p.setAa(auxP);
            p.setNomeAutor(alVC.get(i).getNomeAutor());
            p.setNomePredicao(alVC.get(i).getNomePredicao());
            auxP=1/(double)z;
            p.setRa(auxP);
            alP.add(p);
        }
        
        return alP;
    }
}
