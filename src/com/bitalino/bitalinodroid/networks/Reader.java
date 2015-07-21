package com.bitalino.bitalinodroid.networks;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.bitalino.bitalinodroid.MainActivity;
import com.bitalino.bitalinodroid.models.DataQueue;
import com.bitalino.bitalinodroid.threading.Work;
import com.bitalino.comm.BITalinoDevice;
import com.bitalino.comm.BITalinoFrame;

public class Reader extends Work<Void> {
	private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private final int POSITION_EDA = 1;
	private final int POSITION_ECG = 2;
	private final int POSITION_ACC = 3;

	private final int SAMPLING_RATE = 100;

	private BluetoothDevice dev;

	public Reader(BluetoothDevice dev) {
		super();
		this.dev = dev;
	}

	@Override
	public Void work() throws IOException {
		try {
			BluetoothSocket sock = dev.createRfcommSocketToServiceRecord(MY_UUID);
			sock.connect();

			MainActivity.screenLog("connected");

			BITalinoDevice bitalino = new BITalinoDevice(SAMPLING_RATE, new int[] { POSITION_EDA, POSITION_ECG, POSITION_ACC });
			bitalino.open(sock.getInputStream(), sock.getOutputStream());

			MainActivity.screenLog("BITalino version: " + bitalino.version());

			// start acquisition on predefined analog channels
			bitalino.start();

			MainActivity.screenLog("start reading...");

			// read
			// store in queue
			int counter = 0;
			while (counter < 300) {
				BITalinoFrame[] frames = bitalino.read(SAMPLING_RATE);
				for (BITalinoFrame frame : frames) {
					DataQueue.addEda(frame.getAnalog(POSITION_EDA));
					DataQueue.addEcg(frame.getAnalog(POSITION_ECG));
					DataQueue.addAcc(frame.getAnalog(POSITION_ACC));
				}

				counter++;
			}

			// terminate connection
			bitalino.stop();
			sock.close();

		} catch (IOException e) {
			Log.e("MAIN", "Lost connection to BITalino");
		} catch (Exception e) {
			Log.e("MAIN", "There was an error.", e);
		}

		return null;
	}

}
