package com.jcapax.jcarlosporcel.profactura.listas;

/**
 * Created by jcarlos.porcel on 17/12/2015.
 */
public class detalleProductos {

    String nombreProducto;
    String cantidad;
    String costoUnitario;
    String precioTotal;
    String iceTotal;
    String alicuota;

    public detalleProductos(String nombreProducto, String cantidad, String costoUnitario, String precioTotal, String iceTotal, String alicuota) {
        this.nombreProducto = nombreProducto;
        this.cantidad       = cantidad;
        this.costoUnitario  = costoUnitario;
        this.precioTotal    = precioTotal;
        this.iceTotal       = iceTotal;
        this.alicuota       = alicuota;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(String costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getIceTotal() {
        return iceTotal;
    }

    public void setIceTotal(String iceTotal) {
        this.iceTotal = iceTotal;
    }

    public String getAlicuota() {
        return alicuota;
    }

    public void setAlicuota(String alicuota) {
        this.alicuota = alicuota;
    }
}
