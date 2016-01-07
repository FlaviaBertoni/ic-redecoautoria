package semantic_web;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import java.util.ArrayList;

public class ConsultasSPARQL {
    
    String file;
    
    public ConsultasSPARQL(String file)
    {
        this.file = file;
    }
    
    public ArrayList consultaDadosGerais(){
        
        ModelRDF model = new ModelRDF(file); 
        Model m = model.loadModel();
        //Consulta
        String queryString = "PREFIX bio:  <http://purl.org/vocab/bio/0.1/>\n" +
        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
        "SELECT ?URI ?Nome ?Resumo\n" +
        "	WHERE { ?URI foaf:name ?Nome .\n" +
        "?URI bio:biography ?Resumo}";
	
        Query query = QueryFactory.create(queryString);

        QueryExecution qe = QueryExecutionFactory.create(query, m);
        ResultSet results = qe.execSelect();
        
        ArrayList al = new ArrayList();
        
        while(results.hasNext())
        {
            QuerySolution qs = results.next();
            al.add(qs.get("URI").toString());
            al.add(qs.get("Nome").toString());
            al.add(qs.get("Resumo").toString());
        }
        
	qe.close();
        
        return al;
    }
    
    public ArrayList consultaProducao(String pesquisa){
        ModelRDF model = new ModelRDF(file); 
        Model m = model.loadModel();
        //Consulta
        String queryString="";
        if(pesquisa.equals("")){
        queryString = "PREFIX bio:  <http://purl.org/vocab/bio/0.1/>\n" +
        "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
        "SELECT ?Article ?Title\n" +
        "	WHERE { ?Article dc:title ?Title }";
        }else{
            queryString = "PREFIX bio:  <http://purl.org/vocab/bio/0.1/>\n" +
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
            "SELECT ?Article ?Title\n" +
            "	WHERE { ?Article dc:title ?Title ."+
            "FILTER( CONTAINS( UCASE(str(?Title)), \""+pesquisa+"\") )\n" +
            "}";
        }
	
        Query query = QueryFactory.create(queryString);

        QueryExecution qe = QueryExecutionFactory.create(query, m);
        ResultSet results = qe.execSelect();

	ArrayList al = new ArrayList();
        
        while(results.hasNext())
        {
            QuerySolution qs = results.next();
            al.add(qs.get("Title").toString());
        }
        
	qe.close();
        
        return al;
    }
    
    public ArrayList consultaAutoresProducao(String pesquisa){
        
        ModelRDF model = new ModelRDF(file); 
        Model m = model.loadModel();
        //Consulta
        String queryString="";
        if(pesquisa.equals("")){
        queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
        "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
        "PREFIX bibo: <http://purl.org/ontology/bibo/>\n" +
        "         SELECT *\n" +
        "          	WHERE {?resource dc:title ?title .\n" +
        "?resource dc:creator ?creator .\n" +
        "?creator foaf:name ?name}";
        }else{
            queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
            "PREFIX bibo: <http://purl.org/ontology/bibo/>\n" +
            "         SELECT *\n" +
            "          	WHERE {?resource dc:title ?title .\n" +
            "?resource dc:creator ?creator .\n" +
            "?creator foaf:name ?name ."+
            "FILTER( CONTAINS( UCASE(str(?title)), \""+pesquisa+"\") )\n" +
            "}";
        }
	
        Query query = QueryFactory.create(queryString);

        QueryExecution qe = QueryExecutionFactory.create(query, m);
        ResultSet results = qe.execSelect();
        
	ArrayList al = new ArrayList();
        
        while(results.hasNext())
        {
            QuerySolution qs = results.next();
            al.add(qs.get("resource").toString());
            al.add(qs.get("title").toString());
            al.add(qs.get("creator").toString());
            al.add(qs.get("name").toString());
        }
        
	qe.close();
        
        return al;
    }   
}
