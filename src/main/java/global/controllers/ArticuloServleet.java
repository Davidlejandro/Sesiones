package global.controllers;

// Importación de la clase Articulo y servicios necesarios
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

// Define la ruta del servlet para manejar las peticiones a "/articulo"
@WebServlet("/articulo")
public class ArticuloServleet extends HttpServlet {

    // Maneja las peticiones GET (por ejemplo, al listar artículos)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Obtiene la conexión desde el atributo del request
            Connection conn = (Connection) req.getAttribute("conn");

            // Se instancia el servicio de artículo con la conexión
            ArticuloService service = new ArticuloServiceJdbcImplements(conn);

            // Se obtiene la lista de artículos
            List<Articulo> articulos = service.listar();

            // Se obtiene el nombre del usuario de la sesión (opcional)
            LoginService auth = new LoginServiceSessionImplement();
            Optional<String> userName = auth.getUserName(req);

            // Se agregan los datos al request para pasarlos al JSP
            req.setAttribute("articulos", articulos);
            req.setAttribute("username", userName);

            // Se redirige al JSP que lista los artículos
            getServletContext().getRequestDispatcher("/listadoArticulo.jsp").forward(req, resp);
        } catch (Exception e) {
            // Si hay un error, se envía un error 500 al cliente
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar la página");
        }
    }

    // Maneja las peticiones POST (por ejemplo, al guardar un artículo)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtiene la conexión desde el request
        Connection conn = (Connection) req.getAttribute("conn");

        // Instancia del servicio de artículos
        ArticuloService service = new ArticuloServiceJdbcImplements(conn);

        // Obtención de parámetros del formulario
        String idArticuloStr = req.getParameter("idArticulo");
        String idCategoriaStr = req.getParameter("idCategoria");
        String codigo = req.getParameter("codigo");
        String nombre = req.getParameter("nombre");
        String stockStr = req.getParameter("stock");
        String descripcion = req.getParameter("descripcion");
        String imagen = req.getParameter("imagen");

        // Mapa para guardar errores de validación
        Map<String, String> errores = new HashMap<>();

        // Validaciones básicas de campos obligatorios
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

        // Si hay errores, vuelve a mostrar el formulario con los datos y errores
        if (!errores.isEmpty()) {
            Articulo articulo = new Articulo();
            try {
                // Intenta parsear los valores si están presentes
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
                // Se ignoran errores de conversión porque ya se validaron antes
            }

            // Se reenvían los datos y errores al formulario
            req.setAttribute("errores", errores);
            req.setAttribute("articulo", articulo);
            getServletContext().getRequestDispatcher("/formularioArticulo.jsp").forward(req, resp);
            return;
        }

        // Si no hay errores, se guarda o actualiza el artículo
        try {
            Articulo articulo = new Articulo();

            // Si viene un ID, se está editando un artículo
            if (idArticuloStr != null && !idArticuloStr.isEmpty()) {
                articulo.setIdArticulo(Integer.parseInt(idArticuloStr));
            }

            // Se asignan los datos al objeto
            articulo.setIdCategoria(Long.parseLong(idCategoriaStr));
            articulo.setCodigo(codigo.trim());
            articulo.setNombre(nombre.trim());
            articulo.setStock(Integer.parseInt(stockStr));
            articulo.setDescripcion(descripcion != null ? descripcion.trim() : "");
            articulo.setImagen(imagen != null ? imagen.trim() : "");

            // Se guarda en la base de datos
            service.guardar(articulo);

            // Redirige al listado después de guardar
            resp.sendRedirect(req.getContextPath() + "/articulo");
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error completo en consola
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el artículo");
        }
    }
}