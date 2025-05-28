package global.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Patrón creacional Singleton
public class Conexion {
    //Para conectar la BDD
    //1) Inicializo 3 variables globales
    //Creo el url para la conexión con la bdd
    //jdbc:bdd://localhost:3306/nombreBDD?zonaHoraria
    private static String url = "jdbc:mysql://localhost:3306/trabajoenclase?serverTimezone=UTC";
    //colocamos el usuario
    private static String username = "root";
    //Contraseña del usuario, si no hay dejar las comillas
    private static String password = "Mantismarina2";


    //2) Implementamos un método para realizar la conexión

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
