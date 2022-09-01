package com.procesverbal.procesverbal;

import com.procesverbal.procesverbal.dto.CommissionMemberDto;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.ArrayList;
import java.util.List;

public abstract class AppString {
    public static String LOGO = "src/main/resources/static/images/logo.png";
    public static String UNDER_LINE = "src/main/resources/static/images/under-line.png";
    public static String OBJET_TEXT = "src/main/resources/static/text/headerText.txt";
    public static String GARAMOND_FONT = "Garamond";
    public static String TIMES_NEW_RAMAN_FONT = "Times New Roman";
    public static String UNIVERS_57_CONDEDSED = "Univers 57 Condensed";
    public static String COMMISSION_TEXT = "src/main/resources/static/text/commission.txt";
    public static String COMMISSION_TEXT_1 = "src/main/resources/static/text/commission1.txt";
    public static String BLACK_COLOR = "000000";
    public static int BORDER_SIZE = 12;
    public static List<String> COMMISSION_TAB_HEADER = new ArrayList<String>() {{
        add("Membres de la commission");
        add("Emargements");
    }};
    public static List<CommissionMemberDto> COMMISSION_FIX_MEMBER = new ArrayList<CommissionMemberDto>() {{
        add(new CommissionMemberDto("M.EL BERKAOUI AHMED", "Chef de service des affaires administratives et financière, des constructions, des équipements et des patrimoines", "PRESIDENT", ""));
        add(new CommissionMemberDto("M. ABBASSE ANNAYA", "Bureau des marchés", "MEMBRE", ""));
        add(new CommissionMemberDto("Mlle. ASSYA AIT ELMAATI", "Bureau des marchés", "MEMBRE", ""));
        add(new CommissionMemberDto("M. ABDERRAHMANE SOUFI", "Bureau de comptabilité", "MEMBRE", ""));
        add(new CommissionMemberDto("M.ABDELKARIM EL MALKI", "Bureau d’économie", "MEMBRE", ""));

    }};
    public static int CELL_HEIGHT_CONTENT = 1300;
    public static int CELL_HEIGHT_HEADER = 800;


    public static int getImageFormat(String imgFileName) {
        int format;
        if (imgFileName.endsWith(".emf"))
            format = XWPFDocument.PICTURE_TYPE_EMF;
        else if (imgFileName.endsWith(".wmf"))
            format = XWPFDocument.PICTURE_TYPE_WMF;
        else if (imgFileName.endsWith(".pict"))
            format = XWPFDocument.PICTURE_TYPE_PICT;
        else if (imgFileName.endsWith(".jpeg") || imgFileName.endsWith(".jpg"))
            format = XWPFDocument.PICTURE_TYPE_JPEG;
        else if (imgFileName.endsWith(".png"))
            format = XWPFDocument.PICTURE_TYPE_PNG;
        else if (imgFileName.endsWith(".dib"))
            format = XWPFDocument.PICTURE_TYPE_DIB;
        else if (imgFileName.endsWith(".gif"))
            format = XWPFDocument.PICTURE_TYPE_GIF;
        else if (imgFileName.endsWith(".tiff"))
            format = XWPFDocument.PICTURE_TYPE_TIFF;
        else if (imgFileName.endsWith(".eps"))
            format = XWPFDocument.PICTURE_TYPE_EPS;
        else if (imgFileName.endsWith(".bmp"))
            format = XWPFDocument.PICTURE_TYPE_BMP;
        else if (imgFileName.endsWith(".wpg"))
            format = XWPFDocument.PICTURE_TYPE_WPG;
        else {
            return 0;
        }
        return format;
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

