package com.richardz02.personal_finance.dto;

public class ApiResponse {
    public enum Status {
        SUCCESS(true),
        FAILURE(false);

        private final boolean isSuccess;

        Status(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }

    private Status status;
    private String message;

    public ApiResponse(Status status,String message) {
        this.status= status;
        this.message = message;
    }

    public boolean getSuccess() {
        return status.isSuccess();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }
}
