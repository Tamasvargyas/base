package hu.bme.mit.train.system;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Table;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	TrainSystem system;
	
	@Before
	public void before() {
		system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingEmergencyBreak_ActivatesEmergencyBreak() {
		user.applyEmergencyBreak();
		Assert.assertEquals(true, controller.getEmergencyBreak());
	}

	@Test
	public void testTable() {
		LocalDate date = LocalDate.now();
		Integer joy = user.getJoystickPosition();
		Integer speed = controller.getReferenceSpeed();
		system.addRow(date, joy, speed);
		Assert.assertEquals(true, system.getTable().contains(date, joy));
		Assert.assertEquals(true, system.getTable().contains(date, speed));
	}

	@Test
	public void testSpeed() {
		user.overrideJoystickPosition(1);
		system.runTrain();
		Assert.assertEquals(1, controller.getReferenceSpeed());		
	}

	
}
