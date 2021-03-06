package com.microsoft.azure.documentdb;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


/**
 * Represents the service response to a request made from DocumentClient. Contains both the resource and the response
 * headers.
 *
 * @param <T> the resource type of the resource response.
 */
public final class ResourceResponse<T extends Resource> {
    private Class<T> cls;
    private T resource;
    private DocumentServiceResponse response;
    private Map<String, Long> usageHeaders;
    private Map<String, Long> quotaHeaders;

    ResourceResponse(DocumentServiceResponse response, Class<T> cls) {
        this.response = response;
        this.usageHeaders = new HashMap<String, Long>();
        this.quotaHeaders = new HashMap<String, Long>();
        this.cls = cls;
        this.resource = this.response.getResource(this.cls);
        this.response.close();
    }

    /**
     * Max Quota.
     *
     * @return the database quota.
     */
    public long getDatabaseQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.DATABASE);
    }

    /**
     * Current Usage.
     *
     * @return the current database usage.
     */
    public long getDatabaseUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.DATABASE);
    }

    /**
     * Max Quota.
     *
     * @return the collection quota.
     */
    public long getCollectionQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.COLLECTION);
    }

    /**
     * Current Usage.
     *
     * @return the current collection usage.
     */
    public long getCollectionUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.COLLECTION);
    }

    /**
     * Max Quota.
     *
     * @return the user quota.
     */
    public long getUserQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.USER);
    }

    /**
     * Current Usage.
     *
     * @return the current user usage.
     */
    public long getUserUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.USER);
    }

    /**
     * Max Quota.
     *
     * @return the permission quota.
     */
    public long getPermissionQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.PERMISSION);
    }

    /**
     * Current Usage.
     *
     * @return the current permission usage.
     */
    public long getPermissionUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.PERMISSION);
    }

    /**
     * Max Quota.
     *
     * @return the collection size quota.
     */
    public long getCollectionSizeQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.COLLECTION_SIZE);
    }

    /**
     * Current Usage.
     *
     * @return the collection size usage.
     */
    public long getCollectionSizeUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.COLLECTION_SIZE);
    }

    /**
     * Max Quota.
     *
     * @return the document quota.
     */
    public long getDocumentQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.DOCUMENTS_SIZE);
    }

    /**
     * Current Usage.
     *
     * @return the document usage.
     */
    public long getDocumentUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.DOCUMENTS_SIZE);
    }

    /**
     * Max Quota.
     *
     * @return the stored procedures quota.
     */
    public long getStoredProceduresQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.STORED_PROCEDURE);
    }

    /**
     * Current Usage.
     *
     * @return the current stored procedures usage.
     */
    public long getStoredProceduresUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.STORED_PROCEDURE);
    }

    /**
     * Max Quota.
     *
     * @return the triggers quota.
     */
    public long getTriggersQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.TRIGGER);
    }

    /**
     * Current Usage.
     *
     * @return the current triggers usage.
     */
    public long getTriggersUsage() {
        return this.getCurrentQuotaHeader(Constants.Quota.TRIGGER);
    }

    /**
     * Max Quota.
     *
     * @return the user defined functions quota.
     */
    public long getUserDefinedFunctionsQuota() {
        return this.getMaxQuotaHeader(Constants.Quota.USER_DEFINED_FUNCTION);
    }

    /**
     * Current Usage.
     *
     * @return the current user defined functions usage.
     */
    public long getUserDefinedFunctionsUsage() {
        return this.getCurrentQuotaHeader(
            Constants.Quota.USER_DEFINED_FUNCTION);
    }

    /**
     * Gets the Activity ID for the request.
     *
     * @return the activity id.
     */
    public String getActivityId() {
        return this.response.getResponseHeaders().get(HttpConstants.HttpHeaders.ACTIVITY_ID);
    }

    /**
     * Gets the token used for managing client's consistency requirements.
     *
     * @return the session token.
     */
    public String getSessionToken() {
        return this.response.getResponseHeaders().get(HttpConstants.HttpHeaders.SESSION_TOKEN);
    }

    /**
     * Gets the HTTP status code associated with the response.
     *
     * @return the status code.
     */
    public int getStatusCode()
    {
        return this.response.getStatusCode();
    }

    /**
     * Gets the maximum size limit for this entity (in megabytes (MB) for server resources and in count for master
     * resources).
     *
     * @return the max resource quota.
     */
    public String getMaxResourceQuota() {
        return this.response.getResponseHeaders().get(HttpConstants.HttpHeaders.MAX_RESOURCE_QUOTA);
    }

    /**
     * Gets the current size of this entity (in megabytes (MB) for server resources and in count for master resources)
     *
     * @return the current resource quota usage.
     */
    public String getCurrentResourceQuotaUsage() {
        return this.response.getResponseHeaders().get(HttpConstants.HttpHeaders.CURRENT_RESOURCE_QUOTA_USAGE);
    }

    /**
     * Gets the resource for the request.
     *
     * @return the resource.
     */
    public T getResource() {
        return this.resource;
    }

    /**
     * Gets the number of index paths (terms) generated by the operation.
     *
     * @return the request charge.
     */
    public double getRequestCharge() {
        String value = this.getResponseHeaders().get(HttpConstants.HttpHeaders.REQUEST_CHARGE);
        if (StringUtils.isEmpty(value)) {
            return 0;
        }
        return Double.valueOf(value);
    }

    /**
     * Gets the headers associated with the response.
     *
     * @return the resposne headers.
     */
    public Map<String, String> getResponseHeaders() {
        return this.response.getResponseHeaders();
    }

    /**
     * Gets the progress of an index transformation, if one is underway.
     *
     * @return the progress of an index transformation.
     */
    public long getIndexTransformationProgress() {
        String value = this.getResponseHeaders().get(HttpConstants.HttpHeaders.INDEX_TRANSFORMATION_PROGRESS);
        if (StringUtils.isEmpty(value)) {
          return -1;
        }
        return Long.valueOf(value);
    }

    /**
     * Gets the progress of lazy indexing.
     *
     * @return the progress of lazy indexing.
     */
    public long getLazyIndexingProgress() {
        String value = this.getResponseHeaders().get(HttpConstants.HttpHeaders.LAZY_INDEXING_PROGRESS);
        if (StringUtils.isEmpty(value)) {
          return -1;
        }
        return Long.valueOf(value);
    }

    /**
     * Deprecated.
     */
    @Deprecated
    public void close() {
    }

    private long getCurrentQuotaHeader(String headerName) {
        if (this.usageHeaders.size() == 0 &&
                !this.getMaxResourceQuota().isEmpty() &&
                !this.getCurrentResourceQuotaUsage().isEmpty()) {
            this.populateQuotaHeader(this.getMaxResourceQuota(), this.getCurrentResourceQuotaUsage());
        }

        if (this.usageHeaders.containsKey(headerName)) {
            return this.usageHeaders.get(headerName);
        }

        return 0;
    }

    private long getMaxQuotaHeader(String headerName) {
        if (this.quotaHeaders.size() == 0 &&
                !this.getMaxResourceQuota().isEmpty() &&
                !this.getCurrentResourceQuotaUsage().isEmpty()) {
            this.populateQuotaHeader(this.getMaxResourceQuota(), this.getCurrentResourceQuotaUsage());
        }

        if(this.quotaHeaders.containsKey(headerName)) {
            return this.quotaHeaders.get(headerName);
        }

        return 0;
    }

    private void populateQuotaHeader(String headerMaxQuota, String headerCurrentUsage) {
        String[] headerMaxQuotaWords = headerMaxQuota.split(Constants.Quota.DELIMITER_CHARS, -1);
        String[] headerCurrentUsageWords = headerCurrentUsage.split(Constants.Quota.DELIMITER_CHARS, -1);

        assert(headerMaxQuotaWords.length == headerCurrentUsageWords.length);

        for(int i = 0; i < headerMaxQuotaWords.length; ++i) {
            if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.DATABASE)) {
                this.quotaHeaders.put(Constants.Quota.DATABASE, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.DATABASE, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.COLLECTION)) {
                this.quotaHeaders.put(Constants.Quota.COLLECTION, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.COLLECTION, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.USER)) {
                this.quotaHeaders.put(Constants.Quota.USER, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.USER, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.PERMISSION)) {
                this.quotaHeaders.put(Constants.Quota.PERMISSION, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.PERMISSION, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.COLLECTION_SIZE)) {
                this.quotaHeaders.put(Constants.Quota.COLLECTION_SIZE, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.COLLECTION_SIZE, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.DOCUMENTS_SIZE)) {
                this.quotaHeaders.put(Constants.Quota.DOCUMENTS_SIZE, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.DOCUMENTS_SIZE, Long.valueOf(headerCurrentUsageWords[i + 1]));
            }else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.STORED_PROCEDURE)) {
                this.quotaHeaders.put(Constants.Quota.STORED_PROCEDURE, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.STORED_PROCEDURE, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.TRIGGER)) {
                this.quotaHeaders.put(Constants.Quota.TRIGGER, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.TRIGGER, Long.valueOf(headerCurrentUsageWords[i + 1]));
            } else if (headerMaxQuotaWords[i].equalsIgnoreCase(Constants.Quota.USER_DEFINED_FUNCTION)) {
                this.quotaHeaders.put(Constants.Quota.USER_DEFINED_FUNCTION, Long.valueOf(headerMaxQuotaWords[i + 1]));
                this.usageHeaders.put(Constants.Quota.USER_DEFINED_FUNCTION,
                                      Long.valueOf(headerCurrentUsageWords[i + 1]));
            }
        }
    }
}
