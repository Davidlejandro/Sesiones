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
    public Categoria porId(int id) throws SQLException { //Aquí está el id del método
        //Creo un objeto de tipo categoría nulo
        Categoria categoria = null;
        try(PreparedStatement stmt = conn.prepareStatement(
                "select * from categoria where id = ?")){ //Selecciona todo de categoria donde el id del método
            stmt.setInt(1, id); //Setea el valor en la columna número uno
            try(ResultSet rs = stmt.executeQuery()){
                categoria = getCategoria(rs);
            }
        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {
        //declaro una variable de tipo string
        String sql;
        //si el id de categoria es distinto de nulo y es mayor a 0

        if (categoria.getIdCategoria() != null || categoria.getIdCategoria() > 0) {
            //en la variable sql va a guardar el siguiente sql
            sql = "update categoria set nombre=?, descripcion=? where idCategoria=?";
        } else {
            sql = "insert into categoria(nombre, descripcion, condicion) values(?, ?, 1)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establece el nombre de la categoría en el primer parámetro del SQL
            stmt.setString(1, categoria.getNombre());

            // Establece la descripción de la categoría en el segundo parámetro
            stmt.setString(2, categoria.getDescripcion());

            // Establece el ID de la categoría (clave primaria) como tercer parámetro
            stmt.setInt(3, categoria.getIdCategoria());

            // Ejecuta la actualización (UPDATE)
            stmt.executeUpdate();
            }

        }
        @Override
        public void eliminar(int id) throws SQLException {
            String sql = "update categoria set condicion = 0 where idCategoria = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Marca la categoría como inactiva (eliminación lógica)
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        }





    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria(); //Creo un nuevo objeto vació de la clase categoría porque lo lleno con lo de abajo
        c.setNombre(rs.getString("nombre")); //Settear el nombre del método getString del javaBeans
        c.setDescripcion(rs.getString("descripcion"));
        c.setCondicion(rs.getInt("condicion"));
        c.setIdCategoria(rs.getInt("idCategoria"));
        return c;
    }
}