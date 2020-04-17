package com.web.scrapper.utility;

import com.web.scrapper.constant.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DownloadImage implements Runnable{

    private LocalDate date;
    private String downloadPath;
    private String nameStart;

    public DownloadImage(LocalDate date, String downloadPath, String nameStart) {
        this.date = date;
        this.downloadPath = downloadPath;
        this.nameStart = nameStart;
    }

    @Override
    public void run() {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fullUrl = Constants.SCRAPPING_URL +formattedDate;

        try {
            Document doc = Jsoup.connect(fullUrl).get();
            Elements div = doc.getElementsByClass("js-comic-swipe");
            //Elements div = doc.select("div[data-feature-id='344']");
            String image_name = nameStart+"_"+div.attr("data-formatted-date");
            String image_url = div.attr("data-image");
            saveImage(image_url, image_name);
        } catch (IOException e) {
            System.out.println("Sorry but there was no"+nameStart+"for that date."+fullUrl);
        }


    }

    private void saveImage(String imageUrl, String imageName) throws IOException {

        BufferedImage image =null;

        URL url = new URL(imageUrl);

        image = ImageIO.read(url);

        ImageIO.write(image, "png", new File(this.downloadPath+"/"+imageName+".png"));
        System.out.println("Image Downloaded Successfully at -> "+this.downloadPath+"/"+imageName+".png");
    }
}
