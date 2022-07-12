package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.pojos.Car;
import model.pojos.ParkingLot;
import model.pojos.ParkingSlot;

public class ParkingSystem {
	private ParkingLot parkingLot;
	private Map<Integer, Transaction> carTransactionMap;

	public ParkingSystem(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
		this.carTransactionMap = new HashMap<>();
	}

	public void assignCar(Integer entranceId, Car car, Long start) {
		Transaction transaction = this.carTransactionMap.get(car.getId());
		if (transaction == null) {
			transaction = new Transaction();
		}

		if (transaction.isValidRegister()) {
			ParkingSlot slot = this.parkingLot.nearestAvailableSlot(entranceId, car);
			if (slot == null) {
				return;
			}
			slot.assignCar(car);
			car.parkCar(slot);
			transaction.register(car, start);
			this.carTransactionMap.put(car.getId(), transaction);
		}
	}

	public double freeCar(Car car, Long end) {
		Transaction transaction = this.carTransactionMap.get(car.getId());
		if (transaction != null && transaction.isValidDeregister()) {
			transaction.deregister(end);
			car.getSlot().assignCar(null);
			car.parkCar(null);
			return this.getFee(car);
		}
		return 0.0;
	}
	
	public Transaction getTransaction(Car car) {
		return this.carTransactionMap.get(car.getId());
	}

	public double getFee(Car car) {
		Transaction transaction = this.carTransactionMap.get(car.getId());
		if (transaction == null) {
			return 0;
		}
		return transaction.getFee();
	}

	public List<ParkingSlot> getParkingSlots() {
		return this.parkingLot.getParkingSlots();
	}
}
