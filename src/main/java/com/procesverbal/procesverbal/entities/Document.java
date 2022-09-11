package com.procesverbal.procesverbal.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private   String title;
    private   String path;
    private Timestamp date;

    public Document(String title, String path, Timestamp date) {
        this.title = title;
        this.path = path;
        this.date = date;
    }



}
