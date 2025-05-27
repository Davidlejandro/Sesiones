/*
   Clase con la cual vamos a crear un metodo lista donde se van a obtener todos los productos
 * en una lista cada uno de los atributos de la clase mediante un metodo.*/

package global.service;

import global.models.Productos; // Importa la clase Producto del paquete models
import java.util.List; // Importa la interfaz List para manejar listas

public interface ProductoService { // Declara la interfaz ProductoService
    List<Productos> listar(); // Método abstracto que retorna una lista de productos
}

