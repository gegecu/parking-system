package model.pojos;

public class Car {
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

	private Size size;
	private int carId;
	private ParkingSlot slot;

	public Car(int carId, Size size) {
		this.size = size;
		this.carId = carId;
		this.slot = null;
	}

	public Size getSize() {
		return this.size;
	}

	public int getId() {
		return this.carId;
	}

	public void parkCar(ParkingSlot slot) {
		this.slot = slot;
	}

	public ParkingSlot getSlot() {
		return this.slot;
	}
}
