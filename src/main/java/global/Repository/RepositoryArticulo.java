package global.Repository;

import global.models.Articulo;

import java.sql.SQLException;
import java.util.List;

public interface RepositoryArticulo<Articulo> {

    // Listar todos los artículos
    List<Articulo> lista() throws SQLException;

    // Buscar un artículo por su ID
    Articulo porId(Long idArticulo) throws SQLException;

    // Insertar o actualizar un artículo
    void guardar(Articulo articulo) throws SQLException;

    // (Opcional) "Eliminar" un artículo - si usas eliminaciones lógicas, puede mantenerse
    void eliminar(Integer idArticulo) throws SQLException;
}