package com.bitalino.bitalinodroid.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
	public static void log(String path, String msg) {
		File file = new File(path);

		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(msg + "\n");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
