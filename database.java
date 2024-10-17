import java.sql.*;
import java.io.*;  
import java.util.Scanner;
class G9ProjectPart2FrontEnd
{
	public static void main (String args [])
			throws SQLException
	{
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		while (!exit) {
			// Get user input for type of operation to perform
			System.out.print("What would you like to do?\n" +
					"1. Add to database.\n" +
					"2. Query for info.\n" +
					"3. Exit\n" +
					"Enter a number: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume the newline character

			switch (choice) {
			case 1:
				// Add to database
				System.out.print("What kind of record would you like to add?\n" +
						"1. PATIENT\n" +
						"2. DEPARTMENT\n" +
						"3. PROCEDURE\n" +
						"4. DOCTOR\n" +
						"5. MEDICATION\n" +
						"6. INTERACTION RECORD\n" +
						"7. Procedures done\n" +
						"8. Prescriptions\n" +
						"9. Back to maing menu\n" +
						"Enter a number: ");
				int addChoice = scanner.nextInt();
				scanner.nextLine(); // consume the newline character
				//
				//			// Get user input for the record details
				String sql = "";
				switch (addChoice) {
				case 1:
					addPatient();
					break;
				case 2:
					addDepartment();
					break;
				case 3:
					addProcedure();
					break;
				case 4:
					addDoctor();
					break;
				case 5:
					addMedication();
					break;
				case 6:
					addInteraction();
					break;
				case 7:
					addProceduresdone();
					break;
				case 8:
					addPrescriptions();
					break;
				case 9:
					break;

				default:
					System.out.println("Invalid choice");
					continue;
				}
				if (!sql.isEmpty()) {
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(sql);
					System.out.println("Record added.");
				}
				break;

			case 2:
				System.out.println("What type of information would you like to retrieve?");
				System.out.println("1. Patient Health Record");
				System.out.println("2. Procedures offered by chosen department");
				System.out.println("3. Procedures done by chosen doctor");
				int queryChoice = scanner.nextInt();
				scanner.nextLine(); // Consume newline character

				switch (queryChoice) {
				case 1:
					System.out.println("Please enter patient ID (case sensitive):");
					String queryPatientID = scanner.nextLine();
					generateHealthRecord(queryPatientID);
					break;
				case 2:
					System.out.println("Please enter Department Code (case sensitive): ");
					String queryDeptCode = scanner.nextLine();
					generateDeptProcedures(queryDeptCode);
					break;
				case 3:
					System.out.println("Please enter doctor ID (case sensitive): ");
					String queryDoctorID = scanner.nextLine();
					generateProceduresDone(queryDoctorID);
					break;
				default:
					System.out.println("Invalid choice");
					break;
				}
				break;
			case 3:
				System.out.println("Program done.");
				System.exit(0);
				
			default:
				System.out.println("Invalid choice");
				break;

			}
		}

		scanner.close();
	}
	//////////////////////// add patient//////////////////////////////////
	static void addPatient() throws SQLException {
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO PATIENT(PATIENT_ID, FIRST_NAME, MIDDLE_INITIAL, LAST_NAME,SSN,CURRENT_ADDRESS,"
				+ " CURRENT_PHONE, PERMANENT_ADDRESS, PERMANENT_PHONE, BIRTHDATE,SEX,CONDITION,PRIMARY_DOCTOR_ID,SECONDARY_DOCTOR_ID) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?,?, ?,?,?,?,?,?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter patient's ID: ");
			String patientID = getString();
			System.out.println("\nEnter patient's first name: ");
			String patientFname = getString();
			System.out.println("\nEnter patient's middle initial: ");
			String patientMi = getString();
			System.out.println("\nEnter patient's last name: ");
			String patientLname = getString();
			System.out.println("\nEnter patient's SSN: ");
			String patientSsn = getString();
			System.out.println("\nEnter patient's current address: ");
			String currAdd = getString();
			System.out.println("\nEnter patient's current phone #: ");
			String currPhone = getString();
			System.out.println("\nEnter patient's permanent address: ");
			String permAdd = getString();
			System.out.println("\nEnter permanent phone #: ");
			String permPhone = getString();
			System.out.println("\nEnter birthdate (yyyy-mm-dd): ");
			String birthdate = getString(); 
			System.out.println("\nEnter patient's sex: ");
			String sex = getString();
			System.out.println("\nEnter patient's condition: ");
			String condition = getString();
			System.out.println("\nEnter patient's primary doctor ID: ");
			String primaryDoc = getString();
			System.out.println("\nEnter patient's secondary doctor ID: ");
			String secondDoc = getString();

			pstmt.setString(1, patientID);
			pstmt.setString(2, patientFname);
			pstmt.setString(3, patientMi);
			pstmt.setString(4, patientLname);
			pstmt.setString(5,patientSsn);
			pstmt.setString(6, currAdd);
			pstmt.setString(7, currPhone);
			pstmt.setString(8, permAdd);      
			pstmt.setString(9, permPhone);
			pstmt.setString(10, birthdate);
			pstmt.setString(11, sex);
			pstmt.setString(12, condition);
			pstmt.setString(13, primaryDoc);
			pstmt.setString(14, secondDoc);

			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();
	}
	////////////////////add patient done////////////////////////////////////////////////////////


	/////////////// add department/////////////////////////////////////////////////////
	static void addDepartment() throws SQLException {
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO DEPARTMENT(DEPARTMENT_CODE, DEPARTMENT_NAME, OFFICE_NUMBER,OFFICE_PHONE,DEPARTMENT_HEAD_ID)"
				+
				"VALUES (?, ?, ?, ?,?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter department code: ");
			String patientSsn = getString();
			System.out.println("\nEnter department name: ");
			String patientFname = getString();
			System.out.println("\nEnter office number: ");
			String patientMi = getString();
			System.out.println("\nEnter office phone: ");
			String patientLname = getString();
			System.out.println("\nEnter department head ID: ");
			String departmentHead = getString();


			pstmt.setString(1, patientSsn);
			pstmt.setString(2, patientFname);
			pstmt.setString(3, patientMi);
			pstmt.setString(4, patientLname);
			pstmt.setString(5, departmentHead);


			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();

	}
	////////////////////////// add doctor/////////////////////////////////////////////////
	static void addDoctor() throws SQLException {

		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO DOCTORS(DOCTOR_ID, FIRST_NAME, MIDDLE_INITIAL,LAST_NAME, SSN,ADDRESS,PHONE,BIRTHDATE,CONTACT_NUMBER,DEPARTMENT_CODE)"
				+
				"VALUES (?, ?, ?, ?,?,?,?,?,?,?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter doctor's ID: ");
			String drID = getString();
			System.out.println("\nEnter doctor's first name: ");
			String drfName = getString();
			System.out.println("\nEnter doctor's middle intial: ");
			String drMi = getString();
			System.out.println("\nEnter doctors' last name: ");
			String drLname = getString();
			System.out.println("\nEnter doctor birthdate (yyyy-mm-dd): ");
			String birthdate = getString();
			System.out.println("\nEnter doctor's SSN: ");
			String Ssn = getString();
			System.out.println("\nEnter address: ");
			String address = getString();
			System.out.println("\nEnter phone #: ");
			String phoneNum = getString();
			System.out.println("\nEnter contact number: ");
			String contactNum = getString();
			System.out.println("\nEnter department code: ");
			String departCode = getString();

