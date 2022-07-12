package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import model.builders.ParkingFloorBuilder;
import model.builders.ParkingLotBuilder;
import model.builders.ParkingSlotBuilder;
import model.pojos.Car;
import model.pojos.ParkingLot;
import model.pojos.ParkingSlot;
import model.util.RandomUtil;

@RunWith(MockitoJUnitRunner.class)
public class ParkingSystemTest {

	private ParkingSystem parkingSystem;
	private ParkingLot parkingLot;
	
	@Mock
	private RandomUtil randomUtil;

	@Before
	public void init() {
		
		// todo return once / return plenty different sizes
		when(randomUtil.generateRandom(ParkingSlot.Size.values().length)).thenReturn(0);
		
		// specs
		Pair<Integer, Integer> dimension = Pair.with(4, 4);
		Map<Integer, Pair<Integer, Integer>> entrances = new HashMap<>();
		entrances.put(1, Pair.with(0, 1));

		// initialize parkingLot objects and dependencies
		ParkingSlotBuilder parkingSlotBuilder = new ParkingSlotBuilder(randomUtil);
		ParkingFloorBuilder parkingFloorBuilder = new ParkingFloorBuilder(parkingSlotBuilder);
		ParkingLotBuilder parkingLotBuilder = new ParkingLotBuilder(parkingFloorBuilder, dimension, entrances);

		// build parkingLot
		parkingLotBuilder.addFloor();
		parkingLot = parkingLotBuilder.getLot();
		parkingSystem = new ParkingSystem(parkingLot);
	}

	@Test
	public void testCarFeeThreeHours() {
		// given
		Car car1 = new Car(1, Car.Size.SMALL);
		parkingSystem.assignCar(1, car1, (long) 1657447200);
		
		// when
		double fee = parkingSystem.freeCar(car1, (long)1657458000);
		
		// then
		assertThat(fee).isEqualTo(40.0);
	}
	
	@Test
	public void testCarFeeExceedThreeHours() {
		// given
		Car car1 = new Car(1, Car.Size.SMALL);
		parkingSystem.assignCar(1, car1, (long) 1657447200);
		
		// when
		double fee = parkingSystem.freeCar(car1, (long)1657459800);
		
		// then
		assertThat(fee).isEqualTo(60.00);
	}
	
	@Test
	public void testCarFeeOneDay() {
		// given
		Car car1 = new Car(1, Car.Size.SMALL);
		parkingSystem.assignCar(1, car1, (long) 1657447200);
		
		// when
		double fee = parkingSystem.freeCar(car1, (long)1657533600);
		
		// then
		assertThat(fee).isEqualTo(5000.00);
	}
	
	@Test
	public void testCarFeeExceedOneDay() {
		// given
		Car car1 = new Car(1, Car.Size.SMALL);
		parkingSystem.assignCar(1, car1, (long) 1657447200);
		
		// when
		double fee = parkingSystem.freeCar(car1, (long)1657535400);
		
		// then
		assertThat(fee).isEqualTo(5020.00);
	}
}
