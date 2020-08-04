package com.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.dao.DBConnection;
import com.model.User;
import com.model.Vehicle;


public class ApplicationForm extends JFrame implements ActionListener {
	
	private static String title = "Auto Park";
	
	
	String[] COLHEADS = {"ID", "BRAND", "HP", "VIN", "COLOR", "AUTOMATIC"};
	String[][] DATA = {	};
	User muser;
	DBConnection mconnection;
	JTable table = new JTable();

	
	public ApplicationForm(User user, DBConnection connection) {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(title);
		setResizable(true);
		setAlwaysOnTop(true);
		setSize(new Dimension(500, 500));
		
		mconnection = connection;		
		muser = user;
		
		JPanel tPanel;
		JPanel bPanel;
//		JPanel cPanel;
		JPanel tPanelRight;
		JPanel bPanelTop;
		JPanel bPanelBot;
		tPanel = new JPanel();
		bPanel = new JPanel();
//		cPanel = new JPanel();
		tPanelRight = new JPanel();
		bPanelTop = new JPanel();
		bPanelBot = new JPanel();

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		JLabel labelWelcome = new JLabel("Welcome to Auto Park");
		JLabel labelLog = new JLabel("You are logg in as: " + muser.getUsername());
		JButton logOutButon = new JButton("Log out");
		logOutButon.setActionCommand("logOut");
		logOutButon.addActionListener(this);
		
		tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.X_AXIS));
		tPanel.add(labelWelcome);
		tPanel.add(Box.createHorizontalGlue());
		tPanelRight.setLayout(new BoxLayout(tPanelRight, BoxLayout.X_AXIS));
		tPanelRight.add(labelLog);
		tPanelRight.add(logOutButon);
		tPanel.add(tPanelRight);
		container.add(tPanel, BorderLayout.NORTH);
		
		JButton addButon = new JButton("Add");
		addButon.setActionCommand("addCar");
		addButon.addActionListener(this);
		JButton editButon = new JButton("Edit");
		editButon.setActionCommand("editCar");
		editButon.addActionListener(this);
		JButton deleteButon = new JButton("Delete");
		deleteButon.setActionCommand("deleteCar");
		deleteButon.addActionListener(this);
		JButton exitButon = new JButton("Exit");	
		exitButon.setActionCommand("exit");
		exitButon.addActionListener(this);
		
		if(user.getRole().equals("viewer")) {
			addButon.setEnabled(false);
			editButon.setEnabled(false);
			deleteButon.setEnabled(false);
		}
		else {
			addButon.setEnabled(true);
			editButon.setEnabled(true);
			deleteButon.setEnabled(true);
		}

		
		//or use this method to set a new JTable Model:
		table.setModel(new DefaultTableModel(DATA, COLHEADS));

		JScrollPane myscrollpane = new JScrollPane(table);
		container.add(myscrollpane, BorderLayout.CENTER);

		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));
		bPanelTop.setLayout(new BoxLayout(bPanelTop, BoxLayout.X_AXIS));
		bPanelTop.add(addButon);
		bPanelTop.add(editButon);
		bPanelTop.add(deleteButon);
		bPanelTop.add(Box.createHorizontalGlue());
		bPanel.add(bPanelTop);
		bPanelBot.setLayout(new BoxLayout(bPanelBot, BoxLayout.X_AXIS));
		bPanelBot.add(Box.createHorizontalGlue());
		bPanelBot.add(exitButon);
		bPanel.add(bPanelBot);
		container.add(bPanel, BorderLayout.SOUTH);
		

		
		initTable();		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if("exit".equals(e.getActionCommand())) {
			System.exit(0);
		} 
		else if("addCar".equals(e.getActionCommand())) {
			AddEditForm addCar = new AddEditForm(this, null, mconnection);
			addCar.setVisible(true);
		}
		else if("logOut".equals(e.getActionCommand())) {
			LoginForm log = new LoginForm();
			log.setVisible(true);
			dispose();
		}
		else if("deleteCar".equals(e.getActionCommand())) {
			int rowSelected = table.getSelectedRow();
			if(rowSelected == -1) {
				JOptionPane.showMessageDialog(this, "Selecteaza un rand", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				String vehicleBrand = (String) table.getValueAt(rowSelected, 1);
				String vehicleVin = (String) table.getValueAt(rowSelected, 3);
				int sel = JOptionPane.showConfirmDialog(this, "Sigur stergi?", "Delete", JOptionPane.YES_NO_OPTION);
				if(sel == JOptionPane.YES_OPTION) {
					mconnection.deleteVehicle(vehicleBrand, vehicleVin);
					initTable();
				}
			}
		}
		else if("editCar".equals(e.getActionCommand())) {
			Vehicle vehicul = new Vehicle();
			int rowSelected = table.getSelectedRow();
			if(rowSelected == -1) {
				JOptionPane.showMessageDialog(this, "Selecteaza un rand", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				vehicul.setId(Integer.valueOf((String) table.getValueAt(rowSelected, 0)));
				vehicul.setBrand((String) table.getValueAt(rowSelected, 1));
				vehicul.setHorsePower(Integer.valueOf((String) table.getValueAt(rowSelected, 2)));
				vehicul.setVin((String) table.getValueAt(rowSelected, 3));
				vehicul.setColor((String) table.getValueAt(rowSelected, 4));
				vehicul.setAutomatic(Boolean.valueOf((String) table.getValueAt(rowSelected, 5)));
				AddEditForm editCar = new AddEditForm(this, vehicul, mconnection);
				editCar.setVisible(true);
				editCar.textBrand.setText((String) table.getValueAt(rowSelected, 1));
			}
		}
	}

	
	public void initTable() {
		
		ArrayList<Vehicle> vehicleList = mconnection.getAllVehicles();
		if(vehicleList.size() > 0) {
			String[][] mat = new String[vehicleList.size()][COLHEADS.length];
			for(int i = 0; i < vehicleList.size(); ++i) {
				mat[i][0] =String.valueOf(vehicleList.get(i).getId());
				mat[i][1] =vehicleList.get(i).getBrand();
				mat[i][2] =String.valueOf(vehicleList.get(i).getHorsePower());
				mat[i][3] =vehicleList.get(i).getVin();
				mat[i][4] =vehicleList.get(i).getColor();
				mat[i][5] =String.valueOf(vehicleList.get(i).isAutomatic());
			}
			table.setModel(new DefaultTableModel(mat, COLHEADS){

			    @Override
			    public boolean isCellEditable(int row, int column) {
			       return false;
			    }
			});
	}
	
}
}
