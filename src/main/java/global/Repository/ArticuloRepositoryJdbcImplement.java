package global.Repository;

import global.models.Articulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementación JDBC del repositorio de artículos
public class ArticuloRepositoryJdbcImplement implements RepositoryArticulo<Articulo> {

    // Conexión a la base de datos
    private Connection conn;

    // Constructor que recibe la conexión
    public ArticuloRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    // Lista todos los artículos activos (condición = 1)
    @Override
    public List<Articulo> lista() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo WHERE condicion = 1"; // Solo artículos activos
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Se recorre el resultado y se mapean los artículos
            while (rs.next()) {
                articulos.add(mapArticulo(rs));
            }
        }
        return articulos;
    }

    // Busca un artículo por su ID
    @Override
    public Articulo porId(Long idArticulo) throws SQLException {
        Articulo articulo = null;
        String sql = "SELECT * FROM articulo WHERE idarticulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idArticulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    articulo = mapArticulo(rs); // Si existe, se convierte el ResultSet en Articulo
                }
            }
        }
        return articulo;
    }

    // Guarda o actualiza un artículo
    @Override
    public void guardar(Articulo articulo) throws SQLException {
        String sql;
        // Se determina si es una actualización (existe ID válido) o una inserción
        boolean actualizar = articulo.getIdArticulo() != null && articulo.getIdArticulo() > 0;

        if (actualizar) {
            sql = "UPDATE articulo SET idcategoria=?, codigo=?, nombre=?, stock=?, descripcion=?, imagen=? WHERE idarticulo=?";
        } else {
            sql = "INSERT INTO articulo(idcategoria, codigo, nombre, stock, descripcion, imagen, condicion) VALUES (?, ?, ?, ?, ?, ?, 1)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Se establecen los parámetros para ambos casos
            stmt.setLong(1, articulo.getIdCategoria());
            stmt.setString(2, articulo.getCodigo());
            stmt.setString(3, articulo.getNombre());
            stmt.setInt(4, articulo.getStock());
            stmt.setString(5, articulo.getDescripcion());
            stmt.setString(6, articulo.getImagen());

            if (actualizar) {
                stmt.setInt(7, articulo.getIdArticulo()); // Solo para actualizaciones
            }

            stmt.executeUpdate(); // Ejecuta la operación
        }
    }

    // Alterna la condición (activo/inactivo) del artículo
    @Override
    public void eliminar(Integer idArticulo) throws SQLException {
        String selectSql = "SELECT condicion FROM articulo WHERE idArticulo = ?";
        String updateSql = "UPDATE articulo SET condicion = ? WHERE idArticulo = ?";

        try (
                PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                PreparedStatement updateStmt = conn.prepareStatement(updateSql)
        ) {
            // Consulta la condición actual
            selectStmt.setInt(1, idArticulo);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int condicionActual = rs.getInt("condicion");
                int nuevaCondicion = (condicionActual == 1) ? 0 : 1; // Alterna el valor

                // Actualiza la nueva condición
                updateStmt.setInt(1, nuevaCondicion);
                updateStmt.setInt(2, idArticulo);
                updateStmt.executeUpdate();
            }
        }
    }

    // Método privado para mapear un ResultSet a un objeto Articulo
    private Articulo mapArticulo(ResultSet rs) throws SQLException {
        Articulo articulo = new Articulo();
        articulo.setIdArticulo(rs.getInt("idarticulo"));
        articulo.setIdCategoria(rs.getLong("idcategoria"));
        articulo.setCodigo(rs.getString("codigo"));
        articulo.setNombre(rs.getString("nombre"));
        articulo.setStock(rs.getInt("stock"));
        articulo.setDescripcion(rs.getString("descripcion"));
        articulo.setImagen(rs.getString("imagen"));
        articulo.setCondicion(rs.getInt("condicion"));
        return articulo;
    }
}
