package com.aspect.queue.model.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
        value = "RestError",
        description = "Response of a failed REST operation, containing detailed information about the error."
)
public class OrderRestError {

    @ApiModelProperty(value = "Time of the error, " +
            "represented as number of milliseconds since January 1, 1970, 00:00:00 GMT", required = true)
    private long timestamp;
    @ApiModelProperty(value = "HTTP status code of the error", required = true)
    private int status;
    @ApiModelProperty(value = "Detailed description of the error", required = true)
    private String message;
    @ApiModelProperty(value = "Url where the error occurred", required = true)
    private String url;
    @ApiModelProperty(value = "Error description", required = true)
    private String error;

    public OrderRestError(){}


    public OrderRestError(@JsonProperty("status") int status, @JsonProperty("message") String message,
            @JsonProperty("url") String url,  @JsonProperty("error") String error) {
        this.message = message;
        this.status = status;
        this.url = url;
        this.error = error;
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getError() {
        return error;
    }


}
