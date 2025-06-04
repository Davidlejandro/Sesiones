package global.service;

import global.models.Categoria;

import java.util.List;
import java.util.Optional;

// Interfaz para definir las operaciones del servicio de Categorías
public interface CategoriaService {

    // Devuelve la lista completa de categorías
    List<Categoria> listar();

    // Busca una categoría por su ID, devuelve un Optional para manejar ausencia
    Optional<Categoria> porId(Long idCategoria);

    // Guarda una nueva categoría o actualiza una existente
    void guardar(Categoria categoria);

    // Elimina una categoría dado su ID (puede ser eliminación lógica)
    void eliminar(Long idCategoria);
}