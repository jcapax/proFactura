package com.jcapax.jcarlosporcel.profactura.listas;

import java.util.Date;

/**
 * Created by jcarlos.porcel on 12/12/2015.
 */
public class Factura {
    private int id;
    private int idSucursal;
    private int especificacion;
    private int correlativoSucursal;
    private Date fechaFactura;
    private int nroFactura;
    private int nroAutorizacion;
    private int estado;
    private String nit;
    private String razonSocial;
    private Double importeTotal;
    private Double importeIce;
    private Double importeExportaciones;
    private Double importeVentasTasaCero;
    private Double importeSubtotal;
    private Double importeRebajas;
    private Double importeBaseDebitoFiscal;
    private Double debitoFiscal;
    private String codigoControl;
    private int idVenta;
    private Date fechaLimiteEmision;
    private int idDosificacion;

    public Factura(int id, int idSucursal, int especificacion, int correlativoSucursal,
                   Date fechaFactura, int nroFactura, int nroAutorizacion, int estado,
                   String nit, String razonSocial, Double importeTotal, Double importeIce,
                   Double importeExportaciones, Double importeVentasTasaCero,
                   Double importeSubtotal, Double importeRebajas,
                   Double importeBaseDebitoFiscal, Double debitoFiscal,
                   String codigoControl, int idVenta,
                   Date fechaLimiteEmision, int idDosificacion){

        this.id                      = id;
        this.idSucursal              = idSucursal;
        this.especificacion          = especificacion;
        this.correlativoSucursal     = correlativoSucursal;
        this.fechaFactura            = fechaFactura;
        this.nroFactura              = nroFactura;
        this.nroAutorizacion         = nroAutorizacion;
        this.estado                  = estado;
        this.importeExportaciones    = importeExportaciones;
        this.importeVentasTasaCero   = importeVentasTasaCero;
        this.importeSubtotal         = importeSubtotal;
        this.importeRebajas          = importeRebajas;
        this.importeBaseDebitoFiscal = importeBaseDebitoFiscal;
        this.debitoFiscal            = debitoFiscal;
        this.codigoControl           = codigoControl;
        this.idVenta                 = idVenta;
        this.fechaLimiteEmision      = fechaLimiteEmision;
        this.idDosificacion          = idDosificacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public void setEspecificacion(int especificacion) {
        this.especificacion = especificacion;
    }

    public void setCorrelativoSucursal(int correlativoSucursal) {
        this.correlativoSucursal = correlativoSucursal;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public void setNroFactura(int nroFactura) {
        this.nroFactura = nroFactura;
    }

    public void setNroAutorizacion(int nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public void setImporteIce(Double importeIce) {
        this.importeIce = importeIce;
    }

    public void setImporteExportaciones(Double importeExportaciones) {
        this.importeExportaciones = importeExportaciones;
    }

    public void setImporteVentasTasaCero(Double importeVentasTasaCero) {
        this.importeVentasTasaCero = importeVentasTasaCero;
    }

    public void setImporteSubtotal(Double importeSubtotal) {
        this.importeSubtotal = importeSubtotal;
    }

    public void setImporteRebajas(Double importeRebajas) {
        this.importeRebajas = importeRebajas;
    }

    public void setImporteBaseDebitoFiscal(Double importeBaseDebitoFiscal) {
        this.importeBaseDebitoFiscal = importeBaseDebitoFiscal;
    }

    public void setDebitoFiscal(Double debitoFiscal) {
        this.debitoFiscal = debitoFiscal;
    }

    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setFechaLimiteEmision(Date fechaLimiteEmision) {
        this.fechaLimiteEmision = fechaLimiteEmision;
    }

    public void setIdDosificacion(int idDosificacion) {
        this.idDosificacion = idDosificacion;
    }

    public int getId() {

        return id;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public int getEspecificacion() {
        return especificacion;
    }

    public int getCorrelativoSucursal() {
        return correlativoSucursal;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public int getNroFactura() {
        return nroFactura;
    }

    public int getNroAutorizacion() {
        return nroAutorizacion;
    }

    public int getEstado() {
        return estado;
    }

    public String getNit() {
        return nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public Double getImporteIce() {
        return importeIce;
    }

    public Double getImporteExportaciones() {
        return importeExportaciones;
    }

    public Double getImporteVentasTasaCero() {
        return importeVentasTasaCero;
    }

    public Double getImporteSubtotal() {
        return importeSubtotal;
    }

    public Double getImporteRebajas() {
        return importeRebajas;
    }

    public Double getImporteBaseDebitoFiscal() {
        return importeBaseDebitoFiscal;
    }

    public Double getDebitoFiscal() {
        return debitoFiscal;
    }

    public String getCodigoControl() {
        return codigoControl;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Date getFechaLimiteEmision() {
        return fechaLimiteEmision;
    }

    public int getIdDosificacion() {
        return idDosificacion;
    }
}

