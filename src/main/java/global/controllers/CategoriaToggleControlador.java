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

@WebServlet("/categoria/toggle")
public class CategoriaToggleControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplements(conn);

        try {
            Long idCategoria = Long.parseLong(req.getParameter("idCategoria"));
            service.toggleCondicion(idCategoria);
        } catch (NumberFormatException e) {
            // Puedes manejar el error aquí o simplemente ignorarlo
        }

        resp.sendRedirect(req.getContextPath() + "/categoria");
    }
}