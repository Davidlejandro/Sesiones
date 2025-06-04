<%--
  Created by IntelliJ IDEA.
  User: ADMIN-ITQ
  Date: 28/5/2025
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, global.models.Categoria" %>
<%
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<html>
<head>
    <title>Listado Categoria</title>
</head>
<body>

<h1>Listado Categoria</h1>
<%
    if (username.isPresent()) {%>
<div style="color: blue;">Hola, <%=username.get()%> bienvenido</div>
<div><a href="${pageContext.request.contextPath}/categoria/form">Añadir Categorias</a></div>

<%}%>

<table>
    <thead>
    <th>ID CATEGORIA</th>
    <th>NOMBRE</th>
    <th>DESCRIPCIÓN</th>
    <th>CONDICIÓN</th>
    <th>ACCIÓN</th>
    </thead>
    <%
        for (Categoria cate : categorias) {%>
    <tbody>
    <td><%=cate.getIdCategoria()%></td>
    <td><%=cate.getNombre()%></td>
    <td><%=cate.getDescripcion()%></td>
    <td><%=cate.getCondicion()%></td>
    <%if(username.isPresent()){%>
    <td>
        <a href="<%= request.getContextPath() %>/categoria/form?id=<%= cate.getIdCategoria() %>">Editar</a>
        <a href="<%=request.getContextPath()%>/categoria/eliminar?idCategoria=<%=cate.getIdCategoria()%>">
            <%= (cate.getCondicion() == 1) ? "Desactivar" : "Activar" %>
        </a>
    </td>
    <%}%>
    </tbody>

    <% }%>

</table>

</body>
</html>