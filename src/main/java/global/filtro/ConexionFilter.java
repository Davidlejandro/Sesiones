package global.filtro;

import global.service.ServiceJdbcException;
import global.util.Conexion;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//Podemos usar esta clase filtro en cualquier parte de la app
@WebFilter("/*")

public class ConexionFilter implements Filter {
    @Override
    //Define los parametros
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //1) Hacemos un try-catch

        try(Connection conn = Conexion.getConnection();) {

            System.out.println("Conexion establecida");
            if (conn.getAutoCommit()) {

                conn.setAutoCommit(false);
            }

            try{
                request.setAttribute("conn", conn);
                chain.doFilter(request, response);
                conn.commit();
            }catch (SQLException | ServiceJdbcException e) {
                conn.rollback();
                ((HttpServletResponse) response ).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException throwables) {
            System.out.println("Error: no se conecto a la base, Detalle: " + throwables.getMessage());
            throwables.printStackTrace();

        }
    }
}
