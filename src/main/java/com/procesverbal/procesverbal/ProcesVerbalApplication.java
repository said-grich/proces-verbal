package com.procesverbal.procesverbal;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication(scanBasePackages={"com.procesverbal.procesverbal"})
public class ProcesVerbalApplication {

   
   public static void main(String[] args) {
//       XWPFDocument document= new XWPFDocument();
//       try {
//           FileOutputStream out = new FileOutputStream(new File("Javatpoint.docx"));
//           // Creating Table
//           XWPFTable tab = document.createTable();
//           XWPFTableRow row = tab.getRow(0); // First row
//           // Columns
//           row.getCell(0).setText("Sl. No.");
//           row.addNewTableCell().setText("Name");
//           row.addNewTableCell().setText("Email");
//           row = tab.createRow(); // Second Row
//           row.getCell(0).setText("1.");
//           row.getCell(1).setText("Irfan");
//           row.getCell(2).setText("irfan@gmail.com");
//           row = tab.createRow(); // Third Row
//           row.getCell(0).setText("2.");
//           row.getCell(1).setText("Mohan");
//           row.getCell(2).setText("mohan@gmail.com");
//           document.write(out);
//       }catch(Exception e) {
//           System.out.println(e);
//       }


      SpringApplication.run(ProcesVerbalApplication.class, args);
    }

}
