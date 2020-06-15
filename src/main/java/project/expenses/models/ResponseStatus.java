package project.expenses.models;

public class ResponseStatus {
    private Boolean successResponse;
    private String error;


    public void setSuccessResponse(Boolean successResponse) {
        this.successResponse = successResponse;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getSuccessResponse() {
        return successResponse;
    }

    public String getError() {
        return error;
    }

}
