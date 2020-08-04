package com.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.dao.DBConnection;
import com.model.Vehicle;


public class AddEditForm extends JDialog implements ActionListener {
	
	private static String title = "Add a new car";
	Vehicle mVehicul;
	JTextField textBrand;
	JTextField textHP;
	JTextField textAutomatic;
	JTextField textKm;
	JTextField textColor;
	DBConnection mconnection;
	private JComboBox<String> combo;

	public AddEditForm(JFrame parent, Vehicle vehicul, DBConnection connection) {
		super(parent, title, true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		setSize(new Dimension(285,300));
		
		combo = new JComboBox<String>();
		combo.addItem("false");
		combo.addItem("true");
		
		JPanel cPanel;
		JPanel bPanel;
		JPanel brandPanel;
		JPanel hpPanel;
		JPanel automaticPanel;
		JPanel kmPanel;
		JPanel colorPanel;
		cPanel = new JPanel();
		bPanel = new JPanel();
		brandPanel = new JPanel();
		hpPanel = new JPanel();
		automaticPanel = new JPanel();
		kmPanel = new JPanel();
		colorPanel = new JPanel();
			
		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		JLabel label1 = new JLabel("Add a car:");
		container.add(label1, BorderLayout.NORTH);
		
		JLabel label2 = new JLabel("Brand:");
		JLabel label3 = new JLabel("HP:");
		JLabel label5 = new JLabel("VIN:");
		JLabel label6 = new JLabel("Color:");
		JLabel label4 = new JLabel("Automatic:");
		
		mVehicul = vehicul;
		
		textBrand = new JTextField();
		textBrand.setPreferredSize(new Dimension(100,20));
		textHP = new JTextField();
		textHP.setPreferredSize(new Dimension(100,20));
		textAutomatic = new JTextField();
		textAutomatic.setPreferredSize(new Dimension(100,20));
		textKm = new JTextField();
		textKm.setPreferredSize(new Dimension(100,20));
		textColor = new JTextField();
		textColor.setPreferredSize(new Dimension(100,20));
		
		if(mVehicul != null) {
			textBrand.setText(mVehicul.getBrand());
			textHP.setText(String.valueOf(mVehicul.getHorsePower()));
			textKm.setText(mVehicul.getVin());
			textColor.setText(mVehicul.getColor());
			textAutomatic.setText(combo.getItemAt(combo.getSelectedIndex()));
		}
		
		cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
		brandPanel.add(label2);
		brandPanel.add(textBrand);
		hpPanel.add(label3);
		hpPanel.add(textHP);
		kmPanel.add(label5);
		kmPanel.add(textKm);
		automaticPanel.add(label4);
		automaticPanel.add(combo);
		colorPanel.add(label6);
		colorPanel.add(textColor);
		cPanel.add(brandPanel);
		cPanel.add(hpPanel);
		cPanel.add(kmPanel);
		cPanel.add(colorPanel);
		cPanel.add(automaticPanel);
		container.add(cPanel, BorderLayout.CENTER);
		
		JButton saveButon = new JButton("Save");
		saveButon.setActionCommand("save");
		saveButon.addActionListener(this);
		JButton cancelButon = new JButton("Cancel");
		cancelButon.setActionCommand("cancel");
		cancelButon.addActionListener(this);
		
		
		bPanel.add(saveButon);
		bPanel.add(cancelButon);
		container.add(bPanel, BorderLayout.SOUTH);
		
		mconnection = connection;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("cancel".equals(e.getActionCommand())) {
			dispose();
		}
		else if("save".equals(e.getActionCommand())) {
			if(mVehicul == null) {
					addVehicle();
					dispose();
			}
			else
			{
				editVehicle();
				dispose();
			}
		}
		
	}
	
	private void addVehicle() {
		Vehicle vehicul = new Vehicle();
		vehicul.setBrand(textBrand.getText());
		vehicul.setHorsePower(Integer.valueOf(textHP.getText()));
		vehicul.setColor(textColor.getText());
		vehicul.setVin(textKm.getText());
		vehicul.setAutomatic(Boolean.valueOf(combo.getItemAt(combo.getSelectedIndex())));
		
		mconnection.addVehicle(vehicul);
		((ApplicationForm) getParent()).initTable();		
	}

	public void editVehicle() {
		Vehicle vehicul =new Vehicle();
		vehicul.setBrand(textBrand.getText());
		vehicul.setHorsePower(Integer.valueOf(textHP.getText()));
		vehicul.setColor(textColor.getText());
		vehicul.setVin(textKm.getText());
		vehicul.setAutomatic(Boolean.valueOf(combo.getItemAt(combo.getSelectedIndex())));
		vehicul.setId(mVehicul.getId());
		
		
		mconnection.updateVehicle(vehicul);
		((ApplicationForm) getParent()).initTable();
	}
	
}
