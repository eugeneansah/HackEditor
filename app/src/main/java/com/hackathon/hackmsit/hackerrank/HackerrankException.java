package com.hackathon.hackmsit.hackerrank;


/**
 * Hackerrank Exception class.
 *
 * @author chris_dutra
 *
 */
public class HackerrankException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Error code.
     */
    private int errorCode;

    /**
     * Constructor
     *
     * @param message
     *            Error message
     * @param errorCode
     *            Error code
     */
    public HackerrankException(String message, int errorCode) {
        super(message);
        this.setErrorCode(errorCode);
    }

    /**
     * Constructor
     *
     * @param message
     *            Error message
     * @param errorCode
     *            Error code
     * @param throwable
     *            Originating exception
     */
    public HackerrankException(String message, int errorCode,
                               Throwable throwable) {
        super(message, throwable);
        this.setErrorCode(errorCode);
    }

    /**
     * Constructor
     *
     * @param throwable
     *            Originating exception
     */
    public HackerrankException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Retrieves the error code.
     *
     * @return Error code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     *
     * @param errorCode
     *            Error code.
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}