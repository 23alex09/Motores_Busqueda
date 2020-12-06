/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

/**
 *
 * @author alex_
 */
public class ParseQueries {
    
    private Scanner sc;
    private SolrDocumentList [] documentos;
    private File archivo;
    
    public ParseQueries(String ruta) throws IOException
    {
        archivo = new File(ruta);
       
        if(archivo.exists())
        {
            archivo.delete();
        }
        archivo.createNewFile();
        
    }
    
    public void parseFile(String fileurl) throws FileNotFoundException, SolrServerException, IOException
    {
        sc = new Scanner(new File(fileurl));
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/Practica1").build();
        int j = 1;
        FileWriter fichero = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(archivo);
        while(sc.hasNextLine())
        {
            String line = sc.nextLine();
            int idQ = Integer.parseInt(line);
            //System.out.println(idQ);
            line = sc.nextLine();
            String texto = line;
            while(!line.contains("#"))
            {
                line = sc.nextLine();
                texto = texto + " " +line;
            }

            String [] palabras = texto.split(" ");
            String consulta = "";
            for(int i = 0; i < 5; i++)
            {
                consulta = consulta + palabras[i] + " ";
            }
            //System.out.println(consulta);
            SolrQuery query = new SolrQuery();
            query.setQuery("texto:" + consulta);
            query.addField("id");
            query.addField("score");
            query.setFields("id, score");     
            QueryResponse rsp = solr.query(query);
            SolrDocumentList docs = rsp.getResults();
            
            //System.out.println("Query: " + j);
            for (int i = 0; i < docs.size(); ++i) {
                System.out.println(docs.get(i));
                String idDocumento = (String) docs.get(i).getFieldValue("id");
                int idConsulta = j;
                float score = (float) docs.get(i).getFieldValue("score");
                fichero.write(idConsulta + " Q0 " + idDocumento + " " + i + " " + score + " ETSI\n");
            }
            
            //documentos[j] = docs;
            j++;
        }
        fichero.close();
        
    }
    /*
    public void crearTrec() throws IOException
    {
        FileWriter fichero = new FileWriter(archivo);
        PrintWriter pw = new PrintWriter(archivo);
        for(int i = 0; i < documentos.length; i++)
        {
            SolrDocumentList docs = documentos[i];
            for (int j = 0; j < docs.size(); ++j) {
                //System.out.println(docs.get(i));
                String idDocumento = (String) docs.get(j).getFieldValue("id");
                int idConsulta = j;
                float score = (float) docs.get(j).getFieldValue("score");
                fichero.write(idConsulta + " Q0 " + idDocumento + " " + j + " " + score + " ETSI\n");
            }
        }
        fichero.close();
    }*/

    public SolrDocumentList[] getDocumentos() {
        return documentos;
    }
    
    
}
