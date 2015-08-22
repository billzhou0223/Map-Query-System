import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.*;

import javax.swing.border.MatteBorder;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.ButtonGroup;

import java.sql.*;

import oracle.spatial.geometry.*;
import oracle.sql.STRUCT;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class GUI extends JFrame {

	private JPanel contentPane;
	private MapPanel mapPanel;
	private JPanel pnlQueryHis;
	private JPanel pnlCoodinate;
	private JPanel pnlQuery;
	private JLabel label;
	private JTextField tfX;
	private JLabel lblY;
	private JTextField tfY;
	private JCheckBox chckbxBuilding;
	private JCheckBox chckbxPhoto;
	private JCheckBox chckbxPhotographer;
	private JLabel lblNewLabel;
	private JPanel panel_6;
	private JRadioButton rdbtnWholeRegion;
	private JRadioButton rdbtnRangeQuery;
	private JRadioButton rdbtnPointQuery;
	private JRadioButton rdbtnFindPhoto;
	private JRadioButton rdbtnFindPhotographers;
	private JPanel panel;
	private JButton btnSubmit;
	private JScrollPane scrollPane;
	private JTextArea taHistory;
	
	//checkbox state
	private ArrayList<String> feature;

	//mouse track
//	private String x = "";
//	private String y = "";
	
	//query window
	private ArrayList<Double> win;

	//reload mapPanel thread
//	Thread thread = null;
	
	//find photo
	int findPhotoStep = 1;
	String strSelectedPhotographer = "";
	
	//find photographer
	String strSelectedBuilding = "";
	String strSelectedCenter = "";
	String strSelectedBuildingId = "";
	
	//query history
	int increment = 1;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		setTitle("Chicheng Zhou     00001070966");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1173, 727);
		contentPane = new JPanel();
		contentPane.setBorder(null);
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
//		contentPane.setBounds(0, 0, 820, 580);
		contentPane.setLayout(null);
		
		//mapPanel
		mapPanel = new MapPanel();
		mapPanel.setBorder(null);
		mapPanel.setLocation(0, 0);
		mapPanel.setBounds(0, 0, 820, 580);
		contentPane.add(mapPanel);
		//add listener
		mapPanel.addMouseMotionListener(new MouseMotionEventAdapter());
		mapPanel.addMouseListener(new MouseEventAdapter());
		
		pnlQueryHis = new JPanel();
		pnlQueryHis.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlQueryHis.setBounds(0, 580, 1170, 112);
		contentPane.add(pnlQueryHis);
		pnlQueryHis.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		taHistory = new JTextArea();
		taHistory.setRows(4);
		taHistory.setColumns(100);
		taHistory.setEditable(false);
		scrollPane = new JScrollPane(taHistory);
		pnlQueryHis.add(scrollPane);
		
		pnlQuery = new JPanel();
		pnlQuery.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlQuery.setBounds(820, 164, 350, 378);
		contentPane.add(pnlQuery);
		pnlQuery.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("  Query");
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setFont(new Font("SimSun", Font.PLAIN, 17));
		pnlQuery.add(lblNewLabel, BorderLayout.NORTH);
		
		panel_6 = new JPanel();
		pnlQuery.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new GridLayout(5, 0, 0, 0));
		
		rdbtnWholeRegion = new JRadioButton("Whole region");
		rdbtnWholeRegion.setSelected(true);
		rdbtnWholeRegion.setActionCommand("Whole region");
		panel_6.add(rdbtnWholeRegion);
		
		rdbtnRangeQuery = new JRadioButton("Range query");
		rdbtnRangeQuery.setActionCommand("Range query");
		panel_6.add(rdbtnRangeQuery);
		
		rdbtnPointQuery = new JRadioButton("Point query");
		rdbtnPointQuery.setActionCommand("Point query");
		panel_6.add(rdbtnPointQuery);
		
		rdbtnFindPhoto = new JRadioButton("Find photos");
		rdbtnFindPhoto.setActionCommand("Find photos");
		panel_6.add(rdbtnFindPhoto);
		
		rdbtnFindPhotographers = new JRadioButton("Find photographers");
		rdbtnFindPhotographers.setActionCommand("Find photographers");
		panel_6.add(rdbtnFindPhotographers);
		
		//radioButton group
		ButtonGroup group = new ButtonGroup (); 
		group.add(rdbtnWholeRegion);
		group.add(rdbtnRangeQuery);
		group.add(rdbtnPointQuery);
		group.add(rdbtnFindPhoto);
		group.add(rdbtnFindPhotographers);
		
		//add radioButton listener
		rdbtnWholeRegion.addActionListener(new RadioButtonActionHandler());
		rdbtnRangeQuery.addActionListener(new RadioButtonActionHandler());
		rdbtnPointQuery.addActionListener(new RadioButtonActionHandler());
		rdbtnFindPhoto.addActionListener(new RadioButtonActionHandler());
		rdbtnFindPhotographers.addActionListener(new RadioButtonActionHandler());
		
		pnlCoodinate = new JPanel();
		pnlCoodinate.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlCoodinate.setBounds(820, 0, 350, 34);
		contentPane.add(pnlCoodinate);
		
		label = new JLabel("X");
		pnlCoodinate.add(label);
		
		tfX = new JTextField();
		tfX.setEditable(false);
		tfX.setColumns(10);
		pnlCoodinate.add(tfX);
		
		lblY = new JLabel("Y");
		pnlCoodinate.add(lblY);
		
		tfY = new JTextField();
		tfY.setEditable(false);
		pnlCoodinate.add(tfY);
		tfY.setColumns(10);
		
		JPanel m_pnlAFT = new JPanel();
		m_pnlAFT.setBorder(new LineBorder(new Color(0, 0, 0)));
		m_pnlAFT.setBounds(820, 34, 350, 130);
		contentPane.add(m_pnlAFT);
		m_pnlAFT.setLayout(new BorderLayout(0, 0));
		
		JLabel lblActuralFeatureType = new JLabel("  Active Feature Type");
		lblActuralFeatureType.setFont(new Font("SimSun", Font.PLAIN, 17));
		m_pnlAFT.add(lblActuralFeatureType, BorderLayout.NORTH);
		
		JPanel panel_5 = new JPanel();
		m_pnlAFT.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new GridLayout(2, 2, 0, 0));
		
		chckbxBuilding = new JCheckBox("Building");
		panel_5.add(chckbxBuilding);
		
		chckbxPhotographer = new JCheckBox("Photographer");
		panel_5.add(chckbxPhotographer);
		
		chckbxPhoto = new JCheckBox("Photo");
		panel_5.add(chckbxPhoto);
		
		//add chckbx itemListener
		chckbxBuilding.addItemListener(new CheckboxItemListener());
		chckbxPhotographer.addItemListener(new CheckboxItemListener());
		chckbxPhoto.addItemListener(new CheckboxItemListener());
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(820, 542, 350, 38);
		contentPane.add(panel);
		
		btnSubmit = new JButton("Submit query");
		btnSubmit.addActionListener(new ButtonActionListener());
		panel.add(btnSubmit);
		
		//initialize
		feature = new ArrayList<String>();
		win = new ArrayList<Double>();
	}
	
	//display mouse coodinate
	class MouseMotionEventAdapter extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent e){
			String x = Integer.toString(e.getX());
			String y = Integer.toString(e.getY());
			tfX.setText(x);
			tfY.setText(y);
		}
	}
	
	//mouse left and right button click
	class MouseEventAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			String x = Integer.toString(e.getX());
			String y = Integer.toString(e.getY());
			
			if(rdbtnWholeRegion.isSelected()){
				//do nothing
			}else if(rdbtnRangeQuery.isSelected()){
				if(e.getButton() == e.BUTTON1){   //left button
					double dX = Double.parseDouble(x);
					double dY = Double.parseDouble(y);
					win.add(dX);
					win.add(dY);
					double[] coodinate = {dX, dY};
					mapPanel.drawSquare(coodinate, Color.RED);
				}else if(e.getButton() == e.BUTTON3){   //right button
					int size = win.size();
					double[] coodinate = new double[size+2];
					for(int i=0; i<size; i++){
						coodinate[i] = win.get(i);
					}
					coodinate[size] = win.get(0);
					coodinate[size+1] = win.get(1);
					win.add(coodinate[size]);
					win.add(coodinate[size+1]);
					mapPanel.drawPolygon(coodinate, Color.RED);				
				}
			}else if(rdbtnPointQuery.isSelected()){
				if(e.getButton() == e.BUTTON1){   //left button
					double dX = Double.parseDouble(x);
					double dY = Double.parseDouble(y);
					win.add(dX);
					win.add(dY);
					double[] coodinate = {dX, dY};
					mapPanel.drawSquare(coodinate, Color.RED);
					mapPanel.drawCircle(coodinate, 100, Color.RED);
					
				}else if(e.getButton() == e.BUTTON3){   //right button
					
				}
			}else if(rdbtnFindPhoto.isSelected()){				
				if(e.getButton() == e.BUTTON1){   //left button
					if (findPhotoStep == 1){
						double dX = Double.parseDouble(x);
						double dY = Double.parseDouble(y);
						String center = Integer.toString((int)dX) + "," + Integer.toString((int)dY);
						
						
						Connection con = null;
						Statement statement = null;
						try {
							con = ConnectToDB.openConnection();
							statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
							
							String sql = "SELECT pg.shape, pg.pgID "
									+ "FROM photographer pg "
									+ "WHERE SDO_NN(pg.shape, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(" + center + ",NULL), NULL, NULL), 'sdo_num_res=1') = 'TRUE'";
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							//judge number rows in the resultset
							rs.last();
							if(rs.getRow() != 1) {
								System.out.println("Error");
								return;
							}
							rs.beforeFirst();
							
							while(rs.next()){			
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawSquare(coodinate, Color.RED);
								
								strSelectedPhotographer = rs.getString(2);
//								System.out.println(strSelectedPhotographer);
							}
							Thread.sleep(1500);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						ConnectToDB.closeConnection(con);
						findPhotoStep = 2;
						if(findPhotoStep == 2){
							JOptionPane.showMessageDialog(contentPane, "First select vertices of rang query window by clicking left button, then enclose window by clicking right button","STEP 2", JOptionPane.INFORMATION_MESSAGE);
						}
					}else if(findPhotoStep == 2){
						double dX = Double.parseDouble(x);
						double dY = Double.parseDouble(y);
						win.add(dX);
						win.add(dY);
						double[] coodinate = {dX, dY};
						mapPanel.drawCircle(coodinate, Color.RED);
					}
				}else if(e.getButton() == e.BUTTON3){   //right button
					int size = win.size();
					double[] coodinate = new double[size+2];
					for(int i=0; i<size; i++){
						coodinate[i] = win.get(i);
					}
					coodinate[size] = win.get(0);
					coodinate[size+1] = win.get(1);
					win.add(coodinate[size]);
					win.add(coodinate[size+1]);
					mapPanel.drawPolygon(coodinate, Color.RED);	
					
					findPhotoStep = 1;
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(contentPane, "Now click submit button","STEP 3", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}else{
				if(e.getButton() == e.BUTTON1){   //left button
						strSelectedCenter = x + "," + y;
												
						Connection con = null;
						Statement statement = null;
						try {
							con = ConnectToDB.openConnection();
							statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
							
							String sql = "SELECT b.shape, b.bid "
									+ "FROM building b "
									+ "WHERE SDO_CONTAINS(b.shape, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(" + strSelectedCenter + ",NULL), NULL, NULL)) = 'TRUE'";							
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							//judge number rows in the resultset
							rs.last();
							int a = rs.getRow();
							if(rs.getRow() != 1) {
								System.out.println("Error");
								return;
							}
							rs.beforeFirst();
							while(rs.next()){			
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getOrdinatesArray();
								mapPanel.drawPolygon(coodinate, Color.RED);
								
								for(int i=0; i<coodinate.length; i++){
									strSelectedBuilding += String.valueOf((int)coodinate[i]) + ",";
								}
								strSelectedBuilding = strSelectedBuilding.substring(0, strSelectedBuilding.length()-1);
								strSelectedBuildingId = rs.getString(2);
								System.out.println(strSelectedBuildingId);
							}							
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						ConnectToDB.closeConnection(con);
				}
			}
		} 
	}
	
	class ButtonActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(rdbtnWholeRegion.isSelected()){   //whole region query				
				int count = feature.size();
				if(count == 0){
					JOptionPane.showMessageDialog(contentPane, "Please choose at least one feature","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Connection con = null;
				Statement statement = null;
				try {
					con = ConnectToDB.openConnection();
					statement = con.createStatement();
					String history = "Query " + String.valueOf(increment) + ": " + "\n";
					taHistory.append(history);
					increment++;
					for(int i=0; i<count; i++){
						String sql = "SELECT shape "
								+ "FROM " + feature.get(i);						
						statement.executeQuery(sql);
						ResultSet rs = statement.getResultSet();
						if(feature.get(i) == "building"){
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getOrdinatesArray();
								mapPanel.drawPolygon(coodinate, Color.YELLOW);
							}
							history = sql + "\n";
							taHistory.append(history);
						}else if(feature.get(i) == "photographer"){
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawSquare(coodinate, Color.GREEN);
							}
							history = sql + "\n";
							taHistory.append(history);
						}else{
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawCircle(coodinate, Color.BLUE);
							}
							history = sql + "\n";
							taHistory.append(history);
						}
					} 
				}catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}					
				ConnectToDB.closeConnection(con);
			}else if(rdbtnRangeQuery.isSelected()){  //range query
				int count = feature.size();
				if(count == 0){
					JOptionPane.showMessageDialog(contentPane, "Please choose at least one feature","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Connection con = null;
				Statement statement = null;
				
				String strCoodinate = "";
				int size = win.size();
				for(int i=0; i<size; i++){
					double d = win.get(i);
					int in = (int)d;
					strCoodinate += Integer.toString(in) + ",";
				}
				strCoodinate = strCoodinate.substring(0, strCoodinate.length()-1);
				win.clear();
				try {
					con = ConnectToDB.openConnection();
					statement = con.createStatement();
					String history = "Query " + String.valueOf(increment) + ": " + "\n";
					taHistory.append(history);
					increment++;
					for(int i=0; i<count; i++){
						if(feature.get(i) == "building"){
							String sql = "SELECT b.shape "
									+ "FROM building b "
									+ "WHERE SDO_INSIDE(b.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(b.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE'";
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getOrdinatesArray();
								mapPanel.drawPolygon(coodinate, Color.YELLOW);
							}
							history = sql + "\n";
							taHistory.append(history);
						}else if(feature.get(i) == "photographer"){
							String sql = "SELECT pg.shape "
									+ "FROM " + "photographer pg "
									+ "WHERE SDO_INSIDE(pg.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(pg.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE'";
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawSquare(coodinate, Color.GREEN);
							}
							history = sql + "\n";
							taHistory.append(history);
						}else{
							String sql = "SELECT p.shape "
									+ "FROM " + "photo p "
									+ "WHERE SDO_INSIDE(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE'";
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawCircle(coodinate, Color.BLUE);
							}
							history = sql + "\n";
							taHistory.append(history);
						}
					} 
				}catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}					
				ConnectToDB.closeConnection(con);
			}else if(rdbtnPointQuery.isSelected()){  //point query
				int count = feature.size();
				if(count == 0){
					JOptionPane.showMessageDialog(contentPane, "Please choose at least one feature","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Connection con = null;
				Statement statement = null;
				
				//coodinate of center
				String center = "";				
				int size = win.size();
				int[] arrCenter = new int[size];
				for(int i=0; i<size; i++){
					double d = win.get(i);
					int in = (int)d;
					arrCenter[i] = in;
					center += Integer.toString(in) + ",";
				}
				center = center.substring(0, center.length()-1);
				
				//coodinate of threes points on circumference
				String points = "";
				String X1 = Integer.toString(arrCenter[0]);
				String Y1 = Integer.toString(arrCenter[1]+100);
				String X2 = Integer.toString(arrCenter[0]+100);
				String Y2 = Integer.toString(arrCenter[1]);
				String X3 = Integer.toString(arrCenter[0]);
				String Y3 = Integer.toString(arrCenter[1]-100);
				points = X1 + "," + Y1 + "," + X2 + "," + Y2 + "," + X3 + "," + Y3;
				
				win.clear();
				try {
					con = ConnectToDB.openConnection();
					statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					String history = "Query " + String.valueOf(increment) + ": " + "\n";
					taHistory.append(history);
					increment++;
					for(int i=0; i<count; i++){
						if(feature.get(i) == "building"){
							String sql = "SELECT b.shape "
									+ "FROM building b "
									+ "WHERE SDO_INSIDE(b.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(b.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE'";
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getOrdinatesArray();
								mapPanel.drawPolygon(coodinate, Color.GREEN);
							}
							history = sql + "\n";
							taHistory.append(history);
							
							sql = "SELECT b.shape "
									+ "FROM building b "
									+ "WHERE (SDO_INSIDE(b.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(b.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE')"
							        + "AND SDO_NN(b.shape, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(" + center + ",NULL), NULL, NULL), 'sdo_num_res=1') = 'TRUE'";
							statement.executeQuery(sql);
							rs = statement.getResultSet();
							//judge number rows in the resultset
							rs.last();
							if(rs.getRow() != 1) {
								System.out.println("Error");
								return;
							}
							rs.beforeFirst();
							
							while(rs.next()){			
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getOrdinatesArray();
								mapPanel.drawPolygon(coodinate, Color.YELLOW);
							}
							history = sql + "\n";
							taHistory.append(history);
						}else if(feature.get(i) == "photographer"){
							String sql = "SELECT pg.shape "
									+ "FROM " + "photographer pg "
									+ "WHERE SDO_INSIDE(pg.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(pg.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE'";
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawSquare(coodinate, Color.GREEN);
							}
							history = sql + "\n";
							taHistory.append(history);
							
							sql = "SELECT pg.shape "
									+ "FROM photographer pg "
									+ "WHERE (SDO_INSIDE(pg.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(pg.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE')"
							        + "AND SDO_NN(pg.shape, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(" + center + ",NULL), NULL, NULL), 'sdo_num_res=1') = 'TRUE'";
							statement.executeQuery(sql);
							rs = statement.getResultSet();
							//judge number rows in the resultset
							rs.last();
							if(rs.getRow() != 1) {
								System.out.println("Error");
								return;
							}
							rs.beforeFirst();
							
							while(rs.next()){			
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawSquare(coodinate, Color.YELLOW);
							}
							history = sql + "\n";
							taHistory.append(history);
						}else{
							String sql = "SELECT p.shape "
									+ "FROM " + "photo p "
									+ "WHERE SDO_INSIDE(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE'";
							statement.executeQuery(sql);
							ResultSet rs = statement.getResultSet();
							while(rs.next()){
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawCircle(coodinate, Color.GREEN);
							}
							history = sql + "\n";
							taHistory.append(history);
							
							sql = "SELECT p.shape "
									+ "FROM photo p "
									+ "WHERE (SDO_INSIDE(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE' "
									+ "OR SDO_OVERLAPBDYINTERSECT(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY(" + points + "))) = 'TRUE')"
							        + "AND SDO_NN(p.shape, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE(" + center + ",NULL), NULL, NULL), 'sdo_num_res=1') = 'TRUE'";
							statement.executeQuery(sql);
							rs = statement.getResultSet();
							//judge number rows in the resultset
							rs.last();
							if(rs.getRow() != 1) {
								System.out.println("Error");
								return;
							}
							rs.beforeFirst();
							
							while(rs.next()){			
								STRUCT st = (STRUCT)rs.getObject(1);
								JGeometry geom = JGeometry.load(st);
								double[] coodinate = geom.getPoint();
								mapPanel.drawCircle(coodinate, Color.YELLOW);
							}
							history = sql + "\n";
							taHistory.append(history);
						}
					} 
				}catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}					
				ConnectToDB.closeConnection(con);
			}else if(rdbtnFindPhoto.isSelected()){   //find photo query
				Connection con = null;
				Statement statement = null;
				
				String strCoodinate = "";
				int size = win.size();
				for(int i=0; i<size; i++){
					double d = win.get(i);
					int in = (int)d;
					strCoodinate += Integer.toString(in) + ",";
				}
				strCoodinate = strCoodinate.substring(0, strCoodinate.length()-1);
				win.clear();
				String history = "Query " + String.valueOf(increment) + ": " + "\n";
				taHistory.append(history);
				increment++;
				try {
					con = ConnectToDB.openConnection();
					statement = con.createStatement();
					String sql = "SELECT p.shape "
							+ "FROM " + "photo p "
							+ "WHERE (SDO_INSIDE(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE' "
							+ "OR SDO_OVERLAPBDYINTERSECT(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strCoodinate + "))) = 'TRUE')"
					        + "AND p.pgID = '" + strSelectedPhotographer + "'";
					statement.executeQuery(sql);
					ResultSet rs = statement.getResultSet();
					while(rs.next()){
						STRUCT st = (STRUCT)rs.getObject(1);
						JGeometry geom = JGeometry.load(st);
						double[] coodinate = geom.getPoint();
						mapPanel.drawCircle(coodinate, Color.RED);
					}
					history = sql + "\n";
					taHistory.append(history);
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ConnectToDB.closeConnection(con);
			}else{                                   //find photographer query
				Connection con = null;
				Statement statement = null;
				win.clear();
				String history = "Query " + String.valueOf(increment) + ": " + "\n";
				taHistory.append(history);
				increment++;
				try {
					con = ConnectToDB.openConnection();
					statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					String sql = "SELECT p.shape "
							+ "FROM " + "photo p "
							+ "WHERE SDO_WITHIN_DISTANCE(p.shape, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY(" + strSelectedBuilding + ")), 'distance=80') = 'TRUE' ";
					statement.executeQuery(sql);
					ResultSet rs = statement.getResultSet();
					while(rs.next()){
						STRUCT st = (STRUCT)rs.getObject(1);
						JGeometry geom = JGeometry.load(st);
						double[] coodinate = geom.getPoint();
						mapPanel.drawCircle(coodinate, Color.RED);
					}
					history = sql + "\n";
					taHistory.append(history);
									
					sql = "SELECT pg.shape "
						 +"FROM photographer pg";
					statement.executeQuery(sql);
					rs = statement.getResultSet();
					ArrayList<String> arrLines = new ArrayList<String>();
					while(rs.next()){
						STRUCT st = (STRUCT)rs.getObject(1);
						JGeometry geom = JGeometry.load(st);
						double[] coodinate = geom.getPoint();
						String line = strSelectedCenter + "," + String.valueOf((int)coodinate[0]) + "," + String.valueOf((int)coodinate[1]);
						arrLines.add(line);						
					}
					
					for(int i=0; i<arrLines.size(); i++){
						String line = arrLines.get(i);
						String sqlInsert = "INSERT INTO temp VALUES(SDO_GEOMETRY(2002, null, null, SDO_ELEM_INFO_ARRAY(1,2,1), SDO_ORDINATE_ARRAY(" + line + ")))";
						statement.executeUpdate(sqlInsert);
					}
					
					sql = "SELECT t.shape "
						+ "FROM temp t "
						+ "WHERE NOT EXISTS(SELECT b.shape "
						+	               "FROM building b "
						+	               "WHERE SDO_ANYINTERACT(b.shape, t.shape) = 'TRUE'"
						+	               "AND b.bid <> '" + strSelectedBuildingId + "')";
					statement.executeQuery(sql);
					rs = statement.getResultSet();
					while(rs.next()){
						STRUCT st = (STRUCT)rs.getObject(1);
						JGeometry geom = JGeometry.load(st);
						double[] coodinate = geom.getOrdinatesArray();
						double[] coodPhotographer = new double[2];
						coodPhotographer[0] = coodinate[2];
						coodPhotographer[1] = coodinate[3];
						mapPanel.drawSquare(coodPhotographer, Color.RED);
					}
					history = sql + "\n";
					taHistory.append(history);

					sql = "DELETE FROM temp";
					statement.executeUpdate(sql);
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ConnectToDB.closeConnection(con);
			}
		}
	}
	
	class RadioButtonActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand() == "Whole region"){
				mapPanel.repaint();
				findPhotoStep = 1;
				win.clear();
				strSelectedPhotographer = "";
				strSelectedBuilding = "";
				strSelectedCenter = "";
				strSelectedBuildingId = "";
				
			}else if(e.getActionCommand() == "Range query"){
				mapPanel.repaint();
				findPhotoStep = 1;
				win.clear();
				strSelectedPhotographer = "";
				strSelectedBuilding = "";
				strSelectedCenter = "";
				strSelectedBuildingId = "";
				
			}else if(e.getActionCommand() == "Point query"){
				mapPanel.repaint();
				findPhotoStep = 1;
				win.clear();
				strSelectedPhotographer = "";
				strSelectedBuilding = "";
				strSelectedCenter = "";
				strSelectedBuildingId = "";
				
			}else if(e.getActionCommand() == "Find photos"){
//				reloadImg();
//				thread.start();	
				new Thread(new Runnable(){
					public void run(){
						mapPanel.repaint();
						findPhotoStep = 1;
						win.clear();
						strSelectedPhotographer = "";
						strSelectedBuilding = "";
						strSelectedCenter = "";
						strSelectedBuildingId = "";
						showAllFeatures();
						if(findPhotoStep == 1){
							JOptionPane.showMessageDialog(contentPane, "Choose a photographer by clicking left button","STEP 1", JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
				}
						).start();
				
			}else{
				new Thread(new Runnable(){
					public void run(){
						mapPanel.repaint();
						findPhotoStep = 1;
						win.clear();
						strSelectedPhotographer = "";
						strSelectedBuilding = "";
						strSelectedCenter = "";
						strSelectedBuildingId = "";
						showAllFeatures();
						
					}
				}
						).start();
			}
		}
	}
	
	class CheckboxItemListener implements ItemListener{
		 public void itemStateChanged(ItemEvent e){
			 if(e.getSource() == chckbxBuilding){
				 if(e.getStateChange() == ItemEvent.SELECTED){
					 feature.add("building");
				 }else if(e.getStateChange() == ItemEvent.DESELECTED){
					 feature.remove("building");
				 }
			 }else if(e.getSource() == chckbxPhotographer){
				 if(e.getStateChange() == ItemEvent.SELECTED){
					 feature.add("photographer");
				 }else if(e.getStateChange() == ItemEvent.DESELECTED){
					 feature.remove("photographer");					 
				 }
			 }else{
				 if(e.getStateChange() == ItemEvent.SELECTED){
					 feature.add("photo");
				 }else if(e.getStateChange() == ItemEvent.DESELECTED){
					 feature.remove("photo");
				 }
			 }
		 }
	}
	
	private void showAllFeatures(){
		Connection con = null;
		Statement statement = null;
		try {
			con = ConnectToDB.openConnection();
			statement = con.createStatement();
		
			String sql = "SELECT shape "
					+ "FROM building";						
			statement.executeQuery(sql);
			ResultSet rs = statement.getResultSet();
			while(rs.next()){
				STRUCT st = (STRUCT)rs.getObject(1);
				JGeometry geom = JGeometry.load(st);
				double[] coodinate = geom.getOrdinatesArray();
				mapPanel.drawPolygon(coodinate, Color.YELLOW);
			}
			
			sql = "SELECT shape "
					+ "FROM photographer";	
			statement.executeQuery(sql);
			rs = statement.getResultSet();
			while(rs.next()){
				STRUCT st = (STRUCT)rs.getObject(1);
				JGeometry geom = JGeometry.load(st);
				double[] coodinate = geom.getPoint();
				mapPanel.drawSquare(coodinate, Color.GREEN);
			}

			sql = "SELECT shape "
				+ "FROM photo";
			statement.executeQuery(sql);
			rs = statement.getResultSet();
			while(rs.next()){
				STRUCT st = (STRUCT)rs.getObject(1);
				JGeometry geom = JGeometry.load(st);
				double[] coodinate = geom.getPoint();
				mapPanel.drawCircle(coodinate, Color.BLUE);
			}
		}catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}					
		ConnectToDB.closeConnection(con);
	}
}
