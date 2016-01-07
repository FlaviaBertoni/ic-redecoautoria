package semantic_web;

import com.hp.hpl.jena.util.FileManager;
import org.apache.jena.iri.impl.Main;
import com.hp.hpl.jena.rdf.model.Model;

public class ModelRDF {
    
     protected String file = "nomeRDF.xml";
     
     public ModelRDF(String fileModel){
         file = fileModel;
     }
     
     public Model loadModel(){
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        Model model = FileManager.get().loadModel(file);
        return model;
     } 
}
