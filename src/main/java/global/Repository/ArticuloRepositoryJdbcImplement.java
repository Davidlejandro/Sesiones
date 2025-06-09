package global.Repository;

import global.models.Articulo;
import global.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloRepositoryJdbcImplement implements Repository<Articulo> {
    private Connection conn;

    public ArticuloRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Articulo> listar() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT a.*, c.nombre as categoria FROM articulo as a " +
                     "INNER JOIN categoria as c ON (a.idcategoria=c.idcategoria) ORDER BY a.idarticulo")) {
            while (rs.next()) {
                articulos.add(getArticulo(rs));
            }
        }
        return articulos;
    }

    @Override
    public Articulo porId(Long id) throws SQLException {
        Articulo articulo = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT a.*, c.nombre as categoria FROM articulo as a " +
                "INNER JOIN categoria as c ON (a.idcategoria=c.idcategoria) WHERE a.idarticulo = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    articulo = getArticulo(rs);
                }
            }
        }
        return articulo;
    }

    @Override
    public void guardar(Articulo articulo) throws SQLException {
        String sql;
        if (articulo.getIdArticulo() != null && articulo.getIdArticulo() > 0) {
            sql = "UPDATE articulo SET idcategoria=?, codigo=?, nombre=?, stock=?, descripcion=?, imagen=?, condicion=? WHERE idarticulo=?";
        } else {
            sql = "INSERT INTO articulo (idcategoria, codigo, nombre, stock, descripcion, imagen, condicion) VALUES (?,?,?,?,?,?,?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, articulo.getCategoria().getIdCategoria());
            stmt.setString(2, articulo.getCodigo());
            stmt.setString(3, articulo.getNombre());
            stmt.setInt(4, articulo.getStock());
            stmt.setString(5, articulo.getDescripcion());
            stmt.setString(6, articulo.getImagen());
            stmt.setInt(7, articulo.getCondicion());
            if (articulo.getIdArticulo() != null && articulo.getIdArticulo() > 0) {
                stmt.setLong(8, articulo.getIdArticulo());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM articulo WHERE idarticulo=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Articulo getArticulo(ResultSet rs) throws SQLException {
        Articulo articulo = new Articulo();
        articulo.setIdArticulo(rs.getLong("idarticulo"));
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getLong("idcategoria"));
        c.setNombre(rs.getString("categoria"));
        articulo.setCategoria(c);
        articulo.setCodigo(rs.getString("codigo"));
        articulo.setNombre(rs.getString("nombre"));
        articulo.setStock(rs.getInt("stock"));
        articulo.setDescripcion(rs.getString("descripcion"));
        articulo.setImagen(rs.getString("imagen"));
        articulo.setCondicion(rs.getInt("condicion"));
        return articulo;
    }
}