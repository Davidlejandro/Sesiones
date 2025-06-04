package global.Repository;

import java.sql.SQLException;
import java.util.List;

// Interfaz genérica para operaciones CRUD sobre artículos
public interface RepositoryArticulo<Articulo> {

    // Devuelve una lista de todos los artículos activos (o todos, según implementación)
    List<Articulo> lista() throws SQLException;

    // Busca un artículo por su ID único
    Articulo porId(Long idArticulo) throws SQLException;

    // Guarda un nuevo artículo o actualiza uno existente
    void guardar(Articulo articulo) throws SQLException;

    // Elimina un artículo por su ID (en implementaciones con eliminación lógica puede cambiar solo el estado)
    void eliminar(Integer idArticulo) throws SQLException;
}