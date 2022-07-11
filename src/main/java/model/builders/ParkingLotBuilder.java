package model.builders;

import java.util.Map;

import org.javatuples.Pair;

import model.pojos.ParkingFloor;
import model.pojos.ParkingLot;

public class ParkingLotBuilder {
	private ParkingFloorBuilder parkingFloorBuilder;
	private Pair<Integer, Integer> dimension;
	private Map<Integer, Pair<Integer, Integer>> entranceLocations;
	private ParkingLot lot;

	public ParkingLotBuilder(ParkingFloorBuilder parkingFloorBuilder, Pair<Integer, Integer> dimension,
			Map<Integer, Pair<Integer, Integer>> entranceLocations) {
		this.parkingFloorBuilder = parkingFloorBuilder;
		this.dimension = dimension;
		this.entranceLocations = entranceLocations;
		this.lot = new ParkingLot();
	}
	
	public void addFloor() {
		ParkingFloor floor = this.parkingFloorBuilder.createParkingFloor(this.dimension, this.entranceLocations);
		this.lot.addFloor(floor);
	}
	
	public void demolishFloor() {
		this.lot.demolishFloor();
	}
	
	public ParkingLot getLot() {
		return this.lot;
	}

}
