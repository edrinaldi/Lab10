package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;

public class Simulatore {

	// Coda degli eventi
	private PriorityQueue<Event> queue;
	
	// Parametri di simulazione
	private double k;
	private double fMed;
	private double Q;
	private double fOutMin;
	Map<LocalDate, Double> flowMap;
	
	// Output della simulazione
	private int giorni;
	private double cMed;
	private List<Double> cList;
	
	// Stato del mondo simulato
	private double C;
	private double fOut;
	
	public Simulatore(River fiume, double k, double fMed, List<Flow> flussi) {
		this.k = k;
		this.fMed = fMed;
		this.Q = this.k*this.fMed*30*24*60*60;
		this.C = this.Q/2;
		this.fOutMin = 0.8*this.fMed*24*60*60;
		this.queue = new PriorityQueue<>();
		for(Flow f : flussi) {
			this.queue.add(new Event(f.getDay(), f.getFlow()));
		}
		this.giorni = 0;
		this.cList = new ArrayList<Double>();
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		double fIn = e.getfIn()*24*60*60;
		double random = Math.random();
		if(random <= 0.05) {
			this.fOut = 10*this.fOutMin;
		}
		else {
			this.fOut = this.fOutMin;
		}
		
		// aggiorno lo stato del mondo
		this.C = this.C + fIn - this.fOut;
		this.cList.add(this.C);
		if(this.C < this.fOut) {
			// non si e' potuta garantire l'erogazione minima
			this.giorni++;
		}
		if(this.C < 0) {
			this.C = 0;
		}
		if(this.C > this.Q) {
			// tracimazione
			this.C = this.Q;
		}
	}
	
	public int getGiorni() {
		return this.giorni;
	}
	
	public double getcMed() {
		double somma = 0;
		for(Double c : this.cList) {
			somma += c;
		}
		this.cMed = somma/this.cList.size();
		return this.cMed;
	}
}
