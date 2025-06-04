package global.service;

import global.models.Articulo;

import java.util.List;
import java.util.Optional;

// Interfaz que define las operaciones del servicio para gestionar artículos
public interface ArticuloService {

    // Lista todos los artículos activos (o según la lógica del repositorio)
    List<Articulo> listar();

    // Busca un artículo por su ID, devuelve Optional para manejar ausencia de datos
    Optional<Articulo> porId(Long idArticulo);

    // Guarda un nuevo artículo o actualiza uno existente
    void guardar(Articulo articulo);

    // Elimina (o desactiva lógicamente) un artículo por su ID
    void eliminar(Integer idArticulo);
}