/*
package com.hackathon.hackmsit.hackerrank;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

*/
/**
 * Represents the Hackerrank API client.
 *
 * @author chris_dutra
 *//*

public class HackerrankClient {

    */
/**
     * API client version.
     *//*

    private static final String VERSION = "0.0.1";

    */
/**
     * Endpoint.
     *//*

    private static final String ENDPOINT = "http://api.hackerrank.com/checker";

    */
/**
     * Http client.
     *//*

    private CloseableHttpClient httpClient;
    */
/**
     * Api key.
     *//*

    private String apiKey;

    */
/**
     * Constructor of the Hackerrank API client.
     *
     * @param apiKey API key
     *//*

    public HackerrankClient(String apiKey) {
        validateApiKey(apiKey);
        this.apiKey = apiKey;
        setHttpClient(HttpClients.createDefault());
    }

    */
/**
     * Valid API key.
     *
     * @param apiKey API key
     *//*

    private void validateApiKey(String apiKey) {
        if (apiKey == null || apiKey.equals("")) {
            throw new IllegalArgumentException("API key'" + apiKey
                    + "' is invalid!");
        }
    }

    */
/**
     * Sets the http client.
     *
     * @param httpClient Http client
     *//*

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    */
/**
     * Retrieves the http client.
     *
     * @return Http client
     *//*

    public CloseableHttpClient getHttpClient() {
        return this.httpClient;
    }

    */
/**
     * Retrieves all the supported programming languages. <br>
     * <br>
     * List of keys: "c", "cpp", "java", "csharp", "php", "ruby", "python",
     * "perl", "haskell", "clojure", "scala", "bash", "mysql", "oracle",
     * "erlang", "clisp", "lua", "go".
     *
     * @return Map with supported programming languages.
     * @throws HackerrankException
     *//*

    public Map<String, ProgrammingLanguage> getProgrammingLanguages()
            throws HackerrankException {
        HackerrankResponse response = request(HttpMethod.GET, ENDPOINT
                + "/languages.json", null);
        try {
            if (response.isError()) {
                throw new HackerrankException(response.getUrl(),
                        response.getHttpStatusCode());
            }
        } catch (NullPointerException e) {
            throw new HackerrankException(e);
        }
        return jsonToProgrammingLanguage(new JSONObject(
                response.getResponseText()));
    }

    */
/**
     * Converts a JSON object to a Map of programming languages. <br>
     * <br>
     * List of keys: "c", "cpp", "java", "csharp", "php", "ruby", "python",
     * "perl", "haskell", "clojure", "scala", "bash", "mysql", "oracle",
     * "erlang", "clisp", "lua", "go".
     *
     * @param json JSON object.
     * @return Map with programming languages.
     *//*

    public Map<String, ProgrammingLanguage> jsonToProgrammingLanguage(
            JSONObject json) {
        Map<String, ProgrammingLanguage> result = new HashMap<String, ProgrammingLanguage>();
        JSONObject languages = null;
        JSONObject names = null;
        JSONObject codes = null;

        try {
            languages = json.getJSONObject("languages");
            names = languages.getJSONObject("names");
            codes = languages.getJSONObject("codes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> codeIterator = codes.keys();
        while (codeIterator.hasNext()) {
            String id = codeIterator.next();
            String name;
            try {
                name = names.getString(id);
            } catch (Exception e) {
                name = id;
            }
            int code = 0;
            try {
                code = codes.getInt(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result.put(id, new ProgrammingLanguage(name, code));
        }
        return result;
    }

    */
/**
     * Submits source code to the Hackerrank platform
     *
     * @param source    Source code
     * @param lang      Programming language code
     * @param testCases JSON array format string containing the test cases
     * @return response
     * @throws HackerrankException
     *//*

    public HackerrankResponse submit(String source, int lang, String testCases)
            throws HackerrankException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("source", source));
        params.add(new BasicNameValuePair("lang", lang + ""));
        params.add(new BasicNameValuePair("testcases", testCases));
        params.add(new BasicNameValuePair("api_key", apiKey));
        return request(HttpMethod.POST, ENDPOINT + "/submission.json", params);
    }

    */
