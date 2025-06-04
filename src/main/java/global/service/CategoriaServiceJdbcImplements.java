package global.service;

import global.Repository.CategoriaRepositoryJdbcImplement;
import global.models.Categoria;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaServiceJdbcImplements implements CategoriaService {

    //Creamos una variable de tipo CategoriaRepositoryJdbcImplement
    private CategoriaRepositoryJdbcImplement repositoryJdbc;
    //Creamos una variable de tipo conexion
    public CategoriaServiceJdbcImplements(Connection conn) {
        this.repositoryJdbc= new CategoriaRepositoryJdbcImplement(conn);
    }
    @Override
    public List<Categoria> listar() {
        try {
            return repositoryJdbc.listar();
        }catch ( SQLException throwables){
            throw new ServiceJdbcException(throwables.getMessage(),throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> porId(Long idCategoria) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(idCategoria));
        }catch ( SQLException throwables){
            throw new ServiceJdbcException(throwables.getMessage(),throwables.getCause());
        }
    }

    @Override
    public void guardar (Categoria categoria){
        try {
            repositoryJdbc.guardar(categoria);
        }catch ( SQLException throwables){
            throw new ServiceJdbcException(throwables.getMessage(),throwables.getCause());
        }
    }
    @Override
    public void eliminar(Long idCategoria) {
        try {
            repositoryJdbc.eliminar(idCategoria);
        }catch (SQLException throwables){
            throw new ServiceJdbcException(throwables.getMessage(),throwables.getCause());
        }
    }
}

