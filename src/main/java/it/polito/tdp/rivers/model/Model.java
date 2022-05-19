package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private Integer giorni;
	private Double cMedia;
	private LocalDate primaMisurazione;
	private LocalDate ultimaMisurazione;
	private int nMisurazioni;
	private double avgMisurazioni;
	
	public Model() {
		this.dao = new RiversDAO();
		this.giorni = null;
		this.cMedia = null;
	}
	
	public List<River> getAllRivers() {
		List<River> fiumi = new ArrayList<>(this.dao.getAllRivers());
		Collections.sort(fiumi);
		return fiumi;
	}
	
	public void infoFiume(River fiume) {
		this.primaMisurazione = this.dao.getPrimaMisurazione(fiume);
		this.ultimaMisurazione = this.dao.getUltimaMisurazione(fiume);
		this.nMisurazioni = this.dao.getnMisurazioni(fiume);
		this.avgMisurazioni = this.dao.getAvgMisurazioni(fiume);
	}
	
	public LocalDate getPrimaMisurazione(River fiume) {
		return this.primaMisurazione;
	}
	
	public LocalDate getUltimaMisurazione(River fiume) {
		return this.ultimaMisurazione;
	}
	
	public Integer getnMisurazioni(River fiume) {
		return this.nMisurazioni;
	}
	
	public Double getAvgMisurazioni(River fiume) {
		return this.avgMisurazioni;
	}
	
	public void simula(River fiume, double k) {
		List<Flow> flussi = new ArrayList<>(this.dao.getMisurazioni(fiume));
		Simulatore sim = new Simulatore(fiume, k, this.avgMisurazioni, flussi);
		sim.run();
		this.giorni = sim.getGiorni();
		this.cMedia = sim.getcMed();
	}
	
	public int getGiorni() {
		return this.giorni;
	}
	
	public double getcMedia() {
		return this.cMedia;
	}
}
