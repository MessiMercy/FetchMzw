package imageIO;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class K {
	public void imageConverter(File srcImage, String imagePath) {
		BufferedImage bufferedImage;

		try {

			// read image file
			// bufferedImage = ImageIO.read(new
			// File("E:\\2\\workspace\\imageIO\\2.png"));
			bufferedImage = ImageIO.read(srcImage);// 将图像存入当前buffer流
			// System.out.print(fileName);
			double oldheight = bufferedImage.getHeight();// 原图像纵向长度
			double oldwidth = bufferedImage.getWidth();// 原图像横向长度
			if (oldheight == oldwidth | srcImage.getName() == "icon.png") {// 判断该图像是否为正方形或者名字叫icon
				BufferedImage newBufferedImage = new BufferedImage(124, 124,
						BufferedImage.TYPE_INT_RGB);// 如果是，则开始处理
				// newBufferedImage.createGraphics().drawImage(bufferedImage, 0,
				// 0,124,124, Color.WHITE, null);此行为java默认处理方式
				newBufferedImage.createGraphics().drawImage(
						bufferedImage.getScaledInstance(124, 124,
								Image.SCALE_SMOOTH), 0, 0, Color.WHITE, null);
				;
				// 此行处理方式为能使图片更平滑的方式，color.white为设定背景色0,0为起始坐标，124,124为终止坐标
				ImageIO.write(newBufferedImage, "png", new File(imagePath
						+ "png"));// 设定转换后的格式及文件名
			} else {// 如果图像不是正方形或者名字叫icon则进行下面操作
				// double percent = 320/oldwidth;
				double percent = (oldheight > oldwidth ? (320 / oldwidth)
						: (550 / oldwidth));// 计算得到图片压缩百分比，如果纵长比横长大，则取前一种，否则为后一种
				int newheight = (int) (oldheight * percent);
				int newwidth = (int) (oldwidth * percent);// 得到新图的横长和纵长
				BufferedImage newBufferedImage = new BufferedImage(newwidth,
						newheight, BufferedImage.TYPE_INT_RGB);
				// newBufferedImage.createGraphics().drawImage(bufferedImage, 0,
				// 0,newwidth,newheight, Color.WHITE, null);
				newBufferedImage.createGraphics().drawImage(
						bufferedImage.getScaledInstance(newwidth, newheight,
								Image.SCALE_SMOOTH), 0, 0, null);
				;
				// 此行处理方式为能使图片更平滑的方式，color.white为设定背景色0,0为起始坐标，124,124为终止坐标，上面那行为默认方式
				ImageIO.write(newBufferedImage, "jpg", new File(imagePath
						+ "jpg"));// 设定为jpg，文件名为新的。
			}
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}