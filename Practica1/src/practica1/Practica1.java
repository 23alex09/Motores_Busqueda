/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 *
 * @author alex_
 */
public class Practica1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SolrServerException, IOException {
        
        ParseDocuments pd = new ParseDocuments();
        ParseQueries pq = new ParseQueries("ficheros_lisa/trec_top_file.txt");
        
        //pd.parseFile("ficheros_lisa/LISA0.001");
        pq.parseFile("ficheros_lisa/LISA.QUE");
        //pq.crearTrec();
        
        
        //olrDocumentList [] documentos = pq.getDocumentos();
        
     
    }
    
}
