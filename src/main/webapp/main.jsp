<%--
  Created by IntelliJ IDEA.
  User: David Ruiz
  Date: 27/5/2025
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<!-- 1. ENCABEZADO JSP Y CONFIGURACIONES INICIALES -->
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>           <!-- Importamos listas de Java -->
<%@page import="global.service.ProductoServiceImplement"%> <!-- Nuestra clase de servicio -->
<%@page import="global.models.Productos"%> <!-- Nuestra clase modelo Productos -->

<!-- 2. CÓDIGO JAVA PARA OBTENER LOS PRODUCTOS -->
<%
    // Crear una instancia del servicio de productos
    ProductoServiceImplement productoService = new ProductoServiceImplement();

    // Obtener la lista de productos llamando al método listar() del servicio
    List<Productos> productos = productoService.listar();
    // Esto sería como pedirle a un asistente: "Oye, tráeme todos los productos que tengas registrados"
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Página Principal</title>
    <link rel="stylesheet" href="CSS/Main.css"> <!-- Enlaza nuestro archivo CSS -->
</head>
<body>

<!-- 3. BARRA DE NAVEGACIÓN SUPERIOR -->
<div class="navbar">
    <h1>Registro Compra - Venta</h1> <!-- Título principal de la página -->
</div>

<!-- 4. CONTENIDO PRINCIPAL -->
<div class="container">
    <!-- BARRA LATERAL (MENÚ) -->
    <div class="sidebar">
        <ul>
            <li><a href="#">Productos</a></li> <!-- Enlace activo actual -->
            <li><a href="#">Valor x</a></li> <!-- Otros enlaces de ejemplo -->
            <li><a href="#">Valor x</a></li>
            <li><a href="#">Valor x</a></li>
        </ul>
    </div>

    <!-- CONTENIDO CENTRAL -->
    <div class="main-content">
        <h2>Productos</h2> <!-- Subtítulo de la sección -->

        <!-- 5. TABLA DE PRODUCTOS -->
        <table>
            <thead> <!-- Encabezados de la tabla -->
            <tr>
                <th>ID PRODUCTO</th>
                <th>NOMBRE</th>
                <th>CATEGORÍA</th>
                <th>PRECIO</th>
                <th>ACCIONES</th>
            </tr>
            </thead>
            <tbody>
            <%
                // Hacemos un for each para recorrer cada producto en la lista
                for (Productos p : productos) {
                    // Para cada producto (p) en la lista de productos
            %>
            <tr> <!-- Fila de la tabla -->
                <td><%= p.getId() %></td> <!-- Mostrar ID del producto -->
                <td><%= p.getNombre() %></td> <!-- Mostrar nombre -->
                <td><%= p.getTipo() %></td> <!-- Mostrar categoría/tipo -->
                <td>$<%= String.format("%.2f", p.getPrecio()) %></td> <!-- Precio con 2 decimales -->
                <td>
                    <!-- Formulario para Editar -->
                    <form>
                        <input type="hidden" name="id" value="<%= p.getId() %>"> <!-- Envía el ID oculto -->
                        <input type="submit" value="Editar"> <!-- Botón Editar -->
                    </form>

                    <!-- Formulario para Eliminar -->
                    <form>
                        <input type="hidden" name="id" value="<%= p.getId() %>"> <!-- Envía el ID oculto -->
                        <input type="submit" value="Eliminar"> <!-- Botón Eliminar -->
                    </form>
                </td>
            </tr>
            <%
                } // Fin del bucle for
            %>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>