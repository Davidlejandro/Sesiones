package global.service;

// Esta clase define una excepción personalizada que extiende RuntimeException
public class ServiceJdbcException extends RuntimeException {

    // Constructor que recibe solo un mensaje de error
    public ServiceJdbcException(String message) {
        // Llama al constructor de la clase padre (RuntimeException) con el mensaje
        super(message);
    }

    // Constructor que recibe un mensaje y una causa (otra excepción)
    public ServiceJdbcException(String message, Throwable cause) {
        // Llama al constructor de la clase padre con el mensaje y la causa original
        super(message, cause);
    }

}