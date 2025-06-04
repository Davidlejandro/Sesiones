package global.controllers;

import global.models.Articulo;
import global.service.ArticuloService;
import global.service.ArticuloServiceJdbcImplements;
import global.service.LoginService;
import global.service.LoginServiceSessionImplement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/articulo")
public class ArticuloServleet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection conn = (Connection) req.getAttribute("conn");
            ArticuloService service = new ArticuloServiceJdbcImplements(conn);
            List<Articulo> articulos = service.listar();

            LoginService auth = new LoginServiceSessionImplement();
            Optional<String> userName = auth.getUserName(req);

            req.setAttribute("articulos", articulos);
            req.setAttribute("username", userName);

            getServletContext().getRequestDispatcher("/listadoArticulo.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar la página");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);

        // Recoger parámetros
        String idArticuloStr = req.getParameter("idArticulo");
        String idCategoriaStr = req.getParameter("idCategoria");
        String codigo = req.getParameter("codigo");
        String nombre = req.getParameter("nombre");
        String stockStr = req.getParameter("stock");
        String descripcion = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");

        Map<String, String> errores = new HashMap<>();

        // Validaciones simples
        if (idCategoriaStr == null || idCategoriaStr.isEmpty()) {
            errores.put("idCategoria", "La categoría es obligatoria");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            errores.put("codigo", "El código es obligatorio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio");
        }
        if (stockStr == null || stockStr.isEmpty()) {
            errores.put("stock", "El stock es obligatorio");
        }
        // Puedes agregar más validaciones si quieres

        if (!errores.isEmpty()) {
            // Enviar errores y valores para mostrar en el formulario
            Articulo articulo = new Articulo();
            try {
                if (idArticuloStr != null && !idArticuloStr.isEmpty()) {
                    articulo.setIdArticulo(Integer.parseInt(idArticuloStr));
                }
                articulo.setIdCategoria(Long.parseLong(idCategoriaStr));
                articulo.setCodigo(codigo);
                articulo.setNombre(nombre);
                articulo.setDescripcion(descripcion);
                articulo.setImagen(imagen);
                articulo.setStock(stockStr != null && !stockStr.isEmpty() ? Integer.parseInt(stockStr) : 0);
            } catch (NumberFormatException e) {
                // Ignorar, ya validamos antes
            }

            req.setAttribute("errores", errores);
            req.setAttribute("articulo", articulo);
            getServletContext().getRequestDispatcher("/formularioArticulo.jsp").forward(req, resp);
            return;
        }

        // Si pasa las validaciones, guardar o actualizar
        try {
            Articulo articulo = new Articulo();
            if (idArticuloStr != null && !idArticuloStr.isEmpty()) {
                articulo.setIdArticulo(Integer.parseInt(idArticuloStr));
            }
            articulo.setIdCategoria(Long.parseLong(idCategoriaStr));
            articulo.setCodigo(codigo.trim());
            articulo.setNombre(nombre.trim());
            articulo.setStock(Integer.parseInt(stockStr));
            articulo.setDescripcion(descripcion != null ? descripcion.trim() : "");
            articulo.setImagen(imagen != null ? imagen.trim() : "");

            service.guardar(articulo);
            resp.sendRedirect(req.getContextPath() + "/articulo");
        } catch (Exception e) {
            e.printStackTrace(); // 👈 MOSTRARÁ EL ERROR EN LA CONSOLA
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el artículo");
        }
    }
}