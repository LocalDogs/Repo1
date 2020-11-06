package com.example.localdogs;
import java.util.Calendar;
import java.util.Date;

public class dob {
    public int month;
    public int day;
    public int year;

    public dob(){

    }
    public dob(int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
    }
    public int getAgeYears(){
        java.util.Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        dob d = new dob(cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
        return getDiffYears(d);
    }
    public int getDiffYears(dob d){
        int y = Math.abs(d.year - this.year);
        if(d.month < this.month)
            y--;
        else if(d.month == this.month)
            if(d.day < this.day)
                y--;
        return y;
    }
}
