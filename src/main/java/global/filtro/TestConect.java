package global.filtro;

import global.util.Conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConect extends Conexion {
public static void main(String[] args) {
    System.out.println("Probando la conexión");
    //hacemos un try catch para que la app no se vaya con Diosito
    //conn debe darme la conexión
    try (Connection conn = Conexion.getConnection()) {
        //si la conexión no es nula y no está cerrada entonces me dirá exitosa
        if (conn != null && !conn.isClosed()) {
            System.out.println(" ¡Conexión exitosa!");
            //caso contrario, me dira un error de conexión
        } else {
            System.out.println("No se pudo establecer la conexión.");
        }
        //cualquier error de sql me pondrá en el catch
    } catch (SQLException e) {
        System.out.println("Error al conectar con la base de datos:");
        e.printStackTrace();
        }
    }
}