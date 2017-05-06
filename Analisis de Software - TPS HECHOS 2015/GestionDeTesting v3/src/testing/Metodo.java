/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * 
 */
public class Metodo {

	private int lineasCodigo;
	private int lineasComentario;
	private int complejidadCiclomatica;
	private int fanIn;
	private int fanOut;
	private LinkedList<String> operadores = new LinkedList<String>();
	private LinkedList<String> operadoresUnicos = new LinkedList<String>();
	private LinkedList<String> operandos = new LinkedList<String>();
	private LinkedList<String> operandosUnicos = new LinkedList<String>();
	private String codigo;
	private String nombre;
	private int linea;

	/**
	 * Devuelve el porcentaje de lineas de comentario en total como un String
	 * del formato xx%. Ej.: 23%
	 */
	public String getPorcentaje() {

		Float porcentaje = new Float((float) this.lineasComentario * 100
				/ (float) (this.lineasCodigo == 0 ? 1 : this.lineasCodigo));

		return String.format("%.0f", porcentaje) + " %";
	}

	/**
	 * Agrego un operador, verifica si es unico.
	 */
	public void agregarOperador(String operador) {
		boolean unico = true;

		// Veo si ya lo agregue antes
		for (Iterator<String> it = operadoresUnicos.iterator(); it.hasNext();) {
			String op = it.next();
			if (op.equals(operador)) {
				unico = false;
			}
		}

		if (unico) {
			operadoresUnicos.add(operador);
		}

		operadores.add(operador);

	}

	/**
	 * Agrego un operando, verifica si es unico.
	 */
	public void agregarOperando(String operando) {
		boolean unico = true;

		// Veo si ya lo agregue antes
		for (Iterator<String> it = operandosUnicos.iterator(); it.hasNext();) {
			String op = it.next();
			if (op.equals(operando)) {
				unico = false;
			}
		}

		if (unico) {
			operandosUnicos.add(operando);
		}

		operandos.add(operando);

	}

	public boolean esOperando(String str) {

		boolean ret = false;

		// Veo si ya lo agregue antes
		for (Iterator<String> it = operandosUnicos.iterator(); it.hasNext()
				&& !ret;) {
			String op = it.next();
			if (op.equals(str)) {
				ret = true;
			}
		}

		return ret;
	}

	public Integer getLineasCodigo() {
		return lineasCodigo;
	}

	public void setLineasCodigo(Integer lineasCodigo) {
		this.lineasCodigo = lineasCodigo;
	}

	public Integer getLineasComentario() {
		return lineasComentario;
	}

	public void setLineasComentario(Integer lineasComentario) {
		this.lineasComentario = lineasComentario;
	}

	public void incLineasComentario() {
		this.lineasComentario = this.lineasComentario + 1;
	}

	public void incComlejidadCiclomatica() {
		this.complejidadCiclomatica = complejidadCiclomatica + 1;
	}

	public Integer getComplejidadCiclomatica() {
		return complejidadCiclomatica;
	}

	public void setComplejidadCiclomatica(Integer complejidadCiclomatica) {
		this.complejidadCiclomatica = complejidadCiclomatica;
	}

	public void incFanIn() {
		this.fanIn = this.fanIn + 1;
	}

	public Integer getFanIn() {
		return fanIn;
	}

	public void setFanIn(Integer fanIn) {
		this.fanIn = fanIn;
	}

	public void incFanOut() {
		this.fanOut = this.fanOut + 1;
	}

	public Integer getFanOut() {
		return fanOut;
	}

	public void setFanOut(Integer fanOut) {
		this.fanOut = fanOut;
	}

	public Integer gethLongitud() {
		return operadores.size() + operandos.size();
	}

	public String gethVolumen() {

		Double volumen = this.gethLongitud()
				* (Math.log(operadoresUnicos.size() + operadoresUnicos.size()) / Math
						.log(2));

		return String.format("%.0f", volumen);
	}

	public String getCodigo() {
		return codigo == null ? "" : codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public LinkedList<String> getOperadores() {
		return operadores;
	}

	public void setOperadores(LinkedList<String> operadores) {
		this.operadores = operadores;
	}

	public LinkedList<String> getOperadoresUnicos() {
		return operadoresUnicos;
	}

	public void setOperadoresUnicos(LinkedList<String> operadoresUnicos) {
		this.operadoresUnicos = operadoresUnicos;
	}

	public LinkedList<String> getOperandos() {
		return operandos;
	}

	public void setOperandos(LinkedList<String> operandos) {
		this.operandos = operandos;
	}

	public LinkedList<String> getOperandosUnicos() {
		return operandosUnicos;
	}

	public void setOperandosUnicos(LinkedList<String> operandosUnicos) {
		this.operandosUnicos = operandosUnicos;
	}

}
