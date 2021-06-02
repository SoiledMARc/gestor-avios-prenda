package com.prenda.proyecto.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "prenda")
public class Prenda implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idprenda")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String tipo;
	
	@Column(name = "nombre_prenda")
	private String nombre;
	
	private Integer cantidad;
	
	@Column(name = "precio_pieza")
	private Double precioPieza;
	
	@Column(name = "costo_total")
	private Double costoTotal;
	
	@Column(name = "cantidad_tela")
	private Integer cantidadTela;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fecha;
	
	private String estado;
	

	public Prenda() {
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioPieza() {
		return precioPieza;
	}

	public void setPrecioPieza(Double precioPieza) {
		this.precioPieza = precioPieza;
	}

	public Double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public Integer getCantidadTela() {
		return cantidadTela;
	}

	public void setCantidadTela(Integer cantidadTela) {
		this.cantidadTela = cantidadTela;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
