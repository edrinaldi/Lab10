package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}

	public LocalDate getPrimaMisurazione(River fiume) {
		// TODO Auto-generated method stub
		final String sql = "select min(day) as data "
				+ "from flow "
				+ "where river = ?";
		LocalDate data = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if(res.next()) {
				data = res.getDate("data").toLocalDate();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return data;
	}

	public LocalDate getUltimaMisurazione(River fiume) {
		// TODO Auto-generated method stub
		final String sql = "select max(day) as data "
				+ "from flow "
				+ "where river = ?";
		LocalDate data = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if(res.next()) {
				data = res.getDate("data").toLocalDate();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return data;
	}

	public int getnMisurazioni(River fiume) {
		// TODO Auto-generated method stub
		final String sql = "select count(*) as n "
				+ "from flow "
				+ "where river = ?";
		Integer tot = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if(res.next()) {
				tot = res.getInt("n");
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return tot;
	}

	public Double getAvgMisurazioni(River fiume) {
		// TODO Auto-generated method stub
		final String sql = "select avg(flow) as avg "
				+ "from flow "
				+ "where river = ?";
		Double avg = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			if(res.next()) {
				avg = res.getDouble("avg");
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return avg;
	}
	
	public List<Flow> getMisurazioni(River fiume) {
		String sql = "select day, flow "
				+ "from flow "
				+ "where river = ?";
		List<Flow> flussi = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				flussi.add(new Flow(rs.getDate("day").toLocalDate(), rs.getDouble("flow"), 
						fiume));
			}
			conn.close();
			Collections.sort(flussi);
			return flussi;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
}
