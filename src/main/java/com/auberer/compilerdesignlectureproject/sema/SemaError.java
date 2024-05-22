package com.auberer.compilerdesignlectureproject.sema;

public class SemaError extends RuntimeException {

    public SemaError(String message) {
      super(message);
    }

    public SemaError(String message, Throwable cause) {
      super(message, cause);
    }
}
