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
@WebServlet("/articulos/eliminar")
public class ArticuloEliminarControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);

        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                Long id = Long.parseLong(idStr);
                service.eliminar(id);
            } catch (NumberFormatException e) {
                // Manejar error si el ID no es válido
            }
        }
        resp.sendRedirect(req.getContextPath() + "/articulos");
    }
}