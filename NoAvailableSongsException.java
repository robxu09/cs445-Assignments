package cs445.a4;

/**
 * An exception that is thrown when there are no
 * available songs in the system to return that fit 
 * a certain criteria
 */
public class NoAvailableSongsException extends Exception {
    public NoAvailableSongsException() { super(); }
    public NoAvailableSongsException(String e) { super(e); }
}

