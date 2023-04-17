package hu.bme.mit.train.system;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import hu.bme.mit.train.controller.TrainControllerImpl;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.sensor.TrainSensorImpl;
import hu.bme.mit.train.user.TrainUserImpl;

public class TrainSystem {

	private TrainController controller = new TrainControllerImpl();
	private TrainUser user = new TrainUserImpl(controller);
	private TrainSensor sensor = new TrainSensorImpl(controller, user);
	Table<LocalDate, Integer, Integer> trainTable = HashBasedTable.create();

	

	public TrainController getController() {
		return controller;
	}

	public TrainSensor getSensor() {
		return sensor;
	}

	public TrainUser getUser() {
		return user;
	}

	public void addRow(LocalDate date, Integer joy, Integer speed) {
		trainTable.put(date, joy, speed);		
	}
	public Table<LocalDate, Integer, Integer> getTable() {
		return trainTable;
	}

	public void runTrain() {		
		controller.followSpeed();		
	}

	

}
