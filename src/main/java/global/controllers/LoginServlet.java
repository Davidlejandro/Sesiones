package global.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import global.service.LoginService;
import global.service.LoginServiceSessionImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

// Definición del Servlet que manejará las rutas /login y /login.html
@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {
    // Credenciales fijas para autenticación (en producción usar base de datos)
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    // Método para manejar solicitudes GET (cuando se accede a la página)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //Implementamos el objeto de tipo sesión
        LoginService auth = new LoginServiceSessionImplement();
        //Creamos una variable optional para obtener el nombre del usuario
        Optional<String> usernameOptional= auth.getUserName(req);

        // Si existe la cookie (usuario ya autenticado)
        if (usernameOptional.isPresent()) {
            // 2) Si existe, nos mandará directamente al Main.jsp
            getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
        } else {
            // Si no hay cookie, mostrar el formulario de login (JSP)
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    // Método para manejar solicitudes POST (envío del formulario de login)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String username = req.getParameter("username"); //obtenemos el user del formulario
        String password = req.getParameter("password"); //obtenemos el password del formulario

        // Validar credenciales
        if (username.equals(USERNAME) && password.equals(PASSWORD)) { //Si las credenciales son iguales
            //1) Creamos la sesión
            HttpSession session = req.getSession();
            //2) Seteo los valores de la sesión
            session.setAttribute("username", username);

            // Redirigir a la página de login (mostrará mensaje de bienvenida)
            resp.sendRedirect("login.html");
        } else {
            // Si credenciales son inválidas, enviar error HTTP 401 (No autorizado)
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no tiene acceso");
        }
    }
}