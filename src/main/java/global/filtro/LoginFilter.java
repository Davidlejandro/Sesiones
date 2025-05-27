package global.filtro;

import global.controllers.LoginServlet;
import global.service.LoginService;
import global.service.LoginServiceSessionImplement;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebFilter({"/productos", "/Jastinpropapurre"})

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //1) Necesito traer el nombre del usuario
        LoginService service = new LoginServiceSessionImplement();
        Optional<String> username = service.getUserName((HttpServletRequest) request);

        //2) Realizamos una condicional para ver si está presente el nombre del user
        //Verificamos si el nombre del usuario está presente
        if (username.isPresent()) {
            chain.doFilter(request, response);//
            //3) Caso contrario, mandará un error
        } else {
            ((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "no estás autorizado, safa");
        }
    }
}
