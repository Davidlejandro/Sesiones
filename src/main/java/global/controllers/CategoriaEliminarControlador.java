package global.controllers;

import global.service.CategoriaService;
import global.service.CategoriaServiceJdbcImplements;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/categoria/eliminar")
public class CategoriaEliminarControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplements(conn);

        try {
            Long idCategoria = Long.parseLong(req.getParameter("idCategoria"));
            service.eliminar(idCategoria); // ← cambia condición a 0 (inactivo)
        } catch (NumberFormatException e) {
            // Puedes registrar el error si lo deseas
        }

        resp.sendRedirect(req.getContextPath() + "/categoria");
    }
}