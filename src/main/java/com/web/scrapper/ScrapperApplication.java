package com.web.scrapper;

import com.web.scrapper.constant.Constants;
import com.web.scrapper.utility.DownloadImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrapperApplication {

	private static final int MYTHREADS = 5;



	public static void main(String[] args) {

		Pattern pat = Pattern.compile("(?<=\\/)(.*?)(?=\\/)");
		Matcher mat = pat.matcher(Constants.SCRAPPING_URL);
		String name_start = "";
		while(mat.find()){
			name_start  = mat.group().replace("/", "");
		}

		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		Path path = Paths.get(helper+"./"+name_start);
		Path downloadPath = null;
		if (!Files.exists(path)) {
			try {
				downloadPath = Files.createDirectories(path);
			} catch (IOException e) {
				System.out.println("Unable to create folder to store images");
				System.exit(0);
			}
		}
		else{
			downloadPath = path;
		}


		if(downloadPath!=null) {
				String dir = downloadPath.toString();
				ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

				LocalDate start = LocalDate.parse(Constants.START_DATE, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
				LocalDate end = LocalDate.parse(Constants.END_DATE, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

				String finalName_start = name_start;
				start.datesUntil(end).forEach(date -> {
							Runnable worker = new DownloadImage(date, dir, finalName_start);
							executor.execute(worker);
							/*try {
								downloadImage(date);
							} catch (IOException e) {
								System.out.println("Exception for date : "+date.toString());
								e.printStackTrace();
							}*/
						}
				);

				executor.shutdown();
				// Wait until all threads are finish
				while (!executor.isTerminated()) {

				}
				System.out.println("\nFinished all threads");
			}
			else{
				System.out.println("There was an issue while creating new Directory");
			}
	}
}
