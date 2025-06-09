<%--
  Created by IntelliJ IDEA.
  User: David Ruiz
  Date: 3/6/2025
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, global.models.Articulo" %>
<%@ page import="java.util.List" %>
<html>
<head>
  <title>${titulo}</title>
  <style>
    .error-message {
      color: red;
      font-size: 12px;
      margin: 5px 0;
    }
  </style>
</head>
<body>
<h1>${titulo}</h1>
<a href="<%=request.getContextPath()%>/articulos/form">Crear Artículo</a>

<%
  List<Articulo> articulos = (List<Articulo>) request.getAttribute("articulos");
  List<String> errores = (List<String>) request.getAttribute("errores");
%>

<table border="1">
  <tr>
    <th>ID</th>
    <th>Categoría</th>
    <th>Código</th>
    <th>Nombre</th>
    <th>Stock</th>
    <th>Descripción</th>
    <th>Imagen</th>
    <th>Condición</th>
    <th>Acciones</th>
  </tr>
  <% if (articulos != null) {
    for (Articulo a : articulos) {
  %>
  <tr>
    <td><%= a.getIdArticulo() %></td>
    <td><%= a.getCategoria().getNombre() %></td>
    <td>
      <%= a.getCodigo() %>
      <% if (errores != null) {
        for (String error : errores) {
          if (error.contains(a.getNombre()) && error.contains("código")) { %>
      <div class="error-message"><%= error %></div>
      <%      }
      }
      } %>
    </td>
    <td>
      <%= a.getNombre() %>
      <% if (errores != null) {
        for (String error : errores) {
          if (error.contains(a.getNombre()) && error.contains("nombre")) { %>
      <div class="error-message"><%= error %></div>
      <%      }
      }
      } %>
    </td>
    <td>
      <%= a.getStock() %>
      <% if (errores != null) {
        for (String error : errores) {
          if (error.contains(a.getNombre()) && error.contains("Stock")) { %>
      <div class="error-message"><%= error %></div>
      <%      }
      }
      } %>
    </td>
    <td><%= a.getDescripcion() != null ? a.getDescripcion() : "" %></td>
    <td><%= a.getImagen() != null ? a.getImagen() : "" %></td>
    <td>
      <%= a.getCondicion() %>
      <% if (errores != null) {
        for (String error : errores) {
          if (error.contains(a.getNombre()) && error.contains("Condición")) { %>
      <div class="error-message"><%= error %></div>
      <%      }
      }
      } %>
    </td>
    <td>
      <a href="<%=request.getContextPath()%>/articulos/form?id=<%= a.getIdArticulo() %>">Editar</a>
      <a href="<%=request.getContextPath()%>/articulos/eliminar?id=<%= a.getIdArticulo() %>"
         onclick="return confirm('¿Está seguro que desea eliminar?');">Eliminar</a>
    </td>
  </tr>
  <%
      }
    }
  %>
</table>
</body>
</html>