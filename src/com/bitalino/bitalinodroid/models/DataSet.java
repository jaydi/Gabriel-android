package com.bitalino.bitalinodroid.models;

import java.util.List;

public class DataSet {
	private List<Integer> ecgs;
	private List<Integer> edas;
	private List<Integer> accs;

	public List<Integer> getEcgs() {
		return ecgs;
	}

	public void setEcgs(List<Integer> ecgs) {
		this.ecgs = ecgs;
	}

	public List<Integer> getEdas() {
		return edas;
	}

	public void setEdas(List<Integer> edas) {
		this.edas = edas;
	}

	public List<Integer> getAccs() {
		return accs;
	}

	public void setAccs(List<Integer> accs) {
		this.accs = accs;
	}

	public boolean isEmpty() {
		return (ecgs == null || ecgs.isEmpty()) && (edas == null || edas.isEmpty()) && (accs == null || accs.isEmpty());
	}

}
