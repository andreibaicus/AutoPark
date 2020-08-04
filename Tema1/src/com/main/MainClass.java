package com.main;


import java.util.ArrayList;
import com.dao.DBConnection;
import com.gui.AddEditForm;
import com.gui.ApplicationForm;
import com.gui.LoginForm;
import com.model.User;
import com.model.Vehicle;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LoginForm logFrame = new LoginForm();
		logFrame.setVisible(true);
		
//		ApplicationForm myFrame = new ApplicationForm();
//		myFrame.setVisible(true);
		
//		AddEditForm editFrame = new AddEditForm(myFrame);
//		editFrame.setVisible(true);

		DBConnection connection = new DBConnection();
		connection.connect();
		
		User user = new User();
		user = connection.checkLoginInfo(logFrame.getTextUser(), logFrame.getTextPass());
//		
//		Vehicle myVehicle = new Vehicle();
//		myVehicle = connection.getVehicleByBrandAndVin("Audi", "Audi001");
//		
//		System.out.println("Vehiculul are brandul:" + myVehicle);
//		
//		ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
//		vehicles = connection.getAllVehicles();
//		
//		for (Vehicle vehicle: vehicles) {
//			System.out.println("Vehiculele din DB au urmatoarele:" + vehicle);
//		}
		
//		Vehicle editvehicul = connection.getVehicleByBrandAndVin("Mercedes", "Mercedes001");
//		editvehicul.setBrand("Audi");
//		editvehicul.setAutomatic(true);
//		editvehicul.setColor("gray");
//		editvehicul.setHorsePower(140);
//		editvehicul.setVin("Audi002");
//		
//		int updateCount = connection.updateVehicle(editvehicul);
//		
//		if(updateCount == 1) {
//			System.out.println("Vehiculul a fost editat!!");
//		}
		
		
//		int deleteCount = connection.deleteVehicle("Audi", "Audi002");
//		
//		if(deleteCount ==1) {
//			System.out.println("Vehiculul a fost sters!!");
//		}
		
//		myVehicle.setBrand("opel");
//		myVehicle.setAutomatic(true);
//		myVehicle.setColor("white");
//		myVehicle.setHorsePower(90);
//		myVehicle.setVin("opel002");
//		
//		int addCount = connection.addVehicle(myVehicle);
//		
//		if(addCount == 1) {
//			System.out.println("Vehiculul a fost adaugat!!");
//		}
		
	}

}
