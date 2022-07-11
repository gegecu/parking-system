package model.pojos;

import java.util.Collections;
import java.util.List;

import model.pojos.comparators.ParkingSlotComparator;

public class ParkingFloor {
	private List<ParkingSlot> slots;

	public ParkingFloor(List<ParkingSlot> slots) {
		this.slots = slots;
	}

	public ParkingSlot nearestAvailableSlot(Integer entranceId, Car car) {
		Collections.sort(this.slots, new ParkingSlotComparator(entranceId));
		for (ParkingSlot slot : this.slots) {
			if (!slot.isOccupied() && car.getSize().getValue() <= slot.getSize().getValue()) {
				return slot;
			}
		}
		return null;
	}
	
	public List<ParkingSlot> slots() {
		return this.slots;
	}
}
