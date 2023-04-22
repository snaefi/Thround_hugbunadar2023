package hi.vidmot.verkefni.repository.exceptions;

public class OfflineException extends Exception {

    public OfflineException() {
        super("Your offline, check your internet");
    }
}
