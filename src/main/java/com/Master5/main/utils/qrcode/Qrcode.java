package com.Master5.main.utils.qrcode;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public final class Qrcode {

	public static int COLOR = 0xFF000000;
	public static int BACKCOLOR = 0xFFFFFFFF;

	private Qrcode() {
	}

	/**
	 * 对字符串进行编码 并输出到指定路径
	 * 
	 * @param content
	 *            需要转换成二维码 字符串
	 * @param file
	 *            输出二维码图片的file对象
	 * @return
	 */
	public static BufferedImage enCode(String content) {
		try {

			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			// 二维码编码格式
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			// 二维码边界空白大小 ，如：1、2、3、4 默认是4
			hints.put(EncodeHintType.MARGIN, 1);
			// 指定纠错等级
			/*
			 * 共有四个级别L M Q H， L 7% M 15% Q 25% H 30% 详情参照变量
			 */
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
			// MatrixToImageWriter.writeToFile(bitMatrix, "png", file);
			return Qrcode.toBufferedImage(bitMatrix);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对二维码进行解码
	 * 
	 * @param file
	 *            二维码图片位置的file对象
	 */
	@SuppressWarnings("unchecked")
	public void deCode(File file) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map hints = new HashMap();
			// 字符串编码集
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 二维码边界空白大小 ，如：1、2、3、4 默认好像是4
			hints.put(EncodeHintType.MARGIN, 1);
			// 指定纠错等级
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			Result result = formatReader.decode(binaryBitmap, hints);

			System.out.println("result = " + result.toString());
			System.out.println("resultFormat = " + result.getBarcodeFormat());
			System.out.println("resultText = " + result.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 二维码添加自定义logo（关键部分）
	 */
	public static void overLogo(BufferedImage image, String imgSavePath, String logoPath, String formate) {
		try {
			BufferedImage logo = ImageIO.read(new File(logoPath));
			Graphics2D g = image.createGraphics();
			// logo宽高
			int width = image.getWidth() / 5;
			int height = image.getHeight() / 5;
			// logo起始位置，此目的是为logo居中显示
			int x = (image.getWidth() - width) / 2;
			int y = (image.getHeight() - height) / 2;
			g.drawImage(logo, x, y, width, height, null);
			g.dispose();
			ImageIO.write(image, formate, new File(imgSavePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? COLOR : BACKCOLOR);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}

}