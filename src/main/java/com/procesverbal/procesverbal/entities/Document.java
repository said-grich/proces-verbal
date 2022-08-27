package com.procesverbal.procesverbal.entities;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private   String title;
    private   String path;
    private Timestamp timestamp;
}
