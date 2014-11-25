package br.uff.ic.gems.phoenix.exception;

public class PhoenixDiffException extends Exception {

    private static final long serialVersionUID = 8896488301644194951L;

    public PhoenixDiffException(String message) {
        super(message);
    }
    
    public PhoenixDiffException(String message, Throwable cause) {
        super(message,cause);
    }
}
