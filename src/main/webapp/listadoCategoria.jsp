<%--
  Created by IntelliJ IDEA.
  User: David Ruiz
  Date: 28/5/2025
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import= "global.models.*, java.util.*" %>
<%
    // Se obtienen del request: la lista de categorías y el nombre del usuario
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>

<html>

<head>
    <title>Listado Categoría</title>
</head>

<body>
<%
    if(username.isPresent()){%>
<div style="color:blue;"> Hola <%= username.get()%>, bienvenido a la aplicación</div>
<div><p><a href="${pageContext.request.contextPath}/categoria/form">Ingrese el producto</a></p></div>
<%}%>

<h1>Listado Categoría</h1>
<table>
    <thead>
    <th>Id Categoría</th>
    <th>Nombre</th>
    <th>Descripción</th>
    <th>Condición</th>
    <th>Acciones</th>
    </thead>

    <%
        for (Categoria cat : categorias) {  %>
    <tbody>
    <td><%=cat.getIdCategoria()%> </td >
    <td><%=cat.getNombre()%> </td >
    <td><%=cat.getDescripcion()%> </td >
    <td><%=cat.getCondicion()%> </td>
    <td><a href="">Editar</a></td>
    <td><a href="">Activar o Desactivar</a></td>
    </tbody>
    <% } %>



</table>

</body>

</html>