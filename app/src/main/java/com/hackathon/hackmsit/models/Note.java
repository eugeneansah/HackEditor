package com.hackathon.hackmsit.models;

import android.database.Cursor;

import com.hackathon.hackmsit.utilities.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Note {
    private Long id;
    private String title;
    private String content;
    private String ext;
    private Calendar dateCreated;
    private Calendar dataModified;

    public String getReadableModifiedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault());
        sdf.setTimeZone(getDataModified().getTimeZone());
        Date modifiedDate = getDataModified().getTime();
        String displayDate = sdf.format(modifiedDate);
        return displayDate;
    }

    public static Note fromCursor(Cursor cursor){
        Note note = new Note();
        note.setId(cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TITLE)));
        note.setContent(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CONTENT)));

        //get Calendar instance
        Calendar calendar = GregorianCalendar.getInstance();

        //set the calendar time to date created
        calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_CREATED_TIME)));
        note.setDateCreated(calendar);

        //set the calendar time to date modified
        calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(Constants.COLUMN_MODIFIED_TIME)));
        note.setDataModified(calendar);
        return note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Calendar getDataModified() {
        return dataModified;
    }

    public void setDataModified(Calendar dataModified) {
        this.dataModified = dataModified;
    }
}
