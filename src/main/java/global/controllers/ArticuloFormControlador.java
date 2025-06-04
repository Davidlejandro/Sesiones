package global.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import global.models.Articulo;
import global.service.ArticuloService;
import global.service.ArticuloServiceJdbcImplements;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/articulo/form")
public class ArticuloFormControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException,
            IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);

        Integer id;
        try {
            id = Integer.parseInt(req.getParameter("idArticulo"));
        } catch (NumberFormatException e) {
            id = 0;
        }

        Articulo articulo = new Articulo();
        if (id > 0) {
            Optional<Articulo> optionalArticulo = service.porId(id.longValue());
            if (optionalArticulo.isPresent()) {
                articulo = optionalArticulo.get();
            }
        }

        req.setAttribute("articulo", articulo);
        getServletContext().getRequestDispatcher("/formularioArticulo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);

        Integer idArticulo;
        try {
            idArticulo = Integer.parseInt(req.getParameter("idArticulo"));
        } catch (NumberFormatException e) {
            idArticulo = 0;
        }

        Long idCategoria;
        try {
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            idCategoria = 0L;
        }

        String codigo = req.getParameter("codigo");
        String nombre = req.getParameter("nombre");

        int stock;
        try {
            stock = Integer.parseInt(req.getParameter("stock"));
        } catch (NumberFormatException e) {
            stock = 0;
        }

        String descripcion = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");

        Articulo articulo = new Articulo();
        articulo.setIdArticulo(idArticulo);
        articulo.setIdCategoria(idCategoria);
        articulo.setCodigo(codigo);
        articulo.setNombre(nombre);
        articulo.setStock(stock);
        articulo.setDescripcion(descripcion);
        articulo.setImagen(imagen);
        articulo.setCondicion(1);

        try {
            service.guardar(articulo);
            resp.sendRedirect(req.getContextPath() + "/articulo");
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error completo en consola para que puedas ver qué pasa
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el artículo");
        }
    }
}