<%--
  Created by IntelliJ IDEA.
  User: David Ruiz
  Date: 3/6/2025
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, global.models.Articulo" %>
<%
  List<Articulo> articulos = (List<Articulo>) request.getAttribute("articulos");
  Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<html>
<head>
  <title>Listado Artículo</title>
</head>
<body>

<h1>Listado Artículo</h1>

<%
  if (username.isPresent()) {
%>
<div style="color: blue;">Hola, <%=username.get()%> bienvenido</div>
<div><a href="${pageContext.request.contextPath}/articulo/form">Añadir Artículo</a></div>
<%
  }
%>

<table>
  <thead>
  <tr>
    <th>ID ARTÍCULO</th>
    <th>ID CATEGORÍA</th>
    <th>CÓDIGO</th>
    <th>NOMBRE</th>
    <th>STOCK</th>
    <th>DESCRIPCIÓN</th>
    <th>IMAGEN</th>
    <th>CONDICIÓN</th>
    <% if (username.isPresent()) { %>
    <th>ACCIÓN</th>
    <% } %>
  </tr>
  </thead>
  <tbody>
  <%
    for (Articulo art : articulos) {
  %>
  <tr>
    <td><%= art.getIdArticulo() %></td>
    <td><%= art.getIdCategoria() %></td>
    <td><%= art.getCodigo() %></td>
    <td><%= art.getNombre() %></td>
    <td><%= art.getStock() %></td>
    <td><%= art.getDescripcion() %></td>
    <td><%= art.getImagen() %></td>
    <td><%= art.getCondicion() %></td>
    <% if (username.isPresent()) { %>
    <td>
      <a href="<%=request.getContextPath()%>/articulo/form?id=<%=art.getIdArticulo()%>">Editar</a>
      <a href="<%=request.getContextPath()%>/articulo/eliminar?id=<%=art.getIdArticulo()%>">Eliminar</a>
    </td>
    <% } %>
  </tr>
  <%
    }
  %>
  </tbody>
</table>

</body>
</html>
