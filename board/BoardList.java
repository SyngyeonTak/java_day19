/*게시물 목록 페이지*/

package day1111.board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BoardList extends JPanel{
	JTable table;
	JScrollPane scroll;
	JButton bt;
	BoardApp boardApp;
	public BoardList(BoardApp boardApp) {
		this.boardApp = boardApp;
		table = new JTable(8, 5);
		scroll = new JScrollPane(table);
		bt = new JButton("글 등록");
		setLayout(new BorderLayout());
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				boardApp.setPage(boardApp.BOARD_DETAIL);//상세보기
			}
		});
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//글쓰기 폼 보여주기
				boardApp.setPage(boardApp.BOARD_WRITE);
			}
		});
		
		add(scroll);
		add(bt, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(780, 500));
		setVisible(true);
	}
	
}
