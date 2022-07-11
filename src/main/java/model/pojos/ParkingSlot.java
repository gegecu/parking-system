package model.pojos;

import java.util.Map;

public class ParkingSlot {
	public enum Size {
		SMALL(1), MEDIUM(2), LARGE(3);

		private int value;

		private Size(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private Map<Integer, Integer> distancesPerEntrance;
	private Car car;
	private Size size;
	private int slotId;
	private double price;

	public ParkingSlot(int slotId, Map<Integer, Integer> distancesPerEntrance, int size) {
		this.distancesPerEntrance = distancesPerEntrance;
		this.car = null;
		this.slotId = slotId;
		this.size = Size.values()[size];
		this.price = 20.00 + ((this.size.getValue()-1) * 40.00);
	}

	public Integer getDistance(Integer entranceId) {
		return this.distancesPerEntrance.get(entranceId);
	}

	public Size getSize() {
		return this.size;
	}

	public int getSlotId() {
		return slotId;
	}

	public boolean isOccupied() {
		return this.car != null;
	}

	public void assignCar(Car car) {
		this.car = car;
	}
	
	public Car getCar() {
		return this.car;
	}
	
	public double getPrice() {
		return this.price;
	}
}
