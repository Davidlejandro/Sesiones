package global.service;

import global.models.Articulo;

import java.util.List;
import java.util.Optional;

public interface ArticuloService {
    List<Articulo> listar();
    Optional<Articulo> porId(Long idArticulo);
    void guardar(Articulo articulo);
    void eliminar(Integer idArticulo);
}