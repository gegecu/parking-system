package model.pojos;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
	private List<ParkingFloor> floors;

	public ParkingLot() {
		this.floors = new ArrayList<>();
	}

	public void addFloor(ParkingFloor floor) {
		this.floors.add(floor);
	}

	public void demolishFloor() {
		this.floors.remove(floors.size()-1);
	}

	public ParkingSlot nearestAvailableSlot(Integer entranceId, Car car) {
		ParkingSlot slot = null;
		for (ParkingFloor floor : this.floors) {
			slot = floor.nearestAvailableSlot(entranceId, car);
			if (slot != null)
				return slot;
		}
		return slot;
	}
	
	public List<ParkingSlot> getParkingSlots() {
		List<ParkingSlot> slots = new ArrayList<>();
		for (ParkingFloor floor : this.floors) {
			slots.addAll(floor.slots());
		}
		return slots;
	}
}
