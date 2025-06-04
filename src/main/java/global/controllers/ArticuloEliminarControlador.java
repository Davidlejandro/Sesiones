package global.controllers;

import global.service.ArticuloService;
import global.service.ArticuloServiceJdbcImplements;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/articulo/eliminar")
public class ArticuloEliminarControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro id");
            return;
        }

        try {
            int idArticulo = Integer.parseInt(idParam.trim());

            Connection conn = (Connection) req.getAttribute("conn");
            ArticuloService service = new ArticuloServiceJdbcImplements(conn);
            service.eliminar(idArticulo); // alterna entre 1 y 0

            resp.sendRedirect(req.getContextPath() + "/articulo?accion=listar");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El id no es un número válido");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar artículo");
        }
    }
}