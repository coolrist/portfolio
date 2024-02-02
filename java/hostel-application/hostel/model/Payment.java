package hostel.model;

import java.io.Serializable;

/** Class used to store details of a single payment in a hostel
 * @author John Rist
 * @version 10th February 2023
 */
public class Payment implements Serializable {

	private String month;
	private double amount;
	
	private static final long serialVersionUID = 1208246538242054343L;
	
	/** Constructor initialises the payment month and the amount paid
	 * @param monthIn: month of payment
	 * @param amountIn: amount of payment
	 * @throws HostelException: amount less than 0
	 */
	public Payment(String monthIn, double amountIn) {
		month = monthIn;
		if (amountIn > 0)
			amount = amountIn;
		else throw new HostelException("invalid amount set " + amountIn);
	}
	
	/** Reads the month for which payment was made
	 * @return Returns the month for which payment was made
	 */
	public String getMonth() {
		return month;
	}
	
	/** Reads the amount paid
	 * @return Returns the amount paid
	 */
	public double getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return "(" + getMonth() + ": " + getAmount() + ")"; // a convenient way of displaying attributes
	}
	
}
