package net.thucydides.junit.annotations;

/**
 * Thrown if no suitable WebDriver field with the @Managed annotation is found in a test.
 * 
 * @author johnsmart
 *
 */
public class InvalidManagedPagesFieldException extends RuntimeException {

    private static final long serialVersionUID = -7552399074205295160L;

    public InvalidManagedPagesFieldException(final String message) {
        super(message);
    }
 
}
