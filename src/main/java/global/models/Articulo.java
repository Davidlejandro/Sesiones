package global.models;


public class Articulo {
    //declaramos los atributos de la clase Articulo en base a la tabla de la BBDD
    private Long idArticulo;
    private Categoria categoria;
    private String codigo;
    private String nombre;
    private int stock;
    private String descripcion;
    private String imagen;
    private int condicion;

    //Implementamos el constructor vacio
    public Articulo(){

    }
    //Implmentamso un constructor que inicialice todos los parámetros
    public Articulo(Long idArticulo, String tipo, String codigo, String nombre, int stock, String descripcion, String imagen, int condicion){
        this.idArticulo=idArticulo;
        Categoria categoria = new Categoria();
        categoria.setNombre(tipo);
        this.codigo=codigo;
        this.nombre=nombre;
        this.stock=stock;
        this.descripcion=descripcion;
        this.imagen=imagen;
        this.condicion=condicion;
    }
    //métodos get and set
    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }
}