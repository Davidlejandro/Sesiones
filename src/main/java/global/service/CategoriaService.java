package global.service;

import global.models.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listar();
    Optional<Categoria> porId(Long idCategoria);
    void guardar(Categoria categoria);
    void eliminar(Long idCategoria);
    void toggleCondicion(Long idCategoria);
}

