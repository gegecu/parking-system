
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import model.ParkingSystem;
import model.Transaction;
import model.builders.ParkingFloorBuilder;
import model.builders.ParkingLotBuilder;
import model.builders.ParkingSlotBuilder;
import model.pojos.Car;
import model.pojos.ParkingLot;
import model.pojos.ParkingSlot;
import model.util.RandomUtil;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// specs
		Pair<Integer, Integer> dimension = Pair.with(4, 4);
		Map<Integer, Pair<Integer, Integer>> entrances = new HashMap<>();
		entrances.put(1, Pair.with(0, 1));
		entrances.put(2, Pair.with(3, 3));
		entrances.put(3, Pair.with(2, 0));

		// initialize parkingLot objects and dependencies
		RandomUtil randomUtil = RandomUtil.getInstance();
		ParkingSlotBuilder parkingSlotBuilder = new ParkingSlotBuilder(randomUtil);
		ParkingFloorBuilder parkingFloorBuilder = new ParkingFloorBuilder(parkingSlotBuilder);
		ParkingLotBuilder parkingLotBuilder = new ParkingLotBuilder(parkingFloorBuilder, dimension, entrances);

		// build parkingLot
		parkingLotBuilder.addFloor();
		ParkingLot parkingLot = parkingLotBuilder.getLot();
		ParkingSystem system = new ParkingSystem(parkingLot);
		// printParkingSlots
		printParkingSlots(system.getParkingSlots());

		// create car
		Car car1 = new Car(1, Car.Size.SMALL);
		Car car2 = new Car(2, Car.Size.MEDIUM);

		system.assignCar(1, car1, (long) 1657447200); // 10 July 2022 10:00:00 -- car1
		system.assignCar(1, car2, (long) 1657448700); // 10 July 2022 10:25:00 -- car2
		printCarInfo(car1);
		printCarInfo(car2);
		system.freeCar(car1, (long) 1657449000); // 10 July 2022 10:30:00 -- car1

		system.assignCar(1, car1, (long) 1657450800); // 10 July 2022 11:00:00 -- car1
		printCarInfo(car1);
		system.freeCar(car2, (long) 1657452300); // 10 July 2022 11:25:00 -- car2
		system.freeCar(car1, (long) 1657454400); // 10 July 2022 12:00:00 -- car1

		system.assignCar(1, car2, (long) 1657455300); // 10 July 2022 12:15:00 -- car2
		printCarInfo(car2);
		system.assignCar(1, car1, (long) 1657456200); // 10 July 2022 12:30:00 -- car1
		printCarInfo(car1);
		system.freeCar(car1, (long) 1657459800); // 10 July 2022 13:30:00 -- car1
		double car2Fee = system.freeCar(car2, (long) 1657459800); // 10 July 2022 13:30:00 -- car2
		System.out.println("car2 fee: " + car2Fee);

		system.assignCar(1, car1, (long) 1657460700); // 10 July 2022 13:45:00 -- car1
		printCarInfo(car1);
		double car1Fee = system.freeCar(car1, (long) 1657464300); // 10 July 2022 14:45:00 -- car1
		System.out.println("car1 fee: " + car1Fee);

		/* -------------------- */
		
		system.assignCar(1, car1, (long) 1657468800); // 10 July 2022 16:00:00 -- car1
		printCarInfo(car1);
		system.freeCar(car1, (long) 1657643400); // 12 July 2022 16:30:00 -- car1

		system.assignCar(1, car1, (long) 1657647000); // 12 July 2022 17:30:00 -- car1
		printCarInfo(car1);
		double car1Fee2 = system.freeCar(car1, (long) 1657650600); // 12 July 2022 18:30:00 -- car1
		Transaction transaction = system.getTransaction(car1);
		printCarTransaction(transaction, car1);
		System.out.println("car1 fee: " + car1Fee2);
	}

	private static void printParkingSlots(List<ParkingSlot> parkingSlots) {
		for (ParkingSlot slot : parkingSlots) {
			System.out.println("slot id: " + slot.getSlotId() + " slot size: " + slot.getSize() + " slot occupied: "
					+ slot.isOccupied() + (slot.isOccupied() ? " with car id: " + slot.getCar().getId() : ""));
		}
	}

	private static void printCarInfo(Car car) {
		System.out.println("car id: " + car.getId() + " car size: " + car.getSize()
				+ (car.getSlot() != null
						? (" assigned to " + car.getSlot().getSlotId() + " slot size: " + car.getSlot().getSize())
						: ""));
	}

	private static void printCarTransaction(Transaction transaction, Car car) {
		List<Triplet<Long, Long, ParkingSlot>> parkedTimes = transaction.getParkedTimes();
		for (Triplet<Long, Long, ParkingSlot> parkedTime : parkedTimes) {
			System.out.println(car.getId() + " has parked at: " + new Date(parkedTime.getValue0() * 1000) + " and has freed at: "
					+ new Date(parkedTime.getValue1() * 1000) + " at slot " + parkedTime.getValue2().getSlotId());
		}
	}

}