/**
     * Submits file to the Hackerrank platform
     *
     * @param file      File with the source code.
     * @param lang      Programming language code.
     * @param testCases Each test cases is a string that will be used as input
     * @return response
     * @throws HackerrankException
     *//*

    public HackerrankResponse submit(File file, int lang,
                                     Collection<String> testCases) throws HackerrankException {
        String sourceCode = readFile(file);
        Collection<Object> objectCollection = new ArrayList<Object>(testCases);
        return submit(sourceCode, lang,
                new JSONArray(objectCollection).toString());
    }

    */
/**
     * @param filePath  File path of file containing the source code.
     * @param lang      Programming language code.
     * @param testCases Each test cases is a string that will be used as input
     * @return response
     * @throws HackerrankException
     *//*

    public HackerrankResponse submit(String filePath, int lang,
                                     Collection<String> testCases) throws HackerrankException {
        return submit(new File(filePath), lang, testCases);
    }

    private String readFile(File file) throws HackerrankException {
        FileInputStream fis = null;
        StringBuilder builder = new StringBuilder();
        try {
            fis = new FileInputStream(file);
            int content;
            while ((content = fis.read()) != -1) {
                builder.append((char) content);
            }
        } catch (FileNotFoundException e) {
            throw new HackerrankException(e);
        } catch (IOException e) {
            throw new HackerrankException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new HackerrankException(e);
            }
        }
        return builder.toString();
    }

    */
/**
     * Http Method (eg. GET, POST)
     *
     * @author chris_dutra
     *//*

    private enum HttpMethod {
        GET, POST
    }

    */
/**
     * Performs a http request to the given path with the method and parameters.
     *
     * @param method Http method. (eg. GET, POST)
     * @param path   Path
     * @param params Parameters
     * @return Hackerrank response
     * @throws HackerrankException
     *//*

    public HackerrankResponse request(final HttpMethod method,
                                      final String path, final List<NameValuePair> params)
            throws HackerrankException {
        HttpRequestBase request = setUpRequest(method, path, params);
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            String responseBody = readResponseBody(entity);
            HackerrankResponse hhResponse = new HackerrankResponse(request
                    .getURI().toString(), responseBody, response
                    .getStatusLine().getStatusCode(),
                    response.getHeaders("Content-Type")[0].getValue());
            EntityUtils.consume(entity);
            return hhResponse;
        } catch (IOException e) {
            throw new HackerrankException(e);
        }
    }

    */
/**
     * Reads the response body in entity.
     *
     * @param entity Http entity
     * @return String with the response body content.
     * @throws ParseException
     * @throws IOException
     *//*

    private String readResponseBody(HttpEntity entity) throws ParseException,
            IOException {
        String responseBody = null;
        if (entity != null) {
            responseBody = EntityUtils.toString(entity);
        }
        return responseBody;
    }

    */
/**
     * Sets up the http request.
     *
     * @param method Method (eg. GET, POST)
     * @param path   Path
     * @param params Parameters.
     * @return Http request.
     *//*

    private HttpRequestBase setUpRequest(final HttpMethod method,
                                         final String path, final List<NameValuePair> params) {
        HttpRequestBase request;
        try {
            request = createHttpRequest(method, path, params);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        request.addHeader(new BasicHeader("X-HackerRank-Client", "java-"
                + VERSION));
        request.addHeader(new BasicHeader("User-Agent", "hackerank-java/"
                + VERSION));

        return request;
    }

    */
/**
     * Creates a http request.
     *
     * @param method Method (eg. GET, POST)
     * @param path   Path
     * @param params Parameters.
     * @return Http request.
     * @throws UnsupportedEncodingException
     *//*

    private HttpRequestBase createHttpRequest(final HttpMethod method,
                                              final String path, final List<NameValuePair> params)
            throws UnsupportedEncodingException {
        switch (method) {
            case GET:
                return new HttpGet(buildUri(path, params));
            case POST:
                HttpPost httpPost = new HttpPost(path);
                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                return httpPost;
            default:
                throw new IllegalArgumentException("Invalid http method: " + method);
        }
    }

    */
/**
     * Builds a URI based on the path and parameters.
     *
     * @param path   Path
     * @param params Parameters.
     * @return
     *//*

    private URI buildUri(final String path, final List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder(path);
        if (params != null && !params.isEmpty()) {
            sb.append("?");
            sb.append(URLEncodedUtils.format(params, "UTF-8"));
        }
        URI uri = null;
        try {
            uri = new URI(sb.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

}
*/
