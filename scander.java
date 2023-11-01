
package com.mycompany.pdfextractor;
//import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;

public class pdfExtractor {
   public static void main(String[] args) throws FileNotFoundException, IOException {
        
     
        File file = new File("C:\\Users\\Özlem GÜL\\Desktop\\sample\\yuksekogretim-kurulu-transkript-belgesi-sorgulama.pdf"); 
        FileInputStream fis = new FileInputStream(file);
        
        PDDocument pdfDDocument = PDDocument.load(fis);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String docText = pdfTextStripper.getText(pdfDDocument);
        System.out.println(docText);
        System.out.println(pdfDDocument.getPages().getCount());   
        
        pdfDDocument.close();
        fis.close();
        
        
       
       
       
     
    
    }
    
}
