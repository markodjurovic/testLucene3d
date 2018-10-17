/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lucene3dtest;

import java.io.IOException;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

/**
 *
 * @author mdjurovi
 */
public class MainClass {
  private static final Directory indexDir = new RAMDirectory();
  
  public static void main(String[] args) throws IOException{
    IndexWriter writer = new IndexWriter(indexDir, new IndexWriterConfig(new WhitespaceAnalyzer()).setOpenMode(IndexWriterConfig.OpenMode.CREATE));
    
    //add first city
    Document doc = new Document();    
    doc.add(new StoredField("name","Kraljevo"));
    //Index data as a LatLon Point
    doc.add(new DoublePoint("location", 6.0,79.0,0));
    //Index Altitude as a FloatPoint. An One-Dimensional Point
    doc.add(new FloatPoint("altitude",22));
    //Write the document into Index
    writer.addDocument(doc);
    
    //add second city
    doc = new Document();    
    doc.add(new StoredField("name","Beograd"));
    //Index data as a LatLon Point
    doc.add(new DoublePoint("location", 6.0,79.001,0));
    //Index Altitude as a FloatPoint. An One-Dimensional Point
    doc.add(new FloatPoint("altitude",10));
    //Write the document into Index
    writer.addDocument(doc);
   
    //add 3D point
    doc = new Document();    
    doc.add(new StoredField("name","Mladenovac"));
    //Index data as a LatLon Point
    doc.add(new DoublePoint("location", 6.0,79.0007,15));
    //Index Altitude as a FloatPoint. An One-Dimensional Point
    doc.add(new FloatPoint("altitude",15));
    //Write the document into Index
    writer.addDocument(doc);
    
    double[] lower = new double[3]; lower[0] = 5.99; lower[1] = 79.0; lower[2] = 0.;
    double[] upper = new double[3]; upper[0] = 6.01; upper[1] = 79.01; upper[2] = 22.;
    Query q = DoublePoint.newRangeQuery("location", lower, upper);
    IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(writer));
        
    
    TopDocs topDocs = searcher.search(q, 100000);
    System.out.println("Num of hits: " + topDocs.totalHits);
  }
}
