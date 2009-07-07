package net.chrissearle.flickrvote.flickr;

public class FlickrServiceException extends RuntimeException {

    public FlickrServiceException() {
        super();
    }

    public FlickrServiceException(String message) {
        super(message);
    }

    public FlickrServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlickrServiceException(Throwable cause) {
        super(cause);
    }
}
