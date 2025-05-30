package global.Repository;

import global.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//el <Categorias> es para que se cambie al estado categorias
public class CategoriaRepositoryJdbcImplement implements Repository<Categoria> {
    //1) Creamos una variable donde vamos a guardar la conexión
    private Connection conn;

    //2) Genero un constructor que recibe la conexión
    public CategoriaRepositoryJdbcImplement(Connection conn) {
        //va a traer la conexión y la guardará en el conn que está en la parte derecha del igual
        this.conn = conn;
    }



    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>(); //Creamos un nuevo objeto de tipo categoría
        try(Statement stmt = conn.createStatement(); //Esto me permite interactuar con la bdd
            ResultSet rs = stmt.executeQuery("select * from categoria")){ //Me permite realizar la consulta
            while (rs.next()) { //mientas lo siga recorriendo
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias; //retornamos la lista categorías
    }


    @Override
    public Categoria porId(Long idCategoria) throws SQLException { //Aquí está el id del método
        //Creo un objeto de tipo categoría nulo
        Categoria categoria = null;
        try(PreparedStatement stmt = conn.prepareStatement(
                "select * from categoria where idCategoria = ?")){ //Selecciona todo de categoria donde el id del método
            stmt.setLong(1, idCategoria); //Setea el valor en la columna número uno
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    categoria = getCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {
        // Declaración de la variable SQL
        String sql;

        // Determina si se trata de una actualización (UPDATE) o una inserción nueva (INSERT)
        boolean esUpdate = categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0;

        if (esUpdate) {
            // Si el ID existe, se actualiza la categoría existente
            sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE idCategoria = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNombre());
                stmt.setString(2, categoria.getDescripcion());
                stmt.setLong(3, categoria.getIdCategoria());
                stmt.executeUpdate();
            }
        } else {
            // Si el ID no existe, se inserta una nueva categoría
            // La condición se pone en 1 por defecto (activo)
            sql = "INSERT INTO categoria (nombre, descripcion, condicion) VALUES (?, ?, 1)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNombre());
                stmt.setString(2, categoria.getDescripcion());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void eliminar(Long idCategoria) throws SQLException {
        String sql = "UPDATE categoria SET condicion = 0 WHERE idCategoria = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCategoria);
            stmt.executeUpdate();
        }
    }


    @Override
    public void toggleCondicion(Long idCategoria) throws SQLException {
        String sql = "UPDATE categoria SET condicion = CASE WHEN condicion = 1 THEN 0 ELSE 1 END WHERE idCategoria = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCategoria);
            stmt.executeUpdate();
        }
    }





    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria(); //Creo un nuevo objeto vació de la clase categoría porque lo lleno con lo de abajo
        c.setNombre(rs.getString("nombre")); //Settear el nombre del método getString del javaBeans
        c.setDescripcion(rs.getString("descripcion"));
        c.setCondicion(rs.getInt("condicion"));
        c.setIdCategoria(rs.getLong("idCategoria"));
        return c;
    }
}