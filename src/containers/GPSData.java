package containers;

public class GPSData {
	private long timestamp;
	private double acc;

	public GPSData(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getAcc() {
		return acc;
	}

	public void setAcc(double acc) {
		this.acc = acc;
	}

	@Override
	public String toString() {
		return timestamp + " " + acc;
	}
}
