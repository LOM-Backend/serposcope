package serposcope.controllers.google.entity;

public class KeywordSearchResultsResponse {

    public Boolean success;
    public String message;
    public Object data;

    public KeywordSearchResultsResponse() {
    }

    public KeywordSearchResultsResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public KeywordSearchResultsResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KeywordSearchResultsResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
