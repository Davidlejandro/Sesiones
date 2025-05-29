package global.controllers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import global.models.Categoria;
import global.service.CategoriaService;
import global.service.CategoriaServiceJdbcImplements;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/categoria/form")
public class CategoriaFormControlador extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException,
            IOException {
        //traemos la conexión a la base de datos
        Connection conn = (Connection) req.
                getAttribute("conn");
        CategoriaService service = new
                CategoriaServiceJdbcImplements(conn);
        Long id;
        //Validamos que el campo ingresado sea un
        //número
        try{
            //En la variable id guardamos lo que
            //estamos mapeano por el mpetodo get idCategoria
            id=Long.parseLong(req.getParameter("idCategoria"));
        }catch(NumberFormatException e){
            id=0L;
        }

        //Creamos un objeto Categoria vacio
        Categoria categorias = new Categoria();
        //Verificamos si el id > 0
        if (id>0){
            //Creamos una variable de tipo optional
            //para obtener la categoria por id
            Optional<Categoria> optionalCategoria=
                    service.porId(id);
            //Si la variable optional esta presente
            //obtenemos todos los valores
            if(optionalCategoria.isPresent()){
                categorias=optionalCategoria.get();
            }
        }
        //Seteamos loa atributos en el alcance de
        //request
        req.setAttribute("categorias", categorias);
        getServletContext().getRequestDispatcher("/formularioCategoria.jsp").forward(req,resp);
    }
    //Sobreescribimos el mpetodo doPost
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException,
            IOException {
        Connection conn = (Connection) req.
                getAttribute("conn");
        CategoriaService service= new
                CategoriaServiceJdbcImplements(conn);
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        //Obtenemos el idCategoria
        Long idCategoria;
        try{
            idCategoria=Long.parseLong(req.getParameter("idCategoria"));
        }catch(NumberFormatException e){
            idCategoria=0L;
        }

        Categoria categoria=new Categoria();
        categoria.setIdCategoria(idCategoria);
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        service.guardar(categoria);
        //Redireccionamos al listado para no nos
        //ejecute el metodo doPost
        resp.sendRedirect(req.getContextPath()+"/categoria");
            }
}