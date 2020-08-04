package com.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.controller.GUIController;
import com.dao.DBConnection;
import com.model.User;

public class LoginForm extends JFrame implements ActionListener {
	
	private static String title = "Park Auto";
	
	JTextField textUser = new JTextField();
	JPasswordField textPass = new JPasswordField();
	
	
	
	public String getTextUser() {
		return textUser.getText();
	}


	public String getTextPass() {
		return textPass.getText();
	}


	public LoginForm() {
	super();
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setTitle(title);
	setResizable(false);
	setAlwaysOnTop(true);
	setSize(new Dimension(250, 150));
	
	Container container = getContentPane();
	container.setLayout(new BorderLayout());
	JPanel cPanel;
	JPanel bPanel;
	JPanel uPanel;
	JPanel pPanel;
	cPanel = new JPanel();
	bPanel = new JPanel();
	uPanel = new JPanel();
	pPanel = new JPanel();

	JLabel label1 = new JLabel("Login with your username and password");
	container.add(label1, BorderLayout.NORTH);
	
	JLabel labelUser = new JLabel("Username:");
	JLabel labelPass = new JLabel("Password:");
	
//	JTextField textUser = new JTextField();
	textUser.setPreferredSize(new Dimension(100,20));
//	JTextField textPass = new JTextField();
	textPass.setPreferredSize(new Dimension(100,20));
	
	cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
	uPanel.setLayout(new FlowLayout());
	pPanel.setLayout(new FlowLayout());
	uPanel.add(labelUser);
	uPanel.add(textUser);
	pPanel.add(labelPass);
	pPanel.add(textPass);
	cPanel.add(uPanel);
	Dimension minSize = new Dimension(1, 1);
	Dimension prefSize = new Dimension(1, 1);
	Dimension maxSize = new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
	cPanel.add(new Box.Filler(minSize, prefSize, maxSize));
	cPanel.add(pPanel);
	cPanel.add(Box.createRigidArea(new Dimension(10,10)));
	container.add(cPanel, BorderLayout.CENTER);
	
	GUIController action = new GUIController();
	
	JButton loginButon = new JButton("Login");
	loginButon.setActionCommand("login");
	loginButon.addActionListener(this);
	JButton exitButon = new JButton("Exit");
	exitButon.setActionCommand("exit");
	exitButon.addActionListener(this);;
	
	bPanel.add(loginButon);
	bPanel.add(exitButon);
	container.add(bPanel, BorderLayout.SOUTH);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ("exit".equals(e.getActionCommand())) {
			System.exit(0);
		}
		else if ("login".equals(e.getActionCommand())) {
			DBConnection connection = new DBConnection();
			connection.connect();
			
			User user = connection.checkLoginInfo(textUser.getText(), textPass.getText());
			if(user != null) {
				ApplicationForm autoPark = new ApplicationForm(user, connection);
				autoPark.setVisible(true);
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(this, "The username or password is incorrect. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}
