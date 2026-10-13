package ar.edu.unju.escmi.tp6.dominio;

public class Producto {

	private long codigo;
    private String descripcion;
    private double precioUnitario;
    private String origenFabricacion;

    public Producto() {

    }

    public Producto(long codigo, String descripcion, double precioUnitario, String origenFabricacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.origenFabricacion = origenFabricacion;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getOrigenFabricacion() {
        return origenFabricacion;
    }

    public void setOrigenFabricacion(String origenFabricacion) {
        this.origenFabricacion = origenFabricacion;
    }

    /** Indica si el producto es un celular (para celulares el tope del programa es $1.000.000). */
    public boolean esCelular() {
        return descripcion != null && descripcion.toLowerCase().startsWith("celular");
    }

    /**
     * Indica si el producto entra en el programa "Ahora 20": tiene que ser de fabricación nacional
     * y ser un televisor, aire acondicionado, heladera, lavarropas o celular.
     */
    public boolean esAhora20() {
        if (descripcion == null || !"Argentina".equalsIgnoreCase(origenFabricacion)) {
            return false;
        }
        String d = descripcion.toLowerCase();
        return d.startsWith("televisor") || d.startsWith("aire") || d.startsWith("heladera")
                || d.startsWith("lavarropa") || d.startsWith("celular");
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " Descripcion: " + descripcion + " Precio Unitario: " + precioUnitario
                + " Origen fabricacion: " + origenFabricacion;
    }
}
