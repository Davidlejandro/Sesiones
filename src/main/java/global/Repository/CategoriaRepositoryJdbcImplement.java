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
        String sql;

        if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0) {
            sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE idcategoria = ?";
        } else {
            sql = "INSERT INTO categoria(nombre, descripcion, condicion) VALUES (?, ?, 1)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());

            if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0) {
                stmt.setLong(3, categoria.getIdCategoria());
            }

            stmt.executeUpdate(); // <-- ESTA LÍNEA ES FUNDAMENTAL
        }
    }

    @Override
    public void eliminar(Long idCategoria) throws SQLException {
        // Consulta para obtener el estado actual
        String selectSql = "SELECT condicion FROM categoria WHERE idCategoria = ?";
        // Consulta para actualizar el estado
        String updateSql = "UPDATE categoria SET condicion = ? WHERE idCategoria = ?";

        try (
                PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                PreparedStatement updateStmt = conn.prepareStatement(updateSql)
        ) {
            selectStmt.setLong(1, idCategoria);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int condicionActual = rs.getInt("condicion");
                int nuevaCondicion = (condicionActual == 1) ? 0 : 1; // toggle

                updateStmt.setInt(1, nuevaCondicion);
                updateStmt.setLong(2, idCategoria);
                updateStmt.executeUpdate();
            }
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