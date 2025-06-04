package global.controllers;

// Importaciones necesarias para el uso de servlets y clases auxiliares
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

// Servlet que responde a la URL /articulo/form
@WebServlet("/articulo/form")
public class ArticuloFormControlador extends HttpServlet {

    // Maneja las peticiones GET (por ejemplo, al acceder al formulario)
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException,
            IOException {
        // Recupera la conexión a la base de datos desde el request (establecida previamente)
        Connection conn = (Connection) req.getAttribute("conn");

        // Crea el servicio de artículo con implementación JDBC
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);

        Integer id;
        try {
            // Intenta obtener el id del artículo desde los parámetros del request
            id = Integer.parseInt(req.getParameter("idArticulo"));
        } catch (NumberFormatException e) {
            // Si no se puede convertir, se asigna 0 (nuevo artículo)
            id = 0;
        }

        // Se inicializa un objeto Articulo vacío
        Articulo articulo = new Articulo();

        // Si el id es mayor que 0, se trata de una edición, no creación
        if (id > 0) {
            // Busca el artículo por ID
            Optional<Articulo> optionalArticulo = service.porId(id.longValue());
            if (optionalArticulo.isPresent()) {
                // Si se encuentra, se usa el artículo existente
                articulo = optionalArticulo.get();
            }
        }

        // Se añade el objeto artículo al request para usarlo en el JSP
        req.setAttribute("articulo", articulo);

        // Se redirige al JSP del formulario
        getServletContext().getRequestDispatcher("/formularioArticulo.jsp").forward(req, resp);
    }

    // Maneja las peticiones POST (cuando se envía el formulario)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se recupera la conexión desde el request
        Connection conn = (Connection) req.getAttribute("conn");

        // Se crea la instancia del servicio de artículo
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);

        Integer idArticulo;
        try {
            // Se intenta obtener el id del artículo desde el formulario
            idArticulo = Integer.parseInt(req.getParameter("idArticulo"));
        } catch (NumberFormatException e) {
            // Si falla, se asume que es un nuevo artículo
            idArticulo = 0;
        }

        Long idCategoria;
        try {
            // Se obtiene el id de la categoría
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            // Si no es válido, se asigna 0L por defecto
            idCategoria = 0L;
        }

        // Se obtienen los demás campos del formulario
        String codigo = req.getParameter("codigo");
        String nombre = req.getParameter("nombre");

        int stock;
        try {
            // Intenta convertir el valor del stock a entero
            stock = Integer.parseInt(req.getParameter("stock"));
        } catch (NumberFormatException e) {
            // Si no es válido, asigna 0 por defecto
            stock = 0;
        }

        String descripcion = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");

        // Se crea una instancia de Articulo con todos los datos del formulario
        Articulo articulo = new Articulo();
        articulo.setIdArticulo(idArticulo);
        articulo.setIdCategoria(idCategoria);
        articulo.setCodigo(codigo);
        articulo.setNombre(nombre);
        articulo.setStock(stock);
        articulo.setDescripcion(descripcion);
        articulo.setImagen(imagen);
        articulo.setCondicion(1); // Estado activo (1)

        try {
            // Se guarda el artículo (insertar o actualizar)
            service.guardar(articulo);

            // Redirige al listado de artículos
            resp.sendRedirect(req.getContextPath() + "/articulo");
        } catch (Exception e) {
            // En caso de error, imprime el stacktrace en consola
            e.printStackTrace();

            // Retorna un error 500 al cliente
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el artículo");
        }
    }
}