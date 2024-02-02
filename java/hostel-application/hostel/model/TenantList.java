package hostel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Collection class to hold a list of tenants
 * 
 * @author John Rist
 * @version 15th February 2022
 */
public class TenantList {

	private List<Tenant> tList;
	public final int MAX;

	/**
	 * Constructor initialises the empty tenant list and sets the maximum list size
	 * 
	 * @param maxIn: The maximum number of tenants in the list
	 * @throws HostelException: Maximum number less than 1
	 */
	public TenantList(int maxIn) {
		tList = new ArrayList<>();
		if (maxIn < 1)
			throw new HostelException("invalid list size set " + maxIn);
		else
			MAX = maxIn;
	}

	public TenantList(int maxIn, List<Tenant> listIn) {
		tList = listIn;
		if (maxIn < 1)
			throw new HostelException("invalid list size set " + maxIn);
		else
			MAX = maxIn > listIn.size() ? maxIn : listIn.size();
	}

	/**
	 * Adds a new Tenant to the list
	 * 
	 * @param tenantIn: The Tenant to add
	 * @return Returns true if the tenant was added successfully and false otherwise
	 */
	public boolean addTenant(Tenant tenantIn) {
		if (!isFull()) {
			Tenant oldTenant = search(tenantIn.getRoom()).orElse(null);

			if (oldTenant == null) {
				tList.add(tenantIn);
				return true;
			}
			return false;
		}
		throw new HostelException("There are only " + MAX + " rooms");
	}

	/**
	 * Removes the tenant in the given room number
	 * 
	 * @param roomIn: The room number to of the tenant to remove
	 * @throws HostelException: Not valid room number
	 * @return Returns true if the tenant was removed successfully and false
	 *         otherwise
	 */
	public boolean removeTenant(int roomIn) {
		if (!isEmpty()) {
			search(roomIn).ifPresentOrElse(value -> tList.remove(value), () -> {
				throw new HostelException("Room number " + roomIn + " is empty");
			});
			return true;
		}
		return false;
	}

	/**
	 * Reads the tenant at the given position in the list
	 * 
	 * @param posIn: The logical position of the tenant in the list
	 * @return Return the tenant at the given logical position in the list or null
	 *         if no tenant at that logical position
	 *//*
		 * public Optional<Tenant> getTenant(int posIn) { if (posIn > 0 && posIn <=
		 * tList.size()) return Optional.of(tList.get(posIn - 1)); return
		 * Optional.empty(); }
		 */

	/**
	 * Searches for the tenant in the given room number
	 * 
	 * @param roomIn: The room number to search for
	 * @return Return the tenant in the given room number or null if no tenant in
	 *         the given room number
	 */
	public Optional<Tenant> search(int roomIn) {
		for (Tenant currentTenant : tList)
			if (currentTenant.getRoom() == roomIn)
				return Optional.of(currentTenant);
		return Optional.empty();
	}

	/**
	 * Reports on whether or not the list is empty
	 * 
	 * @return Returns true if the list is empty and false otherwise
	 */
	public boolean isEmpty() {
		return tList.size() == 0;
	}

	/**
	 * Reports on whether or not the list is full
	 * 
	 * @return Returns true if the list is full and false otherwise
	 */
	public boolean isFull() {
		return tList.size() == MAX;
	}

	/**
	 * Gets the total number of tenants
	 * 
	 * @return Return the total number of tenants currently in the list
	 */
	public int getTotal() {
		return tList.size();
	}

	/**
	 * Gets all tenants
	 * 
	 * @return Return all tenants
	 */
	public List<Tenant> getAllTenants() {
		return tList;
	}

	@Override
	public String toString() {
		return tList.toString();
	}

}
