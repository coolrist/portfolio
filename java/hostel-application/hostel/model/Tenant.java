package hostel.model;

import java.io.Serializable;

/** Class used to record the details of a tenant
 * @author John Rist
 * @version 15th February 2022
 */
public class Tenant implements Serializable {

	private String name;
	private int room;
	private PaymentList payments;
	
	private static final long serialVersionUID = 8406453391334558425L;
	public static final int MAX = 12;
	
	/** Constructor initialises the name and room number of the tenant
	 * and sets the payments made to the empty list
	 * @param nameIn: name of tenant
	 * @param roomIn: room number of tenant
	 */
	public Tenant(String nameIn, int roomIn) {
		if (roomIn < 0)
			throw new HostelException(roomIn + " must be greather than 0");
		
		name = nameIn;
		room = roomIn;
		payments = new PaymentList(MAX);
	}
	
	/** Records a payment for the tenant
	 * @param paymentIn: payment made by tenant
	 */
	public boolean makePayment(Payment paymentIn) {
		return payments.addPayment(paymentIn); // call PaymentList method
	}
	
	/** Reads the name of the tenant
	 * @return Returns the name of the tenant
	 */
	public String getName() {
		return name;
	}
	
	/** Reads the room of the tenant
	 * @return Returns the room of the tenant
	 */
	public int getRoom() {
		return room;
	}
	
	/** Reads the payments of the tenant
	 * @return Returns the payments made by the tenant
	 */
	public PaymentList getPayments() {
		return payments;
	}
	
	@Override
	public String toString() {
		return "("+getName()+", "+getRoom()+", "+getPayments()+")";
	}
	
}
