package com.procesverbal.procesverbal;

import com.procesverbal.procesverbal.configuration.StaticFilesConfig;
import com.procesverbal.procesverbal.dto.CommissionMemberDto;
import com.procesverbal.procesverbal.dto.OfferDto;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AppString {
    public static OfferDto offerWinner;
    public static String LOGO="static/images/logo.png";


    public static String UNDER_LINE = "static/images/under-line.png";
    public static String OBJET_TEXT = "static/text/headerText.txt";
    public static String GARAMOND_FONT = "Garamond";
    public static String TIMES_NEW_RAMAN_FONT = "Times New Roman";
    public static String UNIVERS_57_CONDEDSED_FONT = "Univers 57 Condensed";
    public static String SEGOE_PRINT_FONT = "Segoe Print";
    public static String TW_CEN_MT_FONT = "Tw Cen MT";
    public static String COMMISSION_TEXT = "static/text/commission.txt";
    public static String COMMISSION_TEXT_1 = "static/text/commission1.txt";
    public static String COMMISSION_TEXT_2 = "est composée comme suit :";
    public static String JOURNAUX_TEXT_1 = "Et ce, conformément:";
    public static String JOURNAUX_TEXT_2 = "À l’avis publié dans les journaux:";
    public static String JOURNAUX_TEXT_PORTAIL = "- Le portail des marchés publics:";
    public static String JOURNAUX_TEXT_PORTAIL_URL = "www.marchespublics.gov.ma";
    public static String NEANT = "Néant";
    public static String OFFER_TEXT_PLIS = "static/text/plis-text.txt";
    public static String OFFER_TEXT_ONLINE = "static/text/offers-online.txt";
    public static String OFFER_TEXT_NON_DEPOSE = "static/text/non-deposer.txt";
    public static String OFFER_TEXT_INCOMPLETE = "static/text/incomplet.text";
    public static String OFFER_TEXT_PLIS_DESPOSER = "static/text/plis-desposer.txt";
    public static String OFFER_TEXT_WITHOUT_RS_TXT = "static/text/list-final-befor.txt";
    public static String OFFER_TEXT_WITHOUT_RS_STRING = "Liste des concurrents admissibles sans réserve :";
    public static String OFFER_TEXT_WITH_RS_STRING = "Liste des concurrents admissibles avec réserve :";
    public static String OFFER_TEXT_MONTANT = "static/text/montant.txt";
    public static String OFFER_TEXT_MOTIF_PART1 = "static/text/motif-part1.txt";
    public static String OFFER_TEXT_MOTIF_PART2 = "static/text/motif-part2.txt";
    public static String OFFER_TEXT_MOTIF_PART3 = "static/text/motif-part3.txt";
    public static String OFFER_TEXT_MOTIF_PART4 = "static/text/motif-part4.txt";
    public static String OFFER_TEXT_SEANCE_PUBLIC = "La séance publique est alors reprise et le Président :";
    public static String OFFER_TEXT_MAJORATION_1 = "- Donne lecture de la liste des concurrents admissibles cités ci-dessus en précisant aux concurrents admissibles avec réserve l’objet de celle-ci ;";
    public static String OFFER_TEXT_MAJORATION_2 = "- Rend, contre décharge, aux concurrents écartés présents leurs dossiers à l’exception des éléments d’information ayant été à l’origine de leur élimination, il s’agit de : ";
    public static String OFFER_TEXT_FINANCIER = "static/text/offer-financier.txt";
    public static String OFFER_TEXT_MOTIF_AFTER_1 = "static/text/after-maj1.txt";
    public static String OFFER_TEXT_MOTIF_AFTER_2 = "static/text/after-maj1.txt";
    public static String OFFER_RECTIFIES_TEXT = "static/text/rectifies.txt";
    public static String OFFER_CLASSEMENT_TEXT = "static/text/classement.txt";
    public static String OFFER_CLASSEMENT_STRING = "Ce classement donne les résultats suivants :";
    public static String OFFER_INVITE_STRING = "La commission invite par faxe confirmé, le concurrent ayant présenté l’offre la plus avantageuse qui est ";
    public static String OFFER_INVITE_STRING_2 = "1.\tProduire les pièces du dossier administratif prévues par le règlement de la consultation.";
    public static String OFFER_DELAI_STRING_1 = "Et ce avant le";
    public static String OFFER_DELAI_STRING_2 = "délai de rigueur.";
    public static String OFFER_SUSPEND_TEXT = "static/text/suspend.txt";
    public static String OFFER_SUSPEND_STRING = "Le président suspend la séance et fixe la date du ";
    public static String OFFER_FAIT_LE_STRING = "Fait à Zagora, le :";
    public static String OFFER_COMMISSION_LE_STRING = "Emargement des membres de la commission :";
    public static String RECEPTION_TEXT = "static/text/reception.txt";
    public static String RECEPTION_STRING = ") et vérifie les pièces et la réponse reçue.";
    public static String WINNER_STRING = "Ayant présenté l’offre la plus avantageuse, pour un montant de";
    public static String WINNER_TEXT = "static/text/winner.txt";

    public static String DOTE_SYMBOL = "⚫";
    public static String DH_TTC_SYMBOL = "DHS TTC";
    public static String DASH_SYMBOL = "-";
    public static String BLACK_COLOR = "000000";
    public static int BORDER_SIZE = 12;
    public static int PAGE_MARGINS =800;
    public static int PAGE_MARGINS_BOTTOM =200;
    public static List<String> COMMISSION_TAB_HEADER = new ArrayList<String>() {{
        add("Membres de la commission");
        add("Emargements");
    }};
    public static List<String> OFFER_MOTIF_TAB_HEADER = new ArrayList<String>() {{
        add("Concurrents");
        add("Motif de l’écartement");
    }};
    public static List<String> OFFER_RESERVE_TAB_HEADER = new ArrayList<String>() {{
        add("Concurrents");
        add("Objet de la réserve");
    }};
    public static List<String> OFFER_FINANCIER_TAB_HEADER = new ArrayList<String>() {{
        add("Concurrents");
        add("Montant des actes d’engagement en dirhams TTC");
        add("Rabais /Majoration");
    }};
    public static List<String> OFFER_RECTIFIES_TAB_HEADER = new ArrayList<String>() {{
        add("Concurrents");
        add("Montant des actes \n d’engagement en \n dirhams TTC");
        add("Montant des actes  d’engagement rectifiés en dirhams TTC");
    }};
    public static List<CommissionMemberDto> COMMISSION_FIX_MEMBER = new ArrayList<CommissionMemberDto>() {{
        add(new CommissionMemberDto("M.EL BERKAOUI AHMED", "Chef de service des affaires administratives et financière, des constructions, des équipements et des patrimoines", "PRESIDENT", ""));
        add(new CommissionMemberDto("M. ABBASSE ANNAYA", "Bureau des marchés", "MEMBRE", ""));
        add(new CommissionMemberDto("Mlle. ASSYA AIT ELMAATI", "Bureau des marchés", "MEMBRE", ""));
        add(new CommissionMemberDto("M. ABDERRAHMANE SOUFI", "Bureau de comptabilité", "MEMBRE", ""));
        add(new CommissionMemberDto("M.ABDELKARIM EL MALKI", "Bureau d’économie", "MEMBRE", ""));

    }};
    public static int CELL_HEIGHT_CONTENT = 800;
    public static int CELL_HEIGHT_HEADER = 600;
    public static int PARAGRAPH_OFFSET = 300;



}

