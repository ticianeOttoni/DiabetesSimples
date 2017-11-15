package com.example.ticiane.diabetessimples.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ticiane on 16/12/2016.
 */

public class DataUtil {

    public static String formataDataSelecionadaParaSqlite (int year, int month, int day, int hour, int minute){
//        String data;

//        data =   year + "-" +
//                (month + 1 < 10 ? "0" + (month + 1) : month + 1) + "-" +
//                (day < 10 ? "0" + day : day) + " " +
//                (hour < 10 ? "0" + hour : hour) + ":" +
//                (minute < 10 ? "0" + minute : minute)+":00.000";

        //data = year+"-"+(month+1)+"-"+day+ " "+hour+":"+minute+":00";
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        return dateFormat.format(calendar.getTime());
    }


    public static String formataDataSelecionadaParaExibir (int year, int month, int day, int hour, int minute){
//        String data;
//
//        data = (day < 10 ? "0" + day : day) + "/" +
//                (month + 1 < 10 ? "0" + (month + 1) : month + 1) + "/" +
//                year + " as " +
//                (hour < 10 ? "0" + hour : hour) + "h" +
//                (minute < 10 ? "0" + minute : minute);
//        //data = year+"-"+(month+1)+"-"+day+ " "+hour+":"+minute+":00";
//        return data;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        return dateFormat.format(calendar.getTime());
    }

    public static Date recuperaDataDeSqlite( String text ){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date d;
        try{
            d = dateFormat.parse(text);
        } catch (Exception e){
            return null;
        }
        return d;
    }

    public static  String formataDataSqlliteParaExibir(String data){
        String dataFormatada = "";

        return  dataFormatada;
    }
}
