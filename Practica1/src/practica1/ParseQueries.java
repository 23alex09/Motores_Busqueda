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
    public void parseFile(String fileurl) throws FileNotFoundException, SolrServerException, IOException
    {
        sc = new Scanner(new File(fileurl));
        HttpSolrClient solr = new HttpSolrClient.Builder("http://localhost:8983/solr/Practica1").build();
        int j = 0;
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
            for(int i = 0; i<5; i++)
            {
                consulta = consulta + palabras[i] + " ";
            }
            //System.out.println(consulta);
            SolrQuery query = new SolrQuery();
            query.setQuery("text:" + consulta);
            query.addField("id");
            query.addField("score");
            QueryResponse rsp = solr.query(query);
            SolrDocumentList docs = rsp.getResults();
            System.out.println("Query: " + j);
            for (int i = 0; i < docs.size(); ++i) {
                System.out.println(docs.get(i));
            }
            //documentos[j] = docs;
            j++;
        }
    }

    public SolrDocumentList[] getDocumentos() {
        return documentos;
    }
    
    
}
