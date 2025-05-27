package global.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginServiceSessionImplement implements LoginService {
    //1) sobreescribir el método de getUserName

    @Override
    public Optional<String> getUserName(HttpServletRequest request) {
        //Obtenemos la sesión
        HttpSession session = request.getSession();
        //Convierto los datos de la sesión en un string
        //Variable de tipo string que va a traer el atributo "username"
        String username = (String)session.getAttribute("username");
        //Creamos una condición en la cual válido si al obtener el nombre del usuario es distinto del null
        //obtengo username. Caso contrario devuelvo la sesión vacía
        if(username!= null){
            //Si el usuario no es nulo, devuelvo el name del usuario
            return Optional.of(username);
        }
        return Optional.empty();
    }
}
