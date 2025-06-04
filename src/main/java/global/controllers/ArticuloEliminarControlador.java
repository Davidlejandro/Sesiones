package global.controllers;

// Importación del servicio de artículos y su implementación JDBC
import global.service.ArticuloService;
import global.service.ArticuloServiceJdbcImplements;

// Importación de clases necesarias para el manejo de servlets
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

// Servlet que se activa cuando se accede a /articulo/eliminar
@WebServlet("/articulo/eliminar")
public class ArticuloEliminarControlador extends HttpServlet {

    // Método que responde a peticiones GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se obtiene el parámetro "id" desde la URL
        String idParam = req.getParameter("id");

        // Verifica si el parámetro id es nulo o está vacío
        if (idParam == null || idParam.trim().isEmpty()) {
            // Retorna un error 400 (Bad Request) si no se encuentra el id
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro id");
            return;
        }

        try {
            // Convierte el parámetro a entero (id del artículo)
            int idArticulo = Integer.parseInt(idParam.trim());

            // Recupera la conexión desde el atributo del request (probablemente seteado por un filtro)
            Connection conn = (Connection) req.getAttribute("conn");

            // Crea una instancia del servicio de artículos con la conexión
            ArticuloService service = new ArticuloServiceJdbcImplements(conn);

            // Llama al método eliminar pasando el id del artículo
            service.eliminar(idArticulo); // alterna entre 1 y 0

            // Redirige al usuario a la página de listado de artículos
            resp.sendRedirect(req.getContextPath() + "/articulo?accion=listar");

        } catch (NumberFormatException e) {
            // Si el id no es un número válido, retorna error 400
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El id no es un número válido");
        } catch (Exception e) {
            // Cualquier otro error lanza un 500 (Internal Server Error)
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar artículo");
        }
    }
}