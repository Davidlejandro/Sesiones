package global.controllers;

// Importaciones necesarias para el uso de servlets y clases auxiliares
import global.models.Categoria;
import global.service.CategoriaService;
import global.service.CategoriaServiceJdbcImplements;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/articulos/form")
public class ArticuloFormControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ArticuloService articuloService = new ArticuloServiceJdbcImplements(conn);
        CategoriaService categoriaService = new CategoriaServiceJdbcImplements(conn);

        List<Categoria> categorias = categoriaService.listar();
        Articulo articulo = new Articulo();

        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            articulo = articuloService.porId(id);
        }

        req.setAttribute("categorias", categorias);
        req.setAttribute("articulo", articulo);
        req.setAttribute("titulo", req.getParameter("id") != null ?
                "Editar Artículo" : "Crear Artículo");

        getServletContext().getRequestDispatcher("/form-articulo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);
        CategoriaService categoriaService = new CategoriaServiceJdbcImplements(conn);

        Map<String, String> errores = new HashMap<>();

        // Validación de categoría
        String categoriaId = req.getParameter("categoria");
        if (categoriaId == null || categoriaId.isEmpty()) {
            errores.put("categoria", "La categoría es requerida");
        }

        // Validación de código
        String codigo = req.getParameter("codigo");
        if (codigo == null || codigo.isEmpty()) {
            errores.put("codigo", "El código es requerido");
        }

        // Validación de nombre
        String nombre = req.getParameter("nombre");
        if (nombre == null || nombre.isEmpty()) {
            errores.put("nombre", "El nombre es requerido");
        }

        // Validación de stock
        String stockStr = req.getParameter("stock");
        Integer stock = null;
        if (stockStr == null || stockStr.isEmpty()) {
            errores.put("stock", "El stock es requerido");
        } else {
            try {
                stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    errores.put("stock", "El stock no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                errores.put("stock", "El stock debe ser un número válido");
            }
        }

        String descripcion = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");

        // Validación de condición
        String condicionStr = req.getParameter("condicion");
        Integer condicion = null;
        if (condicionStr == null || condicionStr.isEmpty()) {
            errores.put("condicion", "La condición es requerida");
        } else {
            try {
                condicion = Integer.parseInt(condicionStr);
                if (condicion != 0 && condicion != 1) {
                    errores.put("condicion", "La condición debe ser 0 o 1");
                }
            } catch (NumberFormatException e) {
                errores.put("condicion", "La condición debe ser un número válido");
            }
        }

        Articulo articulo = new Articulo();
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            articulo.setIdArticulo(Long.parseLong(idStr));
        }

        // Si hay errores, volver al formulario
        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            req.setAttribute("articulo", articulo);
            req.setAttribute("categorias", categoriaService.listar());
            req.setAttribute("titulo", articulo.getIdArticulo() != null ?
                    "Editar Artículo" : "Crear Artículo");
            getServletContext().getRequestDispatcher("/form-articulo.jsp")
                    .forward(req, resp);
            return;
        }

        // Si no hay errores, guardar el artículo
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(Long.parseLong(categoriaId));

        articulo.setCategoria(categoria);
        articulo.setCodigo(codigo);
        articulo.setNombre(nombre);
        articulo.setStock(stock);
        articulo.setDescripcion(descripcion);
        articulo.setImagen(imagen);
        articulo.setCondicion(condicion);

        service.guardar(articulo);
        resp.sendRedirect(req.getContextPath() + "/articulos");
    }
}