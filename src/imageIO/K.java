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
			bufferedImage = ImageIO.read(srcImage);// ��ͼ����뵱ǰbuffer��
			// System.out.print(fileName);
			double oldheight = bufferedImage.getHeight();// ԭͼ�����򳤶�
			double oldwidth = bufferedImage.getWidth();// ԭͼ����򳤶�
			if (oldheight == oldwidth | srcImage.getName() == "icon.png") {// �жϸ�ͼ���Ƿ�Ϊ�����λ������ֽ�icon
				BufferedImage newBufferedImage = new BufferedImage(124, 124,
						BufferedImage.TYPE_INT_RGB);// ����ǣ���ʼ����
				// newBufferedImage.createGraphics().drawImage(bufferedImage, 0,
				// 0,124,124, Color.WHITE, null);����ΪjavaĬ�ϴ���ʽ
				newBufferedImage.createGraphics().drawImage(
						bufferedImage.getScaledInstance(124, 124,
								Image.SCALE_SMOOTH), 0, 0, Color.WHITE, null);
				;
				// ���д���ʽΪ��ʹͼƬ��ƽ���ķ�ʽ��color.whiteΪ�趨����ɫ0,0Ϊ��ʼ���꣬124,124Ϊ��ֹ����
				ImageIO.write(newBufferedImage, "png", new File(imagePath
						+ "png"));// �趨ת����ĸ�ʽ���ļ���
			} else {// ���ͼ���������λ������ֽ�icon������������
				// double percent = 320/oldwidth;
				double percent = (oldheight > oldwidth ? (320 / oldwidth)
						: (550 / oldwidth));// ����õ�ͼƬѹ���ٷֱȣ�����ݳ��Ⱥ᳤����ȡǰһ�֣�����Ϊ��һ��
				int newheight = (int) (oldheight * percent);
				int newwidth = (int) (oldwidth * percent);// �õ���ͼ�ĺ᳤���ݳ�
				BufferedImage newBufferedImage = new BufferedImage(newwidth,
						newheight, BufferedImage.TYPE_INT_RGB);
				// newBufferedImage.createGraphics().drawImage(bufferedImage, 0,
				// 0,newwidth,newheight, Color.WHITE, null);
				newBufferedImage.createGraphics().drawImage(
						bufferedImage.getScaledInstance(newwidth, newheight,
								Image.SCALE_SMOOTH), 0, 0, null);
				;
				// ���д���ʽΪ��ʹͼƬ��ƽ���ķ�ʽ��color.whiteΪ�趨����ɫ0,0Ϊ��ʼ���꣬124,124Ϊ��ֹ���꣬��������ΪĬ�Ϸ�ʽ
				ImageIO.write(newBufferedImage, "jpg", new File(imagePath
						+ "jpg"));// �趨Ϊjpg���ļ���Ϊ�µġ�
			}
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}