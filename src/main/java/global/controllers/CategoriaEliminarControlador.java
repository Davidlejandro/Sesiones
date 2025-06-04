package global.controllers;

// Importación de clases necesarias para trabajar con servlets y servicios
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import global.service.CategoriaService;
import global.service.CategoriaServiceJdbcImplements;

import java.io.IOException;
import java.sql.Connection;

// Define el servlet y la ruta a la que responde
@WebServlet("/categoria/eliminar")
public class CategoriaEliminarControlador extends HttpServlet {

    // Método que maneja las peticiones GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se obtiene el parámetro "idCategoria" desde la URL
        String idParam = req.getParameter("idCategoria");

        // Se valida que el parámetro no esté vacío o nulo
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro idCategoria");
            return; // Termina el método si el parámetro no es válido
        }

        try {
            // Se convierte el parámetro a tipo Long
            Long idCategoria = Long.parseLong(idParam.trim());

            // Se obtiene la conexión desde el request (usualmente establecida por un filtro)
            Connection conn = (Connection) req.getAttribute("conn");

            // Se crea una instancia del servicio con implementación JDBC
            CategoriaService service = new CategoriaServiceJdbcImplements(conn);

            // Se llama al método eliminar del servicio (puede alternar la condición de la categoría)
            service.eliminar(idCategoria);

            // Redirige al listado de categorías después de eliminar
            resp.sendRedirect(req.getContextPath() + "/categoria?accion=listar");

        } catch (NumberFormatException e) {
            // Error si el parámetro no es un número válido
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El idCategoria no es válido");
        } catch (Exception e) {
            // Captura cualquier otra excepción y muestra en consola para depuración
            e.printStackTrace(); // para debugging en consola
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la categoría");
        }
    }
}
