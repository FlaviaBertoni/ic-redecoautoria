package link_prediction;
import java.util.ArrayList;
import java.util.Iterator;
import prefuse.data.Graph;
import prefuse.data.Node;

public class VizinhosEmComum {

    private String nomeAutor;
    private String nomePredicao;
    private int nVC;
    private ArrayList nomesVizinhos = new ArrayList();
    
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

    public int getnVC() {
        return nVC;
    }

    public void setnVC(int nVC) {
        this.nVC = nVC;
    }

    public ArrayList getNomesVizinhos() {
        return nomesVizinhos;
    }

    public void setNomesVizinhos(ArrayList nomesVizinhos) {
        this.nomesVizinhos = nomesVizinhos;
    }
    
    public ArrayList<VizinhosEmComum> numeroVizinhosComum(String nome)
    {
        Predicao p = new Predicao();
        Graph graph = p.grafoRede();
        int id = p.idDocenteNaRede(nome);
        int idAux=0;
        Node nDocente = graph.getNode(id);
        ArrayList<VizinhosEmComum> alVC = new ArrayList<VizinhosEmComum>();
        VizinhosEmComum vz = new VizinhosEmComum();
        vz.nVC=0;
        vz.nomeAutor="Nenhum";
        vz.nomePredicao="Nenhum";
        vz.nomesVizinhos.add("Nenhum");
        alVC.add(vz);

        Iterator iNosRede = graph.nodes();
        
        while(iNosRede.hasNext())
        {
            int n=0;
            iNosRede.next();
            Node nPredicao = graph.getNode(idAux);
            Iterator<Node> itPredicao = nPredicao.neighbors();
            vz = new VizinhosEmComum();
            while(itPredicao.hasNext()){
                Node vp = itPredicao.next();
                Iterator<Node> itDocente = nDocente.neighbors();
                while(itDocente.hasNext())
                { 
                    Node vd = itDocente.next();
                    if(vd.get("name").equals("IFSP")){}else{
                        if(vd==vp){
                            n++;
                            vz.nomesVizinhos.add(vd.get("name"));
                        }}
                }
            }

            if(idAux==0 || nPredicao==nDocente || n==0){}else{
            vz.nomePredicao = nPredicao.getString("name");
            vz.nomeAutor = nDocente.getString("name");
            vz.nVC = n;
            alVC.add(vz);}
            idAux++;
        }
        // Teste
        /*for(VizinhosEmComum vc: alVC){

            System.out.println(vc.nomePredicao);
            System.out.println(vc.nomeAutor);
            System.out.println(vc.nVC);
             for(int i=0; i<vc.nomesVizinhos.size();i++){
                 System.out.println(vc.nomesVizinhos.get(i));
             }
            System.out.println("______________________");
        }*/
        
        return alVC;
    } 
}
