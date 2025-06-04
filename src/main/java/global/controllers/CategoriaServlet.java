package global.controllers;

import global.models.Categoria;
import global.service.CategoriaService;
import global.service.CategoriaServiceJdbcImplements;
import global.service.LoginService;
import global.service.LoginServiceSessionImplement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection conn = (Connection) req.getAttribute("conn");
            CategoriaService service = new CategoriaServiceJdbcImplements(conn);
            List<Categoria> categorias = service.listar();

            LoginService auth = new LoginServiceSessionImplement();
            Optional<String> userName = auth.getUserName(req);

            req.setAttribute("categorias", categorias);
            req.setAttribute("username", userName);

            getServletContext().getRequestDispatcher("/listadoCategoria.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar la página");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplements(conn);

        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");

        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
            try {
                List<Categoria> categorias = service.listar();
                req.setAttribute("categorias", categorias);
            } catch (Exception e) {
                // Puedes loguear el error aquí si quieres
            }

            req.setAttribute("error", "El nombre y la descripción son obligatorios.");
            req.setAttribute("categorias", new Categoria());  // Para evitar null en JSP
            getServletContext().getRequestDispatcher("/formularioCategoria.jsp").forward(req, resp);
            return;
        }

        Long idCategoria;
        try {
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            idCategoria = 0L;
        }

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        try {
            service.guardar(categoria);
            resp.sendRedirect(req.getContextPath() + "/categoria");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar la categoría");
        }
    }
}