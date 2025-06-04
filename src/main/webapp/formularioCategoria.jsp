<%--
  Created by IntelliJ IDEA.
  User: ADMIN-ITQ
  Date: 28/5/2025
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, global.models.Categoria" %>
<%
  // Obtener errores y categoría del request
  Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
  if (errores == null) {
    errores = new HashMap<>();
  }

  Categoria categoria = (Categoria) request.getAttribute("categoria");
  if (categoria == null) {
    categoria = new Categoria();
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>Formulario Categoria</title>
</head>
<body>
<h1>Formulario Categoria</h1>
<div>
  <form action="<%=request.getContextPath()%>/categoria" method="post">
    <input type="hidden" name="idCategoria" value="<%= categoria.getIdCategoria() != null ? categoria.getIdCategoria() : "" %>">

    <div>
      <label for="nombre">Ingrese el nombre de categoría</label><br>
      <input type="text" id="nombre" name="nombre" value="<%= categoria.getNombre() != null ? categoria.getNombre() : "" %>">
      <br>
      <span style="color:red"><%= errores.get("nombre") != null ? errores.get("nombre") : "" %></span>
    </div>

    <div>
      <label for="descripcion">Ingrese la descripción</label><br>
      <input type="text" id="descripcion" name="descripcion" value="<%= categoria.getDescripcion() != null ? categoria.getDescripcion() : "" %>">
      <br>
      <span style="color:red"><%= errores.get("descripcion") != null ? errores.get("descripcion") : "" %></span>
    </div>

    <div>
      <input type="submit" value="<%=(categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0) ? "Editar" : "Crear" %>">
    </div>
  </form>
</div>
</body>
</html>