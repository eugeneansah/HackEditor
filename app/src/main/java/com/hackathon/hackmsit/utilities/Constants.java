package com.hackathon.hackmsit.utilities;

import org.json.JSONObject;

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
            "void", "volatile", "while", "{", "}", ";", ",", "#", "<", ">", "[", "]"};

    public static final String[] keyWords2 = {"{", "}", ";", ",", "#", "<", ">", "[", "]"};

    public static final String languagesString = "{\"c\":1,\"cpp\":2,\"java\":3, \"py\":5, \"perl\":6, \"php\":7, \"ruby\":8, \"csharp\":9, \"mysql\":10, \"oracle\":11, \"haskell\":12, \"clojure\":13, \"bash\":14, \"scala\":15, \"erlang\":16, \"lua\":18, \"javascript\":20, \"go\":21, \"d\":22, \"ocaml\":23, \"r\":24, \"pascal\":25, \"sbcl\":26, \"python3\":30, \"groovy\":31, \"objectivec\":32, \"fsharp\":33, \"cobol\":36, \"visualbasic\":37, \"lolcode\":38, \"smalltalk\":39, \"tcl\":40, \"whitespace\":41, \"tsql\":42, \"java8\":43, \"db2\":44, \"octave\":46, \"xquery\":48, \"racket\":49, \"rust\":50, \"swift\":51, \"fortran\":54}";

}
