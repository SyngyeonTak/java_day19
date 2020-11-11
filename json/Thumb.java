package day1111.json;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Thumb extends JPanel implements Runnable{
	Image bigImg;
	Image img;
	BufferedImage buffImg;//썸네일로 그려질 이미지
	int width, height;
	
	String url;
	
	JsonGallery jsonGallery;
	
	Thread thread;
	public Thumb(JsonGallery jsonGallery, int width, int height, String url) {
		this.jsonGallery = jsonGallery;
		this.width = width;
		this.height = height;
		
		this.url = url;
		this.setPreferredSize(new Dimension(width, height));
		thread = new Thread(this);
		thread.start();
		
		addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e) {
				jsonGallery.getDetail(bigImg);
			}
		});
	}
	
	public void getErrorImage() {
		URL url = this.getClass().getClassLoader().getResource("res/error.png");
		try {
			buffImg = ImageIO.read(url);
			img = buffImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			URL path = new URL(url);
			buffImg = ImageIO.read(path);
			bigImg = buffImg.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
			img = buffImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			
			jsonGallery.p_south.updateUI();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			getErrorImage();
		}
	}
	
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
	
}
