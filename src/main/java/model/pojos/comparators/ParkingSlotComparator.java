package model.pojos.comparators;

import java.util.Comparator;

import model.pojos.ParkingSlot;

public class ParkingSlotComparator implements Comparator<ParkingSlot> {
	private Integer entranceId;

	public ParkingSlotComparator(Integer entranceId) {
		this.entranceId = entranceId;
	}

	@Override
	public int compare(ParkingSlot o1, ParkingSlot o2) {
		if (o1.getDistance(this.entranceId) < o2.getDistance(this.entranceId))
			return -1;
		else if (o1.getDistance(this.entranceId) > o2.getDistance(this.entranceId))
			return 1;
		else
			return 0;
	}
}
