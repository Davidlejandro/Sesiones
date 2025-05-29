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
        // Creamos la conexión
        Connection conn = (Connection) req.getAttribute("conn");

        // Creamos el servicio de categorías
        CategoriaService service = new CategoriaServiceJdbcImplements(conn);
        List<Categoria> categorias = service.listar();

        // Obtenemos el nombre del usuario desde la sesión
        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> userName = auth.getUserName(req);

        // Seteamos los parámetros para la vista
        req.setAttribute("categorias", categorias);
        req.setAttribute("username", userName); // por si está vacío
        // Redireccionamos a la vista JSP
        getServletContext().getRequestDispatcher("/listadoCategoria.jsp").forward(req, resp);
    }
}
