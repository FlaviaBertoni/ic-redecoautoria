package grafo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorGraphXML {
	
	public static StringBuffer arquivo = new StringBuffer(
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!--  An excerpt of an egocentric social network  -->\n<graphml xmlns=\"").
            append("http://graphml.graphdrawing.org/xmlns\">\n\t<graph edgedefault=\"undirected\">\n\n\t\t<!-- data schema -->\n\t\t<key id=\"name\" for=\"node\"").
            append(" attr.name=\"name\" attr.type=\"string\"/>\n\n\t\t<!-- nodes -->  ");
	
	public static void generateNodes(int userId, String name){

        arquivo.append("\n\t\t<node id=\"").append(userId).append("\">\n\t\t\t").append("<data key=\"name\">").append(name).append("</data>\n\t\t</node>");

	}
	
	public static void generateEdges(long idSource, int idTarget){

            arquivo.append("\n\t\t<edge source=\"").append(idSource).append("\" target=\"").append(idTarget).append("\"></edge>\n\t\t\t");

	}
	
	public static void fechaArquivo(){

		arquivo.append("\n\n\t</graph>\n</graphml>");

	}
	
	public static void salvarXML(String path)
    {
		try {

            BufferedWriter out = new BufferedWriter(new FileWriter(path));

            out.write(arquivo.toString());

            out.flush();

            out.close();

	    } catch (IOException e) {
	
	            e.printStackTrace();
	
	            System.err.println("Erro ao salvar arquivo...Saindo");
	
	            System.exit(0);
	
	    }
    }

}
