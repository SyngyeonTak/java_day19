package lecturer.day1111.json;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonGallery extends JFrame{
	JPanel p_center;//그리드를 적용할 가운데 패털
	JPanel p_south;//썸네일을 부착할 남쪽패널
	JPanel p_can;//큰 그림이 그려질 패널
	JPanel p_detail;//상세 내용이 출력될 패널
	JLabel[] la = new JLabel[4];//[][][][]
	String[] la_str = {"Title", "Phase", "Category", "Release"};
	String[] la_append = new String[4];
	int width;
	int height;
	
	Image big;//큰 이미지
	//원격지의 URL 이미지를 로드하는 동안, 그래픽 처리가 먹통이 되버린다.이 문제를 해결하기 위함
	
	public JsonGallery() {
		p_center = new JPanel();
		p_south = new JPanel();
		p_can = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(big, 0, 0, p_can);
			}
		};
		p_detail = new JPanel();
		
		for (int i = 0; i < la.length; i++) {
			la[i] = new JLabel(la_str[i]);
			la[i].setPreferredSize(new Dimension(380, 50));
			la[i].setFont(new Font("Verdana", Font.BOLD, 15));
			p_detail.add(la[i]);
		}
		p_center.setLayout(new GridLayout(1, 2));
		
		//스타일 적용
		p_center.setBackground(Color.YELLOW);
		p_south.setPreferredSize(new Dimension(800, 100));
		p_south.setBackground(Color.GREEN);
		p_can.setBackground(Color.PINK);
		p_detail.setBackground(Color.ORANGE);
		
		//조립
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		p_center.add(p_can);
		p_center.add(p_detail);
			
		setVisible(true);
		setSize(800, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		createThumb();
	}
	
	//영화 썸네일 생성하기!(복습)
	public void createThumb() {
		
		BufferedReader buffr = null;
		//클래스패스상에 있는 텍스트 파일 읽기
		try {
			URL url = this.getClass().getClassLoader().getResource("res/data.json");//url을 반환한다.
			URI uri = url.toURI();//URL을 URI로 변경
			FileReader reader = new FileReader(new File(uri));//uri를 매개로하는 파일에 reader스트림을 줌
			buffr = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String data = null;
			while(true) {
				data = buffr.readLine();
				if(data == null) break;
				sb.append(data);
			}
			//System.out.println(sb.toString());//모아진 스트링 출력해본다.
			//파싱
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());//문자열에 불과했던 json 표기법 문자열을 실제 json 객체로 반환해준다.
			
			JSONArray jsonArray = (JSONArray)jsonObject.get("marvel");
			
			//따라서 이 시점부터 마치 객체처럼 접근하여 사용이 가능하다.
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = (JSONObject)jsonArray.get(i); //영화 한편 반환
				
				System.out.println(obj.get("title"));//토르
				System.out.println(obj.get("phase"));//부제목
				
				String u =(String)obj.get("url");
				String title = (String)obj.get("title");
				String phase = (String)obj.get("phase");
				String category_name = (String)obj.get("category_name");
				String release_year = ((Long)obj.get("release_year")).toString();
				
				Movie thumbnail = new Movie(this, 45, 45, u, title, phase, category_name, release_year);
				p_south.add(thumbnail);//생성된 썸네일을 p_south 패널에 부착				
				//p_south.updateUI();
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	//상세내용 출력하기
	public void getDetail(Image big, String title, String phase, String category, String release) {
		//이미지 처리
		this.big = big;
		p_can.repaint();
		
		//제목, 등의 영화 정보 출력
		la[0].setText(la_str[0]+":   "+title);
		la[1].setText(la_str[1]+":   "+phase);
		la[2].setText(la_str[2]+":   "+category);
		la[3].setText(la_str[3]+":   "+release);
		
	}
	
	public static void main(String[] args) {
		new JsonGallery();
	}
	
}















