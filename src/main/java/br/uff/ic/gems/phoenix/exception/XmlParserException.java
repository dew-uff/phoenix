package br.uff.ic.gems.phoenix.exception;

public class XmlParserException extends Exception {
    
    private static final long serialVersionUID = -551230746801132910L;

    public XmlParserException(String message) {
        super(message);
    }

    public XmlParserException(String message, Throwable cause) {
        super(message,cause);
    }
}
