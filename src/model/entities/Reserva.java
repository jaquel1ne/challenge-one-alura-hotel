package model.entities;

import java.math.BigDecimal;
import java.util.Date;



public class Reserva {
	
	Long id;
	private Date dataEntrada;
	private Date dataSaida;
	private BigDecimal valor;
	private FormaPagamento formaPagamento;
	
		
	public Reserva() {
	}


	public Reserva(Long id, Date dataEntrada, Date dataSaida, BigDecimal valor, FormaPagamento formaPagamento) {
		super();
		this.id = id;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.valor = valor;
		this.formaPagamento = formaPagamento;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getDataEntrada() {
		return dataEntrada;
	}


	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}


	public Date getDataSaida() {
		return dataSaida;
	}


	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}


	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	
}
