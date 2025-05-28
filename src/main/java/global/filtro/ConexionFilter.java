package global.filtro;

import global.service.ServiceJdbcException;
import global.util.Conexion;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

// Este filtro intercepta todas las solicitudes (/*) para gestionar la conexión a la base de datos
@WebFilter("/*")
public class ConexionFilter implements Filter {

    @Override
    // Método principal del filtro que procesa cada solicitud y respuesta
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Usamos try-with-resources para abrir la conexión y asegurarnos que se cierre automáticamente al finalizar
        try (Connection conn = Conexion.getConnection();) {

            System.out.println("Conexion establecida");

            // Verificamos si el auto-commit está activado para desactivarlo y manejar manualmente las transacciones
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                // Pasamos la conexión a los siguientes componentes (servlets, DAO, etc.) a través del atributo de la request
                request.setAttribute("conn", conn);

                // Continuamos con la cadena de filtros y procesamiento de la solicitud
                chain.doFilter(request, response);

                // Si todo sale bien, confirmamos (commit) los cambios realizados en la base de datos
                conn.commit();

            } catch (SQLException | ServiceJdbcException e) {
                // Si ocurre una excepción durante el procesamiento, revertimos (rollback) la transacción
                conn.rollback();

                // Enviamos un error 500 al cliente con el mensaje de error
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());

                // Imprimimos la traza de error en la consola para diagnóstico
                e.printStackTrace();
            }

        } catch (SQLException throwables) {
            // Si no se pudo establecer la conexión a la base de datos, mostramos un mensaje de error y la traza
            System.out.println("Error: no se conecto a la base, Detalle: " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }
}