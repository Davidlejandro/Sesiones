package global.service;

public class ServiceJdbcException extends RuntimeException{
    //Sobrecargamos los constructores para obtener el mensaje de error
    //y otro constructor que nos va a dar el mensaje y la causa del error


    public ServiceJdbcException(String message) {
        super(message);
    }

    public ServiceJdbcException(String message, Throwable cause) {
        super(message, cause);
    }
}