package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.model.River;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		List<River> fiumi = new ArrayList<>(dao.getAllRivers());
		System.out.println(fiumi);
		System.out.println(dao.getMisurazioni(fiumi.get(0)));	// ok
	}

}
