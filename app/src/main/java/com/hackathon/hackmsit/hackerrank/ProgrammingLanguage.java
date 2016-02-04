package com.hackathon.hackmsit.hackerrank;


/**
 * Represents a programming language from the HackerRank platform.
 *
 * @author chris_dutra
 *
 */
public class ProgrammingLanguage {

    private String name;
    private int code;

    /**
     * Constructor for class ProgrammingLanguage.
     *
     * @param name
     *            Name
     * @param code
     *            Code
     */
    public ProgrammingLanguage(String name, int code) {
        setName(name);
        setCode(code);
    }

    private void isNullOrEmpty(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkCode(int code) {
        if (code < 1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Name of the programming language.
     *
     * @return String with its name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the programming language.
     *
     * @param name
     *            Name
     */
    public void setName(String name) {
        isNullOrEmpty(name);
        this.name = name;
    }

    /**
     * Code of the programming language.
     *
     * @return integer with its code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the code of the programming language.
     *
     * @param code
     *            Code
     */
    public void setCode(int code) {
        checkCode(code);
        this.code = code;
    }

}