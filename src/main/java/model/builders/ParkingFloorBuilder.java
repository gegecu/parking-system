package model.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.javatuples.Pair;

import model.pojos.ParkingFloor;
import model.pojos.ParkingSlot;

public class ParkingFloorBuilder {
	private ParkingSlotBuilder parkingSlotBuilder;
	private static int slotId = 0;
	
	public ParkingFloorBuilder(ParkingSlotBuilder parkingSlotBuilder) {
		this.parkingSlotBuilder = parkingSlotBuilder;
	}
	
	public ParkingFloor createParkingFloor(Pair<Integer, Integer> dimension, Map<Integer, Pair<Integer, Integer>> entranceLocations) {
		List<ParkingSlot> slots = new ArrayList<ParkingSlot>();
		for (int i = 0; i < dimension.getValue1(); i++) {
			for (int j = 0; j < dimension.getValue0(); j++) {
				Map<Integer, Integer> distancesPerEntrance = new HashMap<>();
				for (Entry<Integer, Pair<Integer, Integer>> entranceLocation : entranceLocations.entrySet()) {
					int distance = Math.abs(j - entranceLocation.getValue().getValue0())
							+ Math.abs(i - entranceLocation.getValue().getValue1());
					distancesPerEntrance.put(entranceLocation.getKey(), distance);
				}
				ParkingSlot slot = this.parkingSlotBuilder.createParkingSlot(slotId++, distancesPerEntrance);
				slots.add(slot);
			}
		}
		return new ParkingFloor(slots);
	}
}
