package model;

import model.pojos.ParkingSlot;
import model.util.*;

public class Fee {
	private final double BASE_FEE = 40.00;
	private final double DAY_FEE = 5000.00;
	private final long HOUR_IN_MILLISECONDS = 3600;
	private final int BASE_HOUR = 3;
	private final int DAY_HOUR = 24;

	private ParkingSlot slot;
	private double fee;
	private long cummulativeTime;
	private long start;
	private long end;
	private long excessTime;
	private boolean continousTime;

	public Fee() {
		this.reset();
	}
	
	private void reset() {
		fee = this.BASE_FEE;
		cummulativeTime = 0;
		start = 0;
		end = 0;
		slot = null;
		continousTime = false;
	}
	
	public boolean isNewFee(Long start) {
		return start - this.start > this.HOUR_IN_MILLISECONDS;
	}

	public void registerTransaction(ParkingSlot slot, Long start) {
		if (this.start != 0) {
			if (start - this.start > this.HOUR_IN_MILLISECONDS) {
				this.reset();
				this.start = start;
			} else {
				if (this.continousTime) {
					if (this.slot.getSize() != slot.getSize()) {
						long excessTime = start - this.start;
						this.cummulativeTime += excessTime;
						this.fee -= this.slot.getPrice()
								* ((double) (this.HOUR_IN_MILLISECONDS - (this.excessTime)) / this.HOUR_IN_MILLISECONDS);
						this.fee += this.slot.getPrice() * ((double) excessTime / this.HOUR_IN_MILLISECONDS);
						this.start = start;
					}
					else {
						this.fee -= this.slot.getPrice() * Calculator.computeCeilingTime(this.excessTime, this.HOUR_IN_MILLISECONDS);
						this.start = this.start - this.excessTime;
						this.cummulativeTime -= this.excessTime;
					}
				}
			}
			this.slot = slot;
			return;
		}
		this.slot = slot;
		this.start = start;
	}

	public void unregisterTransaction(Long end) {
		this.end = end;
		this.cummulativeTime += this.end - this.start;
		if (!this.continousTime && (double) this.cummulativeTime / this.HOUR_IN_MILLISECONDS >= this.BASE_HOUR) {
			this.excessTime = this.cummulativeTime - (this.HOUR_IN_MILLISECONDS * this.BASE_HOUR);
			this.continousTime = true;
			this.fee += this.slot.getPrice() * Calculator.computeCeilingTime(excessTime, this.HOUR_IN_MILLISECONDS);
		} else if (this.continousTime) { // recheck
			this.excessTime = this.HOUR_IN_MILLISECONDS - ((this.end - this.start) % this.HOUR_IN_MILLISECONDS);
			this.fee += this.slot.getPrice()
					* Calculator.computeCeilingTime(this.end - this.start, this.HOUR_IN_MILLISECONDS);
		}
		if ((double) this.cummulativeTime / this.HOUR_IN_MILLISECONDS >= this.DAY_HOUR) {
			this.fee = DAY_FEE * (int)(this.cummulativeTime / (this.HOUR_IN_MILLISECONDS * this.DAY_HOUR));
			this.excessTime = (this.cummulativeTime % (this.HOUR_IN_MILLISECONDS * this.DAY_HOUR));
			this.fee += this.slot.getPrice() * Calculator.computeCeilingTime(excessTime, this.HOUR_IN_MILLISECONDS);
			this.cummulativeTime = this.excessTime;
		}
		
		this.start = end;
	}
	
	public Double getFee() {
		return this.fee;
	}
}
