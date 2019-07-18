package nl.Bookshop.exceptions;

public class ClientException extends RuntimeException {
    private int code;

    public ClientException() {
        this(500);
    }
    public ClientException(int code) {
        this(code, "Error while processing the request", null);
    }
    public ClientException(int code, String message) {
        this(code, message, null);
    }
    public ClientException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
