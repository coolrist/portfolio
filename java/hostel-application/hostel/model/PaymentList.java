package hostel.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Collection class to hold a list of Payment objects
 * @author John Rist
 * @version 14th February 2022
 */
public class PaymentList implements Serializable {
	
	// attributes
	private List<Payment> pList = new ArrayList<>();
	public final int MAX;
	
	private static final long serialVersionUID = -8872924776374895040L;
	
	/** Constructor initialises the empty payment list and sets the maximum list size
	 * @param maxIn: The maximum number of payments in the list
	 */
	public PaymentList(int maxIn) {
		if (maxIn < 1)
			throw new HostelException("invalid list size set " + maxIn);
		else
			MAX = maxIn;
	}
	
	/** Adds a new payment to the end of the list
	 * @param paymentIn: The payment to add
	 * @return Returns true if the object was added successfully and false otherwise
	 */
	public boolean addPayment(Payment paymentIn) {
		if (!isFull()) {
			pList.add(paymentIn);
			return true;
		}
		return false;
	}
	
	/** Remove a payment of the list
	 * @param positionIn: The logical position of the payment to remove
	 * @throws HostelException: Not valid position
	 * @return Returns true if the payment was removed successfully and false otherwise
	 */
	public boolean removePayment(int positionIn) {
		if (!isEmpty()) {
			Optional<Payment> payment = getPayment(positionIn);
			payment.ifPresentOrElse(
					value -> pList.remove(value), 
					()-> {throw new HostelException("invalid operation");}
					);
			return true;
		}
		return false;
	}
	
	/** Reads the payment at the given position in the list
	 * @param indexIn: The logical position of the payment in the list
	 * @return Returns the payment at the given logical position in the list
	 * 			or null if no payment at that logical position
	 */
	public Optional<Payment> getPayment(int indexIn) {
		if (indexIn > 0 && indexIn <= pList.size())
			return Optional.of(pList.get(indexIn-1));
		return Optional.empty();
	}
	
	/** Calculates the total payments made by the tenant
	 * @return Returns the total value of payments recorded
	 */
	public double calculateTotalPaid() {
		double sum = 0;
		
		for (Payment payment : pList)
			sum += payment.getAmount();
		return sum;
	}
	
	/** Checks if the payment list is empty
	 * @return Returns true if the list is empty and false otherwise
	 */
	public boolean isEmpty() {
		return pList.size() == 0;
	}
	
	/** Checks if the payment list is full
	 * @return Returns true if the list is full and false otherwise
	 */
	public boolean isFull() {
		return pList.size() == MAX;
	}
	
	/** Gets the total number of payments
	 * @return Returns the total number of payments currently in the list
	 */
	public int getTotal() {
		return pList.size();
	}
	
	/** Gets all payments
     * @return Returns all payments
     */
	public List<Payment> getAllPayments() {
	    return pList;
	}
	
	@Override
	public String toString() {
		return pList.toString();
	}
}
