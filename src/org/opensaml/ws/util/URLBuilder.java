/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.ws.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javolution.util.FastList;

import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.Pair;

/**
 * Utility class for building URLs. May also be used to parse a URL into its invidual components. All components will be
 * converted UTF-8 encoding and then application/x-www-form-urlencoded when built.
 * 
 * This class is not thread-safe.
 */
public class URLBuilder {

    /** URL schema (http, https, etc) */
    private String scheme;

    /** User name in the URL */
    private String username;

    /** Password in the URL */
    private String password;

    /** Host for the URL */
    private String host;

    /** URL port number */
    private int port;

    /** URL path */
    private String path;

    /** Parameters in the query string */
    private List<Pair<String, String>> queryParams;

    /** URL fragment */
    private String fragement;

    /**
     * Constructor
     */
    public URLBuilder() {
        queryParams = new FastList<Pair<String, String>>();
    }

    /**
     * Constructor. The provided URL will be decoded using {@link URI}. Further processing will then be done in order
     * to extract additional information.
     * 
     * @param baseURL URL to parse and use as basis for creating other URLs
     * 
     * @throws IllegalArgumentException thrown if the given base URL is not well formed
     */
    public URLBuilder(String baseURL) throws IllegalArgumentException {
        try {
            URL url = new URL(baseURL);

            setScheme(url.getProtocol());

            String userInfo = url.getUserInfo();
            if (!DatatypeHelper.isEmpty(userInfo)) {
                if (userInfo.contains(":")) {
                    String[] userInfoComps = userInfo.split(":");
                    setUsername(URLDecoder.decode(userInfoComps[0], "UTF-8"));
                    setPassword(URLDecoder.decode(userInfoComps[1], "UTF-8"));
                } else {
                    setUsername(userInfo);
                }
            }

            setHost(url.getHost());
            setPort(url.getPort());
            setPath(url.getPath());

            queryParams = new FastList<Pair<String, String>>();
            String queryString = url.getQuery();
            if (!DatatypeHelper.isEmpty(queryString)) {
                String[] queryComps = queryString.split("&");
                String queryComp;
                String[] paramComps;
                String paramName;
                String paramValue;
                for (int i = 0; i < queryComps.length; i++) {
                    queryComp = queryComps[i];
                    if (!queryComp.contains("=")) {
                        paramName = URLDecoder.decode(queryComp, "UTF-8");
                        queryParams.add(new Pair<String, String>(paramName, null));
                    } else {
                        paramComps = queryComp.split("=");
                        paramName = URLDecoder.decode(paramComps[0], "UTF-8");
                        paramValue = URLDecoder.decode(paramComps[1], "UTF-8");
                        queryParams.add(new Pair<String, String>(paramName, paramValue));
                    }
                }
            }

            setFragement(url.getRef());
        } catch (Exception e) {
            throw new IllegalArgumentException("Given URL is not well formed", e);
        }
    }

    /**
     * Gets the URL fragment in its decode form.
     * 
     * @return URL fragment in its decoded form
     */
    public String getFragement() {
        return fragement;
    }

    /**
     * Sets the URL fragment in its decoded form.
     * 
     * @param fragement URL fragment in its decoded form
     */
    public void setFragement(String fragement) {
        this.fragement = DatatypeHelper.safeTrimOrNullString(fragement);
    }

    /**
     * Gets the host component of the URL.
     * 
     * @return host component of the URL
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host component of the URL.
     * 
     * @param host host component of the URL
     */
    public void setHost(String host) {
        this.host = DatatypeHelper.safeTrimOrNullString(host);
    }

    /**
     * Gets the user's password in the URL.
     * 
     * @return user's password in the URL
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password in the URL.
     * 
     * @param password user's password in the URL
     */
    public void setPassword(String password) {
        this.password = DatatypeHelper.safeTrimOrNullString(password);
    }

    /**
     * Gets the path component of the URL.
     * 
     * @return path component of the URL
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path component of the URL.
     * 
     * @param path path component of the URL
     */
    public void setPath(String path) {
        this.path = DatatypeHelper.safeTrimOrNullString(path);
    }

    /**
     * Gets the port component of the URL.
     * 
     * @return port component of the URL
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port component of the URL.
     * 
     * @param port port component of the URL
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets the query string parameters for the URL. Params may be added and removed through the map interface.
     * 
     * @return query string parameters for the URL
     */
    public List<Pair<String, String>> getQueryParams() {
        return queryParams;
    }

    /**
     * Gets the URL scheme (http, https, etc).
     * 
     * @return URL scheme (http, https, etc)
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Sets the URL scheme (http, https, etc).
     * 
     * @param scheme URL scheme (http, https, etc)
     */
    public void setScheme(String scheme) {
        this.scheme = DatatypeHelper.safeTrimOrNullString(scheme);
    }

    /**
     * Gets the user name component of the URL.
     * 
     * @return user name component of the URL
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user name component of the URL.
     * 
     * @param username user name component of the URL
     */
    public void setUsername(String username) {
        this.username = DatatypeHelper.safeTrimOrNullString(username);
    }

    /**
     * Builds a URL from the given data. The constructured URL may not be valid if sufficient information is not
     * provided. The returned URL will be appropriately encoded using application/x-www-form-urlencoded with appropriate
     * encoding of UTF-8 characters.
     * 
     * @return URL built from the given data
     */
    public String buildURL() {
        StringBuilder builder = new StringBuilder();

        if (!DatatypeHelper.isEmpty(scheme)) {
            builder.append(scheme);
            builder.append("://");
        }

        if (!DatatypeHelper.isEmpty(username)) {
            builder.append(username);
            if (!DatatypeHelper.isEmpty(password)) {
                builder.append(":");
                builder.append(password);
            }

            builder.append("@");
        }

        if (!DatatypeHelper.isEmpty(host)) {
            builder.append(host);
            if (port > 0) {
                builder.append(":");
                builder.append(Integer.toString(port));
            }
        }

        if (!DatatypeHelper.isEmpty(path)) {
            if (!path.startsWith("/")) {
                builder.append("/");
            }
            builder.append(path);
        }

        String queryString = buildQueryString();
        if(!DatatypeHelper.isEmpty(queryString)){
            builder.append("?");
            builder.append(queryString);
        }

        if (!DatatypeHelper.isEmpty(fragement)) {
            builder.append("#");
            builder.append(fragement);
        }

        return builder.toString();
    }
    
    /**
     * Builds the query string for the URL.
     * 
     * @return query string for the URL or null if there are now query parameters
     */
    public String buildQueryString(){
        StringBuilder builder = new StringBuilder();
        try {
            if (queryParams.size() > 0) {
                String name;
                String value;

                Pair<String, String> param;
                for (int i = 0; i < queryParams.size(); i++) {
                    param = queryParams.get(i);
                    name = DatatypeHelper.safeTrimOrNullString(param.getFirst());

                    if (name != null) {
                        builder.append(URLEncoder.encode(name, "UTF-8"));
                        value = DatatypeHelper.safeTrimOrNullString(param.getSecond());
                        if (value != null) {
                            builder.append("=");
                            builder.append(URLEncoder.encode(value, "UTF-8"));
                        }
                        if (i < queryParams.size() - 1) {
                            builder.append("&");
                        }
                    }
                }
                
                return builder.toString();
            }
        } catch (UnsupportedEncodingException e) {
            // UTF-8 encoding is required to be supported by all JVMs
        }
        
        
        return null;
    }
}