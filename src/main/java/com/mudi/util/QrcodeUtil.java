package com.mudi.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * @author "csd"
 * @date 2018年4月10日
 */
public class QrcodeUtil {
	// 二维码颜色
	private static final int BLACK = 0xFF000000;
	// 二维码背景色
	private static final int WHITE = 0xFFFFFFFF;
	// 注：二维码颜色色差大，扫描快，但如果"BLACK'设置为黑色外其他颜色，可能无法扫描
	// 二维码图片宽度
	private static final int width = 300;
	// 二维码图片高度
	private static final int height = 300;
	// 二维码格式参数
	private static final EnumMap<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
	static {
		/*
		 * 二维码的纠错级别(排错率),4个级别： L (7%)、 M (15%)、 Q (25%)、 H (30%)(最高H)
		 * 纠错信息同样存储在二维码中，纠错级别越高，纠错信息占用的空间越多，那么能存储的有用讯息就越少；共有四级； 选择M，扫描速度快。
		 */
		// 纠正等
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 二维码边界空白大小 1,2,3,4 (4为默认,最大),0为没有边界
		hints.put(EncodeHintType.MARGIN, 1);
		// 编码
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 150);
	}

	private static final String IMG_TYPE = "jpg";

	/**
	 * 输出二维码到outputStream
	 * @param content
	 * @param outputStream
	 */
	public static void encodeQrcode(String content, OutputStream outputStream) {
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix, IMG_TYPE, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出BufferedImage到outputStream
	 * @param content
	 * @param outputStream
	 */
	public static void bufferedImageToStream(BufferedImage bufferedImage, OutputStream outputStream) {
		try {
			ImageIO.write(bufferedImage, IMG_TYPE, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出BufferedImage到文件
	 * @param content
	 * @param outputStream
	 */
	public static void bufferedImageToFile(BufferedImage bufferedImage, String toFile) {
		try {
			File file = new File(toFile);
			ImageIO.write(bufferedImage, IMG_TYPE, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出二维码到BufferedImage，可修改二维码颜色,在此BufferedImage上添加的logo是原图片颜色
	 * @param contents
	 * @return
	 */
	public static BufferedImage encodeImg(String contents) {
		BufferedImage image = null;
		try {
			BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			int width = matrix.getWidth();
			int height = matrix.getHeight();
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE); // 设置二维码颜色
				}
			}
		} catch (Exception e) {
			System.out.println("生成二维码失败" + e.getMessage());
		}
		return image;
	}

	/**
	 * 输出二维码到BufferedImage
	 * @param content
	 * @return
	 */
	public static BufferedImage encodeQrcode(String content) {
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 输出二维码到文件
	 * @param contents
	 * @param file
	 */
	public static void writeToFile(String contents, File file) {
		BufferedImage image = encodeQrcode(contents);
		try {
			ImageIO.write(image, IMG_TYPE, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 二维码解码
	 * @param file	二维码图片
	 * @return
	 */
	public static String decodeQrcode(File file) {
		MultiFormatReader multiFormatReader = new MultiFormatReader();
		Map<DecodeHintType, Object> map = new HashMap<DecodeHintType, Object>(1);
		map.put(DecodeHintType.CHARACTER_SET, "utf-8");
		Result decode = null;
		try {
			BufferedImage read = ImageIO.read(file);
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(read)));
			decode = multiFormatReader.decode(binaryBitmap, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decode.getText();
	}

	/**
	 * 图片合并
	 * @param bgImg 背景图片
	 * @param contents	二维码内容
	 * @param toFile	输出的文件
	 */
	public static void imgMerge(String bgImg, String contents, String toFile) {
		try {
			File backgroundImage = new File(bgImg);
			InputStream is = new FileInputStream(backgroundImage);
			BufferedImage templateImage = ImageIO.read(is);
			templateImage.flush();
			Graphics g = templateImage.getGraphics();
			// 二维码
			BufferedImage qrcodeImg = encodeImg(contents);
			// 画图
			g.drawImage(qrcodeImg, 143, 371, 465, 465, null);
			// 输出到文件
			File file = new File(toFile);
			ImageIO.write(templateImage, IMG_TYPE, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片合并
	 * @param bgImg 背景图片
	 * @param qrcodeImg	二维码图片
	 * @param toFile	输出的文件
	 */
	public static void imgMerge(String bgImg, BufferedImage qrcodeImg, String toFile) {
		try {
			File backgroundImage = new File(bgImg);
			InputStream is = new FileInputStream(backgroundImage);
			BufferedImage templateImage = ImageIO.read(is);
			templateImage.flush();
			Graphics g = templateImage.getGraphics();
			// 画图
			g.drawImage(qrcodeImg, 147, 406, 460, 460, null);

			// 输出到文件
			File file = new File(toFile);
			ImageIO.write(templateImage, IMG_TYPE, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 二维码绘制logo
	 * @param twodimensioncode 二维码图片
	 * @param logoImg logo图片文件
	 * */
	private static BufferedImage encodeImgLogo(BufferedImage twodimensioncode, String logoImg) {
		try {
			File logoImage = new File(logoImg);
			// 获取画笔
			Graphics2D g = twodimensioncode.createGraphics();
			// 读取logo图片
			BufferedImage logo = ImageIO.read(logoImage);
			// 设置logo大小，太大，会覆盖二维码，此处20%
			int logoWidth = logo.getWidth(null) > twodimensioncode.getWidth() * 2 / 10 ? (twodimensioncode.getWidth() * 2 / 10) : logo.getWidth(null);
			int logoHeight = logo.getHeight(null) > twodimensioncode.getHeight() * 2 / 10 ? (twodimensioncode.getHeight() * 2 / 10) : logo.getHeight(null);
			// 设置logo图片放置位置
			int x = (twodimensioncode.getWidth() - logoWidth) / 2;
			int y = (twodimensioncode.getHeight() - logoHeight) / 2;
			// 开始合并绘制图片
			g.drawImage(logo, x, y, logoWidth, logoHeight, null);
			// 绘制圆角的边框,logo为圆形的时候可用
			// g.drawRoundRect(x, y, logoWidth, logoHeight, 15 ,15);
			// logo边框大小
			g.setStroke(new BasicStroke(2));
			// logo边框颜色
			g.setColor(Color.WHITE);
			// 绘制指定矩形的边框
			g.drawRect(x, y, logoWidth, logoHeight);
			// 释放资源
			g.dispose();
			logo.flush();
			twodimensioncode.flush();
		} catch (Exception e) {
			System.out.println("二维码绘制logo失败");
		}
		return twodimensioncode;
	}

	public static void main(String[] args) throws IOException {

		// 二维码+logo+背景，start
		String bgImg = "D:\\qr\\yanxiuban\\bg.jpg";
		String logoImg = "D:\\qr\\yanxiuban\\logo.png";
		String toFile = "D:\\qr\\yanxiuban\\aaa.jpg";
		BufferedImage encodeImg = encodeImg("www.baidu.com");
		BufferedImage encodeImgLogo = encodeImgLogo(encodeImg, logoImg);
		imgMerge(bgImg, encodeImgLogo, toFile);
		// 二维码+logo+背景，end

		// 解析二维码
		String decodeQrcode = decodeQrcode(new File("D:\\qr\\yanxiuban\\aaa.jpg"));
		System.out.println(decodeQrcode);
	}

}