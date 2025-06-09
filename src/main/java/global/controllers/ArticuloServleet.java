package global.controllers;


import global.models.Articulo;
import global.service.ArticuloService;
import global.service.ArticuloServiceJdbcImplements;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Connection;
import java.util.*;

@WebServlet("/articulos")
public class ArticuloServleet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);
        List<Articulo> articulos = service.listar();
        List<String> errores = new ArrayList<>();

        // Validaciones globales
        for (Articulo articulo : articulos) {
            if (articulo.getStock() < 0) {
                errores.add("Artículo " + articulo.getNombre() + ": Stock no puede ser negativo");
            }
            if (articulo.getCondicion() != 0 && articulo.getCondicion() != 1) {
                errores.add("Artículo " + articulo.getNombre() + ": Condición debe ser 0 o 1");
            }
            if (articulo.getNombre() == null || articulo.getNombre().trim().isEmpty()) {
                errores.add("Hay artículos sin nombre");
            }
            if (articulo.getCodigo() == null || articulo.getCodigo().trim().isEmpty()) {
                errores.add("Hay artículos sin código");
            }
        }

        req.setAttribute("errores", errores);
        req.setAttribute("articulos", articulos);
        req.setAttribute("titulo", "Listado de Artículos");
        getServletContext().getRequestDispatcher("/listar-articulos.jsp").forward(req, resp);
    }
}