/*
 *
 * Descripcion: Desarrollo de clase productos de servlet para poder mostrar los productos mediante una tabla
 * */
package global.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import global.models.Productos;
import global.service.LoginService;
import global.service.LoginServiceSessionImplement;
import global.service.ProductoService;
import global.service.ProductoServiceImplement;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

//Anotaciones
@WebServlet("/productos")
public class ProductosServlet extends HttpServlet{
    // Método para manejar solicitudes GET (cuando se accede a la página de productos)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Obtener instancia del servicio de productos
        ProductoService service = new ProductoServiceImplement();

        // 2. Obtener lista de productos desde el servicio
        List<Productos> productos = service.listar();

        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional= auth.getUserName(req);
        boolean logeo = usernameOptional.isPresent();
        // 3. Configurar el tipo de contenido de la respuesta como HTML con UTF-8
        resp.setContentType("text/html;charset=UTF-8");

        // 4. Obtener objeto PrintWriter para escribir la respuesta HTML
        PrintWriter out = resp.getWriter();

        // 5. Comenzar a generar la estructura HTML
        out.print("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");  // Especificar encoding
        out.println("<link rel=\"stylesheet\" href=\"css/estilos.css\">");
        out.println("<title>Lista de Productos</title>");  // Título de la pestaña
        out.println("</head>");
        out.println("<body>");

        // 6. Título principal de la página
        out.println("<h1>Lista de productos</h1>");
        if(logeo){
            out.println("<h3>Hola querido " + usernameOptional.get() + "</h3>");
        }
        // 7. Crear tabla para mostrar los productos
        out.println("<table border='1'>");  // Tabla con borde visible

        // 8. Encabezados de la tabla
        out.println("<tr>");  // Fila de encabezado
        out.println("<th>ID PRODUCTO</th>");
        out.println("<th>NOMBRE</th>");
        out.println("<th>CATEGORIA</th>");
        if(logeo){
            out.println("<th>PRECIO</th>");
        }
        out.println("</tr>");

        // 9. Iterar sobre la lista de productos y generar filas de la tabla
        productos.forEach(p -> {
            out.println("<tr>");  // Nueva fila por cada producto
            out.println("<td>" + p.getId() + "</td>");  // Celda con ID
            out.println("<td>" + p.getNombre() + "</td>");  // Celda con nombre
            out.println("<td>" + p.getTipo() + "</td>");  // Celda con categoría/tipo
            if(logeo){
                out.println("<td>" + p.getPrecio() + "</td>");
            }
            out.println("</tr>");
        });

        // 10. Cerrar tabla y estructura HTML
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

}

