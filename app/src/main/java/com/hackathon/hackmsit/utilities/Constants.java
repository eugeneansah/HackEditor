package com.hackathon.hackmsit.utilities;

public class Constants {

    public static final String NOTES_TABLE = "notes";

    public static final String COLUMN_ID = "id";
    //public final static String COLUMN_NAME = "name";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_MODIFIED_TIME = "modified_time";
    public static final String COLUMN_CREATED_TIME = "created_time";

    public static final String[] COLUMNS = {
            Constants.COLUMN_ID,
            Constants.COLUMN_TITLE,
            Constants.COLUMN_CONTENT,
            Constants.COLUMN_MODIFIED_TIME,
            Constants.COLUMN_CREATED_TIME
    };

    public static final String[] keyWords = {"auto", "break", "case", "char", "cin", "const",
            "continue", "cout", "default", "do", "double", "else", "enum", "extern", "float",
            "for", "goto", "if", "int ", "long", "main", "register", "return", "short",
            "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned",
            "void", "volatile", "while"};

    public static final String[] keyWords1 = {"auto", "break", "case", "char", "cin", "const",
            "continue", "cout", "default", "do", "double", "else", "enum", "extern", "float",
            "for", "goto", "if", "int", "long", "main", "register", "return", "short",
            "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned",
            "void", "volatile", "while", "{","}",";",",","#","<",">","[","]"};

    public  static  final  String[] keyWords2={"{","}",";",",","#","<",">","[","]"};


}
