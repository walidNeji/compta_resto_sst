/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.sst.compta_resto.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NEJI Walid
 */
public class MyDate {


    public static String listeDesDates(Date DateDebut, Date DateFin) {

        GregorianCalendar caldeb = new GregorianCalendar();
        caldeb.setTime(DateDebut);
        GregorianCalendar calfin = new GregorianCalendar();
        calfin.setTime(DateFin);
        String listeDates = "";


        while (caldeb.before(calfin)) {
            listeDates = listeDates + formatDate(caldeb.getTime()) + ";";
            caldeb.add(GregorianCalendar.DATE, 1);
        }
        //supprimer le dernier caractère (;)
        return listeDates;
    }

    public static String formatDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("dd/MM/yyyy");
        return sdf.format(d.getTime());
    }
    
    public static Date formatStringToDate(String date){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }
}
