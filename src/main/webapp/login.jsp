<!--
Desarrollo de HTML en archivo jsp  para la visualizacion de el ingreso al ingreso de los datos
del cliente al ingresar a la ruta del jsp.-->

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> <!-- Define el tipo de documento -->
<html lang="en"> <!-- Inicio del documento HTML con idioma inglés -->
<head>
    <meta charset="UTF-8"> <!-- Codificación de caracteres -->
    <title>Login</title> <!-- Título de la página -->
</head>
<body>
<h1>Login de usuario</h1> <!-- Encabezado principal -->
<div>
    <form action="/Sesiones/login" method="post"> <!-- Formulario que envía datos por POST -->
        <div>
            <lable for="username">Nombre de usuarios:</lable> <!-- Etiqueta para el usuario (error de ortografía: label) -->
            <div>
                <input type="text" name="username" id="username"> <!-- Campo para el nombre de usuario -->
            </div>
        </div>
        <div>
            <label for="pass">Password:</label> <!-- Etiqueta para la contraseña (error de ortografía: label) -->
            <div>
                <input type="password" name="password" id="pass"> <!-- Campo para la contraseña -->
            </div>
        </div>
        <div>
            <input type="submit" value="Enviar"> <!-- Botón para enviar el formulario -->
        </div>
    </form>
</div>
</body>
</html>