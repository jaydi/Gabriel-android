package com.bitalino.bitalinodroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.bitalino.bitalinodroid.networks.Reader;
import com.bitalino.bitalinodroid.networks.Uploader;
import com.bitalino.bitalinodroid.threading.ThreadManager;

public class MainActivity extends Activity {
	private static MainActivity instance;

	private static final long USER_ID = 5684453372329984l;
	private final String remoteDevice = "98:D3:31:80:47:CF";

	private TextView tvLog;
	private BluetoothDevice dev;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.activity_main);

		tvLog = (TextView) findViewById(R.id.log);

		final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		for (BluetoothDevice device : btAdapter.getBondedDevices())
			if (device.getAddress().equals(remoteDevice))
				dev = device;

		// start reading data from bitalino
		if (dev != null)
			start();
		else
			showMessage("Couldn't find BITalino device");
	}

	@SuppressLint("HandlerLeak")
	private void start() {
		ThreadManager.execute(new Reader(dev), new Handler() {

			@Override
			public void handleMessage(Message m) {
				showMessage("Done");
			}

		});

		ThreadManager.execute(new Uploader(USER_ID), null);
	}

	public void showMessage(String msg) {
		if (tvLog != null)
			tvLog.append("\n" + msg);
	}

	public static MainActivity getInstance() {
		return instance;
	}

	public static void screenLog(final String msg) {
		final MainActivity instance = MainActivity.getInstance();
		if (instance != null)
			instance.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					instance.showMessage(msg);
				}

			});
	}

}