			pstmt.setString(1, drID);
			pstmt.setString(2, drfName);
			pstmt.setString(3, drMi);
			pstmt.setString(4,drLname);
			pstmt.setString(5, Ssn);
			pstmt.setString(6, address);
			pstmt.setString(7, phoneNum);
			pstmt.setString(8, birthdate);
			pstmt.setString(9, contactNum);
			pstmt.setString(10,departCode);

			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();
	}
	//////////////////////////add doctor done/////////////////////////////////////

	///////////////////////// add procedure///////////////////////////////////////
	static void addProcedure() throws SQLException{
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO PROCEDURES(PROCEDURE_NUMBER, PROCEDURE_NAME, DESCRIPTION,DURATION,OFFERING_DEPARTMENT_CODE)"
				+
				"VALUES (?, ?, ?, ?,?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter procedure number: ");
			String procedureNum = getString();
			System.out.println("\nEnter procedure name: ");
			String procedureName = getString();
			System.out.println("\nEnter description:  ");
			String description = getString();
			System.out.println("\nEnter duration: ");
			String duration = getString();
			System.out.println("\nEnter offering department code: ");
			String offering = getString();

			pstmt.setString(1, procedureNum);
			pstmt.setString(2, procedureName);
			pstmt.setString(3, description);
			pstmt.setString(4, duration);
			pstmt.setString(5, offering);

			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();
		///////////////////// add procedure done ////////////////////////////	 

		///////////////////// add medication ////////////////////////////	 
	}
	static void addMedication() throws SQLException {
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO MEDICATION(MEDICATION_NAME, MANUFACTURER,DESCRIPTION)"
				+
				"VALUES (?, ?, ?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter medication name: ");
			String procedureNum = getString();
			System.out.println("\nEnter manufacturer: ");
			String patientName = getString();
			System.out.println("\nEnter description:  ");
			String description = getString();

			pstmt.setString(1, procedureNum);
			pstmt.setString(2, patientName);
			pstmt.setString(3, description);


			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();
		//////////////// add medication done //////////////////////	 

		//////////////// add interaction //////////////////////	 
	}
	static void addInteraction() throws SQLException {
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO INTERACTION_RECORD (INTERACTION_ID,PATIENT_ID,INT_DATE,INT_TIME,INT_DESCRIPTION)"
				+
				"VALUES (?, ?, ?, ?,?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter interaction id name: ");
			String procedureNum = getString();
			System.out.println("\nEnter patient ID: ");
			String patientName = getString();
			System.out.println("\nEnter interaction (yyyy-mm-dd): ");
			String birthdate = getString();     
			System.out.println("\nEnter  interaction time: ");
			String patientSsn = getString();
			System.out.println("\nEnter description: ");
			String drSsn = getString();

			pstmt.setString(1, procedureNum);
			pstmt.setString(2, patientName);
			pstmt.setString(3, birthdate);
			pstmt.setString(4, patientSsn);
			pstmt.setString(5, drSsn);

			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();
		///////////////// add interaction done /////////////////////////	 

		///////////////// add procedures_done /////////////////////////	 
	}
	static void addProceduresdone() throws SQLException {
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO PROCEDURES_DONE (PROCEDURE_NUMBER,PATIENT_ID,DOCTOR_ID,PD_DATE,PD_TIME,NOTES)"
				+
				"VALUES (?, ?, ?, ?,?,?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter procedure #: ");
			String procedureNum = getString();
			System.out.println("\nEnter patient ID: ");
			String patientName = getString();
			System.out.println("\nEnter  Doctor ID: ");
			String patientSsn = getString();
			System.out.println("\nEnter interaction (yyyy-mm-dd): ");
			String birthdate = getString(); 
			System.out.println("\nEnter time: ");
			String drSsn = getString();
			System.out.println("\nEnter notes: ");
			String notes = getString();

			pstmt.setString(1, procedureNum);
			pstmt.setString(2, patientName);
			pstmt.setString(3, patientSsn);
			pstmt.setString(4, birthdate);
			pstmt.setString(5, drSsn);
			pstmt.setString(6,notes);

			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();
	}
	///////////////// add procedures_done done /////////////////////////

	///////////////// add prescriptions /////////////////////////	 
	static void addPrescriptions() throws SQLException {
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String user = "G9";
		String password = "G9Spring2023";
		Connection conn = DriverManager.getConnection(url, user, password);

		PreparedStatement pstmt = conn.prepareStatement ("INSERT INTO PRESCRIPTIONS(MEDICATION_NAME,PATIENT_ID,DOCTOR_ID,DATE_PRESCRIBED)"
				+
				"VALUES (?, ?, ?, ?)");
		int done = 1;

		while (done != 0) {

			System.out.println("\nEnter medication name: ");
			String procedureNum = getString();
			System.out.println("\nEnter patient ID: ");
			String patientName = getString();
			System.out.println("\nEnter  Doctor ID: ");
			String patientSsn = getString();
			System.out.println("\nEnter date prescribed (yyyy-mm-dd): ");
			String birthdate = getString();   

			pstmt.setString(1, procedureNum);
			pstmt.setString(2, patientName);
			pstmt.setString(3, patientSsn);
			pstmt.setString(4, birthdate);

			int NumRows = pstmt.executeUpdate();
			System.out.println("\n" + NumRows + " row(s) inserted");

			System.out.println("\nHit 0 for exit, " +
					"or enter any other number for another insert: ");
			done = getInt();
		} // while done
		conn.close();
	}
	public static void generateHealthRecord(String patientID) throws SQLException {
		String dbURL = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String username = "G9";
		String password = "G9Spring2023";
		String query = "select PATIENT.first_name, PATIENT.middle_initial, PATIENT.last_name, " +
				"PATIENT.current_address, PATIENT.current_phone, PATIENT.primary_doctor_id, " +
				"PROCEDURES_DONE.*, " +
				"INTERACTION_RECORD.*, " +
				"PRESCRIPTIONS.* " +
				"from PATIENT " +
				"join PROCEDURES_DONE on PATIENT.patient_id = PROCEDURES_DONE.patient_id " +
				"join INTERACTION_RECORD on PATIENT.patient_id = INTERACTION_RECORD.patient_id " +
				"join PRESCRIPTIONS on PATIENT.patient_id = PRESCRIPTIONS.patient_id " +
				"where PATIENT.patient_id = '" + patientID + "'";
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		Statement stmt = conn.createStatement(); {
			try (ResultSet rs = stmt.executeQuery(query)) {
				if (rs.next()) {
					// Get all columns from PATIENT table
					String firstName = rs.getString("first_name");
					String middleInitial = rs.getString("middle_initial");
					String lastName = rs.getString("last_name");
					String currentAddress = rs.getString("current_address");
					String currentPhone = rs.getString("current_phone");
					String primaryDoctorID = rs.getString("primary_doctor_id");

					// Get all columns from PROCEDURES_DONE table
					String procedureNum = rs.getString("procedure_number");
					String procedureDate = rs.getString("pd_date");
					String procedureTime = rs.getString("pd_time");
					String procedureNotes = rs.getString("notes");

					// Get all columns from INTERACTION_RECORD table
					int interactionID = rs.getInt("interaction_id");
					String interactionDate = rs.getString("int_date");
					String interactionDesc = rs.getString("int_description");

					// Get all columns from PRESCRIPTIONS table
					String prescriptionName = rs.getString("medication_name");
					String prescriptionDate = rs.getString("date_prescribed");

					// Print information
					if (middleInitial != null) {
						System.out.println("Patient Health Record: \nPatient Name: " + firstName + " " + middleInitial + " " + lastName);
					} else {
						System.out.println("Patient Health Record: \nPatient Name: " + firstName + " " + lastName);
					}
					System.out.println("Current Address: " + currentAddress);
					System.out.println("Current Phone: " + currentPhone);
					System.out.println("Primary Doctor ID: " + primaryDoctorID);
					System.out.println("Procedures this patient has had: " + procedureNum + " on " + procedureDate +
							" at " + procedureTime + " for " + procedureNotes);
					System.out.println("Interacted with hospital " + interactionID + " times on these dates: " + 
							interactionDate + " for these reasons: " + interactionDesc);
					System.out.println("Medications prescribed: " + prescriptionName + " at " + prescriptionDate);
				} else {
					System.out.println("No health record found for patient ID: " + patientID);
				}
			}
		}
	}

	public static void generateDeptProcedures(String deptCode) throws SQLException {
		String dbURL = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String username = "G9";
		String password = "G9Spring2023";
		String query = 
				"SELECT PROCEDURE_NUMBER, PROCEDURE_NAME " +
						"FROM PROCEDURES " + 
						"WHERE OFFERING_DEPARTMENT_CODE = '" + deptCode + "'";

		Connection conn = DriverManager.getConnection(dbURL, username, password);

		System.out.println("Procedures offered by Department: ");
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			boolean hasData = false;
			while (rs.next()) {
				hasData = true;
				String procedureNumber = rs.getString("procedure_number");
				String procedureName = rs.getString("procedure_name");
				System.out.println("Procedure " + procedureNumber + ": " + procedureName);
			}
			if (!hasData) {
				System.out.println("This Department offers no procedures at this time: " + deptCode);
			}
		}
	}



	public static void generateProceduresDone(String doctorID) throws SQLException {
		String dbURL = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String username = "G9";
		String password = "G9Spring2023";
		String query = "select pd.procedure_number, p.procedure_name "
				+ "from PROCEDURES_DONE pd "
				+ "join PROCEDURES p ON pd.procedure_number = p.procedure_number "
				+ "where pd.doctor_id = '" + doctorID + "'";
		Connection conn = DriverManager.getConnection(dbURL, username, password);
		System.out.println("Procedures by this doctor: ");
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			boolean hasData = false;
			while (rs.next()) {
				hasData = true;
				String procedureNumber = rs.getString("procedure_number");
				String procedureName = rs.getString("procedure_name");
				System.out.println("Procedure " + procedureNumber + ": " + procedureName);
			}
			if (!hasData) {
				System.out.println("No procedures found for doctor ID: " + doctorID);
			}
		}
	}
	public static String getString() {
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String s = br.readLine();
			return s;
		} // try
		catch (IOException e) {return "";}
	} //getString
	public static int getInt() {
		String s= getString();
		return Integer.parseInt(s);
	}
}