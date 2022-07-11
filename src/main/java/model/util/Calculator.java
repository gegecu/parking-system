package model.util;

public class Calculator {
	public static int computeCeilingTime(long time, long fraction) {
		return (int) Math.ceil((double) (time) / fraction);
	}
}
