package global.service;

import global.Repository.ArticuloRepositoryJdbcImplement;
import global.models.Articulo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// Implementación de la interfaz ArticuloService usando JDBC
public class ArticuloServiceJdbcImplements implements ArticuloService {

    // Repositorio que gestiona las operaciones con la base de datos
    private ArticuloRepositoryJdbcImplement repositoryJdbc;

    // Constructor que recibe una conexión JDBC y crea una instancia del repositorio
    public ArticuloServiceJdbcImplements(Connection conn) {
        this.repositoryJdbc = new ArticuloRepositoryJdbcImplement(conn);
    }

    // Devuelve una lista de todos los artículos activos
    @Override
    public List<Articulo> listar() {
        try {
            return repositoryJdbc.lista();
        } catch (SQLException e) {
            // Lanza una excepción personalizada si ocurre un error en la base de datos
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    // Busca un artículo por su ID y lo devuelve como Optional
    @Override
    public Optional<Articulo> porId(Long idArticulo) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(idArticulo));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    // Guarda un nuevo artículo o actualiza uno existente
    @Override
    public void guardar(Articulo articulo) {
        try {
            repositoryJdbc.guardar(articulo);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    // Elimina (o cambia el estado lógico) de un artículo por su ID
    @Override
    public void eliminar(Integer idArticulo) {
        try {
            repositoryJdbc.eliminar(idArticulo);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}