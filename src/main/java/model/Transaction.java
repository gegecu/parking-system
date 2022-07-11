package model;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Triplet;

import model.pojos.Car;
import model.pojos.ParkingSlot;

public class Transaction {
	List<Triplet<Long, Long, ParkingSlot>> parkedTimeList;
	Fee fee;

	public Transaction() {
		reset();
	}

	private void reset() {
		this.parkedTimeList = new ArrayList<>();
		this.fee = new Fee();
	}

	public boolean isValidRegister() {
		return this.parkedTimeList.isEmpty() || (!this.parkedTimeList.isEmpty()
				&& this.parkedTimeList.get(this.parkedTimeList.size() - 1).getValue1() != null);
	}

	public boolean isValidDeregister() {
		return !this.parkedTimeList.isEmpty()
				&& this.parkedTimeList.get(this.parkedTimeList.size() - 1).getValue1() == null;
	}

	public void register(Car car, long timeIn) {
		if (this.fee.isNewFee(timeIn)) {
			reset();
		}
		Triplet<Long, Long, ParkingSlot> time = Triplet.with(timeIn, null, car.getSlot());
		this.parkedTimeList.add(time);
		this.fee.registerTransaction(car.getSlot(), timeIn);
	}

	public void deregister(long timeout) {
		this.parkedTimeList.set(parkedTimeList.size() - 1,
				this.parkedTimeList.get(parkedTimeList.size() - 1).setAt1(timeout));
		this.fee.unregisterTransaction(timeout);
	}

	public List<Triplet<Long, Long, ParkingSlot>> getParkedTimes() {
		return this.parkedTimeList;
	}

	public double getFee() {
		if (this.parkedTimeList.get(this.parkedTimeList.size()-1).getValue1() != null) {
			return this.fee.getFee();
		}
		return 0;
	}
}
