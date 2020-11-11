package lecturer.day1111.json;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Movie extends JPanel implements Runnable{
	Image bigImg; //갤러리에 전달할 큰 이미지...
	Image img;
	BufferedImage buffImg;//썸네일로 그려질 이미지
	
	int width, height;
	Thread thread;
	
	JsonGallery jsonGallery;
	
	//이 객체는 한편의 영화를 표현하는 클래스이다!!
	String url;
	String title;
	String phase;
	String category_name;
	String release_year;
	
	public Movie(JsonGallery jsonGallery, int width, int height, String url, String title, String phase, String category_name, String release_year) {
		this.jsonGallery = jsonGallery;
		this.width = width;
		this.height = height;
		
		this.url = url;
		this.title = title;
		this.phase = phase;
		this.category_name = category_name;
		this.release_year = release_year;
		
		
		this.setPreferredSize(new Dimension(width, height));
		thread = new Thread(this);//Runnable을 구현한 객체를 인수로 넣어준다.
		thread.start();
		
		//리스너와 현재 패널과 연결
		this.addMouseListener(new MouseAdapter() {
			
			public void mouseReleased(MouseEvent e) {
				System.out.println("클릭한 저의 영화 제목은 "+title);
				jsonGallery.getDetail(bigImg, title, phase, category_name, release_year);
			}
		
		});
	}
	
	public void getErrorImage() {
		URL url = this.getClass().getClassLoader().getResource("res/error.png");
		try {
			BufferedImage buffImg = ImageIO.read(url);
			img = buffImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void run() {
		try {
			//1. 이미지가 로컬 하드에 있을 경우 Toolkit 사용
			//2. 이미지가 클래스패스에 있을 경우 ClassLoader()...
			//3.BufferedImage > ImageIO(복습) : 파일로 이미지를 다운받아서 가져오는 것이 아니라 메모리상의 버퍼에 띄어 놓는 것이다.
			//ImageIO: 이미지의 경로를 가지고 온다.
			URL path = new URL(url);
			buffImg = ImageIO.read(path);
			bigImg = buffImg.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
			img = buffImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			
			jsonGallery.p_south.updateUI();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("이미지를 찾을 수 없네요");//url에 이미지가 없는 경우 검은 이미지를 화면에 뿌리자...
			getErrorImage();
		}
	}
	
	//그림 그리기
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}


	
	
}


















