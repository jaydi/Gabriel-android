package com.bitalino.bitalinodroid.models;

import java.util.ArrayList;
import java.util.List;

public class DataQueue {
	private static List<Long> edas = new ArrayList<Long>();
	private static List<Long> ecgs = new ArrayList<Long>();
	private static List<Long> accs = new ArrayList<Long>();
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();
	private static Object lock3 = new Object();

	public static void addEda(long value) {
		synchronized (lock1) {
			edas.add(value);
		}
	}

	public static void addEcg(long value) {
		synchronized (lock2) {
			ecgs.add(value);
		}
	}

	public static void addAcc(long value) {
		synchronized (lock3) {
			accs.add(value);
		}
	}

	public static List<Long> purgeEdas() {
		List<Long> results = new ArrayList<Long>();
		synchronized (lock1) {
			results.addAll(edas);
			edas.clear();
		}
		return results;
	}

	public static List<Long> purgeEcgs() {
		List<Long> results = new ArrayList<Long>();
		synchronized (lock2) {
			results.addAll(ecgs);
			ecgs.clear();
		}
		return results;
	}

	public static List<Long> purgeAccs() {
		List<Long> results = new ArrayList<Long>();
		synchronized (lock3) {
			results.addAll(accs);
			accs.clear();
		}
		return results;
	}
}
