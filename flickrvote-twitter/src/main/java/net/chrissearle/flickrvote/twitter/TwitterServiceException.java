package net.chrissearle.flickrvote.twitter;

public class TwitterServiceException extends RuntimeException {
    public TwitterServiceException() {
        super();
    }

    public TwitterServiceException(String message) {
        super(message);
    }

    public TwitterServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwitterServiceException(Throwable cause) {
        super(cause);
    }
}
