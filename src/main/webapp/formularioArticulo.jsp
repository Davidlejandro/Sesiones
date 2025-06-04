<%--
  Created by IntelliJ IDEA.
  User: David Ruiz
  Date: 3/6/2025
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%-- formularioArticulo.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, global.models.Articulo" %>
<%
  Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
  if (errores == null) {
    errores = new HashMap<>();
  }
  Articulo articulo = (Articulo) request.getAttribute("articulo");
  if (articulo == null) {
    articulo = new Articulo();
  }
%>

<form action="<%= request.getContextPath() %>/articulo" method="post">
  <input type="hidden" name="idArticulo" value="<%= articulo.getIdArticulo() != null ? articulo.getIdArticulo() : "" %>">

  <label for="idCategoria">IdCategoría:</label>
  <input type="number" id="idCategoria" name="idCategoria" value="<%= articulo.getIdCategoria() != null ? articulo.getIdCategoria() : "" %>">
  <span style="color:red"><%= errores.get("idCategoria") != null ? errores.get("idCategoria") : "" %></span>

  <br>

  <label for="codigo">Código:</label>
  <input type="text" id="codigo" name="codigo" value="<%= articulo.getCodigo() != null ? articulo.getCodigo() : "" %>">
  <span style="color:red"><%= errores.get("codigo") != null ? errores.get("codigo") : "" %></span>

  <br>

  <label for="nombre">Nombre:</label>
  <input type="text" id="nombre" name="nombre" value="<%= articulo.getNombre() != null ? articulo.getNombre() : "" %>">
  <span style="color:red"><%= errores.get("nombre") != null ? errores.get("nombre") : "" %></span>

  <br>

  <label for="stock">Stock:</label>
  <input type="number" id="stock" name="stock" value="<%= articulo.getStock() != null ? articulo.getStock() : "" %>">
  <span style="color:red"><%= errores.get("stock") != null ? errores.get("stock") : "" %></span>

  <br>

  <label for="descripcion">Descripción:</label>
  <input type="text" id="descripcion" name="descripcion" value="<%= articulo.getDescripcion() != null ? articulo.getDescripcion() : "" %>">

  <br>

  <label for="imagen">Imagen:</label>
  <input type="text" id="imagen" name="imagen" value="<%= articulo.getImagen() != null ? articulo.getImagen() : "" %>">

  <br>

  <input type="submit" value="<%= (articulo.getIdArticulo() != null && articulo.getIdArticulo() > 0) ? "Editar" : "Crear" %>">
</form>