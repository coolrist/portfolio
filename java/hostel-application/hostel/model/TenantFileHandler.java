package hostel.model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to read and save files
 * 
 * @author John Rist
 * @version 06th february 2023
 */
public class TenantFileHandler {

	/**
	 * Saves a TenantList in a file
	 * 
	 * @param tListIn:      TenantList to save to a file
	 * @param numberOfRoom: Size of TenantList
	 */
	public static void saveRecords(TenantList tListIn) {
		// use try-with-resources to ensure file is closed safely
		try (FileOutputStream tListFile = new FileOutputStream("hostel-file.hst");
				ObjectOutputStream tListWriter = new ObjectOutputStream(tListFile);) {

			// write each element of the list to file
			for (Tenant tenant : tListIn.getAllTenants())
				tListWriter.writeObject(tenant);
			
		} catch (IOException e) { // handle the exception thrown by the FileWriter methods
			System.out.println("\nThere was a problem writing this file");
		}
		
		System.out.println("Tenant list saved successfully");
	}

	/**
	 * Edits a TenantList from a file
	 */
	public static List<Tenant> readRecords() {
		boolean endOfFile = false;
		List<Tenant> tList = new ArrayList<>();

		// use try-with-resources to ensure file is closed safely
		try (FileInputStream tListFile = new FileInputStream("hostel-file.hst");
				ObjectInputStream tListStream = new ObjectInputStream(tListFile);) {

			// read the first line of the file
			Tenant temp = (Tenant) tListStream.readObject();

			/*
			 * read the rest of the first record, then all the rest of records until the end
			 * of the file is reached
			 */
			while (!endOfFile) {
				try {
					tList.add(temp);
					temp = (Tenant) tListStream.readObject();
				} catch (EOFException e) {
					endOfFile = true;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("\nNo file was read");
		} catch (ClassNotFoundException e) {
			System.out.println("\nTrying to read an object of an unknown class");
		} catch (StreamCorruptedException e) {
			System.out.println("\nUnreadable file format");
		} catch (IOException e) {
			System.out.println("There was a problem reading the file");
		}
		
		System.out.println("File loaded successfully");

		return tList;
	}

}
