package com.bitalino.bitalinodroid.networks;

import java.io.IOException;

import com.appspot.project_gabriel.gabriel.Gabriel;
import com.appspot.project_gabriel.gabriel.model.ProtocolsDataMessage;
import com.bitalino.bitalinodroid.MainActivity;
import com.bitalino.bitalinodroid.models.DataQueue;
import com.bitalino.bitalinodroid.threading.Work;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

public class Uploader extends Work<Void> {
	private static final long INTERVAL = 1000 * 3;

	private static Gabriel apis;
	int counter = 0;

	static {
		Gabriel.Builder builder = new Gabriel.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		apis = builder.build();
	}

	private boolean network = true;
	private long userId;

	public Uploader(long userId) {
		super();
		this.userId = userId;
	}

	@Override
	public Void work() throws IOException {
		while (counter < 60) {
			ProtocolsDataMessage data = getData();

			if (data != null) {
				if (network)
					apis.datas().insert(data).execute();

				MainActivity.screenLog(counter + " data sent... ");
			}

			counter++;

			try {
				Thread.sleep(INTERVAL);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private ProtocolsDataMessage getData() {
		ProtocolsDataMessage data = new ProtocolsDataMessage();
		data.setUserId(userId);
		data.setEcgs(DataQueue.purgeEcgs());
		data.setEdas(DataQueue.purgeEdas());
		data.setAccs(DataQueue.purgeAccs());
		return data;
	}

}
