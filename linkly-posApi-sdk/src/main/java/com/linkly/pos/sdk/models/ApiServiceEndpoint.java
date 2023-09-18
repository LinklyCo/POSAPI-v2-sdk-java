package com.linkly.pos.sdk.models;

import java.net.URI;
import java.net.URISyntaxException;

import com.linkly.pos.sdk.service.impl.PosApiService;

/**
 * Configuration for a {@link PosApiService}
 */
public class ApiServiceEndpoint {

    private String authApiHost;
    private String posApiHost;

    /**
     * Specify API endpoints for {@link PosApiService}. This requires an endpoint for the POS
     * service and Auth service.
     * 
     * @param authApiHost
     *            Base URI of the Auth API service.
     *            Example: http://auth.cloud.pceftpos.com
     * @param posApiHost
     *            Base URI of the POS API service.
     *            Example: http://pos.cloud.pceftpos.com
     */
    public ApiServiceEndpoint(String authApiHost, String posApiHost) {
        if (!isBaseUri(authApiHost)) {
            throw new IllegalArgumentException("authApiHost must be absolute and not contain path");
        }
        if (!isBaseUri(posApiHost)) {
            throw new IllegalArgumentException("posApiHost must be absolute and not contain path");
        }
        this.authApiHost = authApiHost;
        this.posApiHost = posApiHost;
    }

    /**
     * Base URI of the Auth API service.
     * 
     * @return String
     */
    public String getAuthApiHost() {
        return authApiHost;
    }

    /**
     * Sets the Base URI used for Auth API services.
     * 
     * @param authApiHost
     *            The String value of authApiHost.
     */
    public void setAuthApiHost(String authApiHost) {
        this.authApiHost = authApiHost;
    }

    /**
     * Base URI of the POS API service.
     * 
     * @return String
     */
    public String getPosApiHost() {
        return posApiHost;
    }

    /**
     * Sets the Base URI used for POS API services.
     * 
     * @param posApiHost
     *            The String value of posApiHost.
     */
    public void setPosApiHost(String posApiHost) {
        this.posApiHost = posApiHost;
    }

    /**
     * Validates that the provided str is a base URI.
     * 
     * @param str
     *            The provided base URI for checking.
     * @return 'true' if the provided string matches a base URI, otherwise returns 'false'.
     * @throws RuntimeException
     *             If the provided str does not match a valid URI.
     */
    private boolean isBaseUri(String str) {
        try {
            URI uri = new URI(str);
            return uri.isAbsolute() && !uri.getPath().equals("/");
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Invalid String URI: " + str);
        }
    }
}
