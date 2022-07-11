package model.builders;

import java.util.Map;

import model.pojos.ParkingSlot;
import model.util.RandomUtil;

public class ParkingSlotBuilder {
	
	private RandomUtil random;

	public ParkingSlotBuilder(RandomUtil random) {
		this.random = random;
	}

	public ParkingSlot createParkingSlot(int slotId, Map<Integer, Integer> distancesPerEntrance) {
		int size = this.random.generateRandom(ParkingSlot.Size.values().length);
		return new ParkingSlot(slotId, distancesPerEntrance, size);
	}
}
