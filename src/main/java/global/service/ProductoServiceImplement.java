/*
 * Fecha: 15/05/2025
 * Descripcion: Desarrollo de clase para poder mostrar mediante una retornacion la lista de la clase productos
 * el cual acompaña de un objeto donde se desarrolla un arreglo de tipo listado que contendra una cadena de
 * valores en cada atributo para visualizar esos datos.*/
package global.service;
//Importacion de librerias
import global.models.Productos;

import java.util.Arrays;
import java.util.List;

public class ProductoServiceImplement implements ProductoService { // Declara la clase que implementa la interfaz ProductoService

    @Override
    public List<Productos> listar() {
        // Retorna una lista inmutable con tres productos de ejemplo
        return Arrays.asList(new Productos(1L, "laptop", "computacion", 523.21),
                new Productos(2L, "Mouse", "inalambrico", 15.25),
                new Productos(3L, "Impresora", "tinta continua", 256.25));
    }
}





