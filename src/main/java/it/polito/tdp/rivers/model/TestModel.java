package it.polito.tdp.rivers.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		System.out.println(m.getPrimaMisurazione(m.getAllRivers().get(0)));
		System.out.println(m.getUltimaMisurazione(m.getAllRivers().get(0)));
		System.out.println(m.getnMisurazioni(m.getAllRivers().get(0)));
		System.out.println(m.getAvgMisurazioni(m.getAllRivers().get(0)));
	}

}
