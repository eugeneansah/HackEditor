package com.hackathon.hackmsit.hackerrank;


/**
 * Represents the HackerrankResponse.
 *
 * @author chris_dutra
 *
 */
public class HackerrankResponse {

    /**
     * Url
     */
    private String url;
    /**
     * Response text.
     */
    private String responseText;
    /**
     * Http status code.
     */
    private int httpStatusCode;
    /**
     * Content type.
     */
    private String contentType;

    /**
     * Constructor of HackerrankResponse.
     *
     * @param url
     *            Response URL.
     * @param responseText
     *            Response text.
     * @param httpStatusCode
     *            Status code.
     */
    public HackerrankResponse(String url, String responseText,
                              int httpStatusCode, String contentType) {
        this.setUrl(url);
        this.setResponseText(responseText);
        this.setHttpStatusCode(httpStatusCode);
        this.setContentType(contentType);
    }

    /**
     * Verifies if the response has succeeded.
     *
     * @return <code>true</code> if response has succeeded <code>false</code>
     *         otherwise.
     */
    public boolean isSuccess() {
        return this.getHttpStatusCode() >= 200
                && this.getHttpStatusCode() < 300;
    }

    /**
     * Verifies if an error has occurred.
     *
     * @return <code>true</code> if an error has occurred <code>false</code>
     *         otherwise.
     */
    public boolean isError() {
        return this.getHttpStatusCode() >= 400;
    }

    /**
     * Verifies if a client error has occurred.
     *
     * @return <code>true</code> if a client error has occurred
     *         <code>false</code> otherwise.
     */
    public boolean isClientError() {
        return this.getHttpStatusCode() >= 400
                && this.getHttpStatusCode() < 500;
    }

    /**
     * Verifies if a server error has occurred.
     *
     * @return <code>true</code> if a server error has occurred
     *         <code>false</code> otherwise.
     */
    public boolean isServerError() {
        return this.getHttpStatusCode() >= 500;
    }

    /**
     * Response raw text.
     *
     * @return String with the response text.
     */
    public String getResponseText() {
        return responseText;
    }

    /**
     * Sets the response raw text.
     *
     * @param responseText
     *            Response text.
     */
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    /**
     * Status code.
     *
     * @return integer with the status code.
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Sets the status code.
     *
     * @param statusCode
     *            Status code.
     */
    public void setHttpStatusCode(int statusCode) {
        this.httpStatusCode = statusCode;
    }

    /**
     * Response URL.
     *
     * @return String with the response URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets response url.
     *
     * @param url
     *            URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Content type.
     *
     * @return Content type.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param contentType
     *            Content type.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Verifies if the content type is json.
     *
     * @return <code>true</code> if content type is json <code>false</code>
     *         otherwise.
     */
    public boolean isJSON() {
        if (this.getContentType() == null) {
            return false;
        } else {
            return this.getContentType().toLowerCase()
                    .contains("application/json");
        }
    }

    /**
     * Verifies if the content type is xml.
     *
     * @return <code>true</code> if content type is xml <code>false</code>
     *         otherwise.
     */
    public boolean isXML() {
        if (this.getContentType() == null) {
            return false;
        } else {
            String lowerCaseContentType = this.getContentType().toLowerCase();
            return lowerCaseContentType.contains("application/xml")
                    || lowerCaseContentType.contains("text/xml");
        }
    }
}