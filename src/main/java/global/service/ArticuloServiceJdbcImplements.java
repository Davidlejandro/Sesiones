package global.service;

import global.Repository.ArticuloRepositoryJdbcImplement;
import global.models.Articulo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ArticuloServiceJdbcImplements implements ArticuloService {

    // Repositorio para manejar operaciones JDBC
    private ArticuloRepositoryJdbcImplement repositoryJdbc;

    // Constructor que recibe una conexión
    public ArticuloServiceJdbcImplements(Connection conn) {
        this.repositoryJdbc = new ArticuloRepositoryJdbcImplement(conn);
    }

    @Override
    public List<Articulo> listar() {
        try {
            return repositoryJdbc.lista();
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Articulo> porId(Long idArticulo) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(idArticulo));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Articulo articulo) {
        try {
            repositoryJdbc.guardar(articulo);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Integer idArticulo) {
        try {
            repositoryJdbc.eliminar(idArticulo);
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}