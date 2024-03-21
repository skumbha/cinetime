package com.cinetime.theater.exception;

public class ErrorResponse {
        private String service;
        private String spanID;
        private ErrorMessage error;

        public ErrorResponse(String service, String spanID, ErrorMessage error) {
            this.service = service;
            this.spanID = spanID;
            this.error = error;
        }

        public String getService() {
            return service;
        }

        public String getSpanID() {
            return spanID;
        }

        public ErrorMessage getError() {
            return error;
        }
    }
