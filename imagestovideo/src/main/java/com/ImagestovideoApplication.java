package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

@SpringBootApplication
public class ImagestovideoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ImagestovideoApplication.class, args);
		System.out.println("Start project");

		Scanner s = new Scanner(System.in);
		String imgPath = "C:\\Users\\sensen\\Desktop\\img";
		String vidPath = "C:\\Users\\sensen\\Desktop\\video\\video.mp4";

		ArrayList<String> links = new ArrayList<>();
		File f = new File(imgPath);
		File[] f2 = f.listFiles();
		for (File f3 : f2) {
			links.add(f3.getAbsolutePath());
		}
		convertJPGtoMovie(links, vidPath);
		System.out.println("Video has been created at " + vidPath);
		s.close();
	}

	public static void convertJPGtoMovie(ArrayList<String> links, String vidPath) {
		OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath, 640, 720);
		try {
			recorder.setFrameRate(1);
			recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
			recorder.setVideoBitrate(9000);
			recorder.setFormat("mp4");
			recorder.setVideoQuality(0); // maximum quality
			recorder.start();
			for (int i = 0; i < links.size(); i++) {
				recorder.record(grabberConverter.convert(cvLoadImage(links.get(i))));
			}
			recorder.stop();
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			e.printStackTrace();
		}
	}
}
