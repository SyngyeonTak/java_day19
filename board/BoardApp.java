package day1111.board;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import common.image.ImageUtil;
import day1111.member.Login;
import day1111.member.RegistForm;

public class BoardApp extends JFrame{
	JPanel p_north;
	JButton bt_board, bt_regist, bt_login;
	JPanel p_center;
	
	JPanel[] pages = new JPanel[5];//화면 전환에 사용될 패널들을 담게 될 배열
	
	//상수: 변하지 않는 데이터에 의미를 부여하여 직관성을 높인다.
	public static final int BOARD_LIST = 0;
	public static final int BOARD_WRITE = 1;
	public static final int BOARD_DETAIL = 2;
	public static final int MEMBER_REGIST = 3;
	public static final int MEMBER_LOGIN = 4;
	
	public BoardApp() {
		//생성
		p_north = new JPanel();
		p_center = new JPanel();
		
		bt_board = new JButton(ImageUtil.getIcon(this.getClass(), "res/board.png", 90, 35));
		bt_regist = new JButton(ImageUtil.getIcon(this.getClass(), "res/regist.png", 90, 35));
		bt_login = new JButton(ImageUtil.getIcon(this.getClass(), "res/login.png", 90, 35));
		pages[0] = new BoardList(this);//게시판 목록 페이지
		pages[1] = new BoardWrite(this);//게시판 글쓰기 페이지
		pages[2] = new BoardDetail(this);//게시판 상세보기 페이지
		pages[3] = new RegistForm(this);//게시판 회원가입 페이지
		pages[4] = new Login(this);//게시판 로그인 페이지
		
		//스타일
		bt_board.setPreferredSize(new Dimension(100, 35));
		bt_regist.setPreferredSize(new Dimension(100, 35));
		bt_login.setPreferredSize(new Dimension(100, 35));
		
		//조립
		p_north.add(bt_board);
		p_north.add(bt_regist);
		p_north.add(bt_login);
		
		p_center.add(pages[0]);
		p_center.add(pages[1]);
		p_center.add(pages[2]);
		p_center.add(pages[3]);
		p_center.add(pages[4]);

		
		add(p_north, BorderLayout.NORTH);
		add(p_center);

		//게시판과 리스너 연결
		bt_board.addActionListener((e)->{
			setPage(BOARD_LIST);
		});
		
		//가입버튼과 리스너 연결
		//LAMBDA 표현식: 함수형 프로그램 표현식 java 8부터 지원됨
		//참고) javascript에서는 람다를 가리켜 클로저라고 한다.
		//이벤트 구현시, 재정의할 메서드가 달랑 1개인 경우 굳이 내부 익명 클래스라는 표기를
		//거창하게 사용할 필요가 없는가? 기능이 같지만 표기법이 간결해짐
		//e는 actionPerformed
		//람다 표현시근 객체를 마치 함수처러 간결하게 사용할 수 있도록 지원하는 표기법이다.
		bt_regist.addActionListener((e)->{
			setPage(MEMBER_REGIST);
		});
		
		
			
		//로그인 버튼과 리스너 연결
		bt_login.addActionListener((e)->{
			setPage(MEMBER_LOGIN);
		});
		
		setVisible(true);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	//이 프로그램에서 사용되는 모든 페이지를 제어하는 메서드
	public void setPage(int showIndex) {//보여주고 싶은 페이지 index 넘겨받자
		for (int i = 0; i < pages.length; i++) {
			if(i == showIndex) {
				pages[i].setVisible(true);
			}else {
				pages[i].setVisible(false);				
			}
		}
	}
	
	public static void main(String[] args) {
		new BoardApp();
	}
}


























