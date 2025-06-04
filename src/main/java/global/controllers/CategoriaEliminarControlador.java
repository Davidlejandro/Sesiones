package global.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import global.service.CategoriaService;
import global.service.CategoriaServiceJdbcImplements;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/categoria/eliminar")
public class CategoriaEliminarControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("idCategoria");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro idCategoria");
            return;
        }

        try {
            Long idCategoria = Long.parseLong(idParam.trim());

            Connection conn = (Connection) req.getAttribute("conn");
            CategoriaService service = new CategoriaServiceJdbcImplements(conn);

            service.eliminar(idCategoria); // Aquí haces el toggle de condición
            resp.sendRedirect(req.getContextPath() + "/categoria?accion=listar");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El idCategoria no es válido");
        } catch (Exception e) {
            e.printStackTrace(); // para debugging en consola
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la categoría");
        }
    }
}
