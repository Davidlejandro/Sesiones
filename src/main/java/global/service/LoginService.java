package global.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface LoginService {
    //Optional: Devuelve un string y dato vacío
    Optional<String> getUserName(HttpServletRequest request);

}
