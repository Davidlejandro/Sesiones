package global.Repository;

import java.sql.SQLException;
import java.util.List;

//Esta plantilla es para el manejo todo lo de mi base de datos


//<T> este párametro es genérico, es decir, puede tomar cualquier estado
//Permite que la interfaz sea utilizada como se desee o cualquier objeto (entidad) que se desee manejar
public interface Repository <T> {
    //La lista me permite listar todos los datos de la bDD
    List<T> listar() throws SQLException;

    //Este método me permite buscar un elemento de la BDD por su identificador
    T porId(Long idCategoria) throws SQLException;

    //Este método me permite crear un nuevo registro a la BDD
    void guardar(T t) throws SQLException;

    //Este método me permite eliminar un registro de la bdd que recibe el parÁmetro de Id
    void eliminar(Long idCategoria) throws SQLException;

}