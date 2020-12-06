/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author alex_
 */
public class ParseDocuments {
    
    private Scanner sc;
    
    public void parseFile(String fileurl) throws FileNotFoundException, SolrServerException, IOException
    {
        sc = new Scanner(new File(fileurl));
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/Practica1").build();
        while(sc.hasNextLine())
        {
            SolrInputDocument doc = new SolrInputDocument();
            String line = sc.nextLine();
            String [] id = line.split(" ");
            doc.addField("id", id[id.length-1]);
            line = sc.nextLine();
            String titulo = "";
            while(!line.isEmpty())
            {
                titulo = titulo + " " + line;
                line = sc.nextLine();   
            }
            doc.addField("title", titulo);           
            line = sc.nextLine();            
            String texto = "";
            while(!line.contains("**********"))
            {
                texto = texto + " " + line;
                line = sc.nextLine();                
            }            
            doc.addField("texto", texto);    
            solr.add(doc);
        }
        solr.commit();
        sc.close();
    }   
}
