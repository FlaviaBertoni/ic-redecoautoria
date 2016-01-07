/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic_web;

import com.hp.hpl.jena.util.FileManager;
import org.apache.jena.iri.impl.Main;
import com.hp.hpl.jena.rdf.model.Model;

/**
 *
 * @author Byzantine
 */
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
