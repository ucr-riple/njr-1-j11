/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.project.hospital;

import hugo.project.hospital.Hospital.Department.Room;
import hugo.project.hospital.Hospital.Reception.PatientInfo;
import hugo.util.structure.BinaryTree;
import hugo.util.structure.EdgeGraph;
import hugo.util.structure.LinkedList;
import hugo.util.structure.PriorityQueue;

enum RoomType {
	WAITINGROOM, WARD
}

public class Hospital {
	protected class Patient implements Comparable<Patient>{
		private final String name;
		private int severity;
		private boolean wantsSingleRoom;
		
		public Patient(String name, int severity, boolean wantsSingleRoom) {
			this.name = name;
			this.severity = severity;
			this.wantsSingleRoom = wantsSingleRoom;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getSeverity() {
			return this.severity;
		}
		
		public boolean wantsSingleRoom() {
			return this.wantsSingleRoom;
		}

		@Override
		public int compareTo(Patient o) {
			return name.compareTo(o.getName());
		}
		
		public String toString() {
			return getName();
		}
	}

	protected class Reception {
		protected class PatientInfo implements Comparable<PatientInfo>{
			protected Patient patient;
			protected Department department;
			protected Room room;
			
			public PatientInfo(Patient p, Department dep, Room room) {
				this.patient = p;
				this.department = dep;
				this.room = room;
			}
			public String getName() {
				return patient.getName();
			}
			public Patient getPatient() {
				return patient;
			}
			
			public Department getDepartment() {
				return department;
			}
			
			public Room getRoom() {
				return room;
			}

			@Override
			public int compareTo(PatientInfo o) {
				return patient.name.compareTo(o.getPatient().getName());
			}

		}
		
		protected BinaryTree<PatientInfo> register;
		
		public Reception(){
			register = new BinaryTree<PatientInfo>();
		}

		public PatientInfo getPatientInfo(String name) {
			for (PatientInfo info : register.BfsTraversalIterable()) {
				if(info.getName().equals(name)) return info;
			}
			return null;
		}
		
		public PatientInfo getPatientInfo(Patient p) {
			return getPatientInfo(p.getName());
		}
		
		public void addPatientInfo(Patient p, Department dep, Room room) {
			register.insert(new PatientInfo(p, dep, room));
		}
		
		public void removePatientInfo(PatientInfo info) {
			register.remove(info);
		}
		
		public void removePatientInfo(String name) {
			register.remove(getPatientInfo(name));
		}
		
		public void removePatientInfo(Patient p) {
			register.remove(getPatientInfo(p));
		}

		public String show() {
			String output = "";
			for (PatientInfo info : register.DfsTraversalIterable()) {
				output += info.getName() + ":\t"
						+ info.getDepartment().getName() + ",\t"
						+ info.getRoom().getName() +"\n";;
			}
			return output;
		}
		
		public String toString() {
			return show();
		}
		
	}

	protected abstract class Department implements Comparable<Department>{
		protected abstract class Room {
			protected RoomType roomType;
			public RoomType getRoomType() {
				return roomType;
			}
			
			public abstract String getName();
			
			public abstract void removePatient(Patient p);
			
			public abstract String toString();
		}

		protected class WaitingRoom extends Room{
			private String name;
			protected PriorityQueue<Patient> waitingList;
			
			public WaitingRoom(String name) {
				this.name = name;
				waitingList = new PriorityQueue<Hospital.Patient>();
				this.roomType = RoomType.WAITINGROOM;
			}
			
			public void addWaitingPatient(Patient p) {
				waitingList.push(p, p.severity);
			}
			
			public Patient popWaitingPatient() {
				return waitingList.pop();
			}
			
			public boolean noOneWaiting() {
				return waitingList.empty();
			}
			
			public int numberOfPeopleWaiting() {
				return waitingList.size();
			}
			
			public String getName() {
				return name;
			}
			
			public String info() {
				String patientListString = "<< ";
				for (Patient patient : waitingList) {
					patientListString += patient.toString() + " << ";
				}
				return "  -> "
						+this.getName()
						+" (" + numberOfPeopleWaiting() + ") "
						+"[ "
						+patientListString
						+" ]";
			}

			@Override
			public void removePatient(Patient p) {
				waitingList.remove(p);
			}
			
			public String toString() {
				return info();
			}
			
		}

		protected class Ward extends Room implements Comparable<Ward>{
			private final int number;
			private final int capacity;
			protected LinkedList<Patient> patients;
			
			public Ward(int number, int capacity) {
				this.number = number;
				this.capacity = capacity;
				patients = new LinkedList<Hospital.Patient>();
				this.roomType = RoomType.WARD;
			}
			
			public boolean isFull() {
				return patients.size() == capacity;
			}
			
			public boolean isSingleRoom() {
				return capacity == 1;
			}
			
			public boolean hasPatient(Patient p) {
				return patients.contains(p) != null;
			}
			
			public void addPatient(Patient p) {
				if(patients.size() < capacity) patients.addFirst(p);
			}
			
			public void removePatient(Patient p) {
				patients.remove(p);
			}

			@Override
			public int compareTo(Ward o) {
				return new Integer(number).compareTo(o.number);
			}
			
			public String info() {
				String patientListString = "";
				for (Patient patient : patients) {
					patientListString += patient.toString() + " ";
				}
				return "  -> "
						+this.getName()
						+" (" + patients.size() +"/" + capacity + ") "
						+"[ "
						+patientListString
						+" ]\n";
			}
			
			public String getName() {
				return "Room " + number;
			}
			
			public String toString() {
				return info();
			}
		}
		
		protected String name;
		protected WaitingRoom waitingRoom;
		protected LinkedList<Ward> wardsForSingle;
		protected LinkedList<Ward> wardsForMulti;
		protected LinkedList<Device> deviceList;
		
		public boolean isForMultiFull() {
			for (Ward room : wardsForMulti) {
				if(!room.isFull()) return false;
			}
			return true;
		}
		public boolean isForSingleFull() {
			for (Ward room : wardsForSingle) {
				if(!room.isFull()) return false;
			}
			return true;
		}
		public boolean isFull() {
			if(isForMultiFull() && isForSingleFull()) return true;
			return false;
		}
		
		public void addPatient(Patient p) {
			if (isFull()) {
				waitingRoom.addWaitingPatient(p);
				reception.addPatientInfo(p, this, waitingRoom);
			} else {
				for (Ward room : (p.wantsSingleRoom()?wardsForSingle:wardsForMulti)) {
					if(!room.isFull()) {
						room.addPatient(p);
						reception.addPatientInfo(p, this, room);
						return;
					}
				}
			}
		}
		
		protected void addPatientFromWaitingRoom() {
			// return if waiting room is empty
			if(waitingRoom.noOneWaiting()) return;
			// Iterate the waiting list until find a person with the ward desired available
			for (Patient patient : waitingRoom.waitingList) {
				// If this patient only want ward for single
				if (patient.wantsSingleRoom && !isForSingleFull()) {
					for (Ward singleWard : wardsForSingle) {
						if (!singleWard.isFull()) {
							singleWard.addPatient(patient);
							reception.addPatientInfo(patient, this, singleWard);
							waitingRoom.waitingList.remove(patient);
							return;
						}
					}
				}
				// If this patient don't care
				if (!patient.wantsSingleRoom && !isFull()) {
					// First find a ward for multiple patients
					for (Ward multiWard : wardsForMulti) {
						if (!multiWard.isFull()) {
							multiWard.addPatient(patient);
							reception.addPatientInfo(patient, this, multiWard);
							waitingRoom.waitingList.remove(patient);
							return;
						}
					}
					// If none available, find a ward for single
					for (Ward singleWard : wardsForSingle) {
						if (!singleWard.isFull()) {
							singleWard.addPatient(patient);
							reception.addPatientInfo(patient, this, singleWard);
							waitingRoom.waitingList.remove(patient);
							return;
						}
					}
				}
			}
		}

		
		public void signOutPatient(PatientInfo info) {
			Room room = info.getRoom();
			room.removePatient(info.getPatient());
			if(!waitingRoom.noOneWaiting()) addPatientFromWaitingRoom();
		}
		
		public void signOutPatient(Patient p) {
			signOutPatient(reception.getPatientInfo(p));
		}
		
		public Device hasDevice(String type) {
			Device targetDevice = deviceFactory(type, 0);
			for (Device device : deviceList) {
				if(device.equals(targetDevice) && device.getAmount()>0) return device;
			}
			return null;
		}
		
		public void addDevice(String type, int addAmount) {
			Device newDevice = hasDevice(type);
			if(newDevice != null) newDevice.addAmount(addAmount);
			else deviceList.addFirst(deviceFactory(type, addAmount));
		}
		
		public void removeDevice(String type) {
			Device targetDevice = hasDevice(type);
			if(targetDevice != null) deviceList.remove(targetDevice);
		}
		
		public void removeDevice(String type, int amount) {
			Device targetDevice = hasDevice(type);
			if(targetDevice != null) targetDevice.reduceAmount(amount);
		}
		

		public int compareTo(Department dep) {
			return this.name.compareTo(dep.name);
		}
		
		public String toString() {
			return info();		
		}
		
		public String getName() {
			return name;
		}

		public String info() {
			String roomInfoListString = "";
			String deviceInfoString = "  Device: [ ";
			for (Ward room : wardsForSingle) {
				roomInfoListString += room.toString();
			}
			for (Ward room : wardsForMulti) {
				roomInfoListString += room.toString();
			}
			for (Device device : deviceList) {
				deviceInfoString += device.getType() + "(" + device.getAmount() + ") ";
			}
			deviceInfoString += "]";
			return this.getName() +":\n" 
			+ deviceInfoString + "\n"
			+ waitingRoom.toString() + "\n"
			+ roomInfoListString + "\n";
		}
	}
	
	protected class NeurologyDepartment extends Department {
		public NeurologyDepartment() {
			name = "Neurology";
			waitingRoom = new WaitingRoom("WR " + name);
			wardsForMulti = new LinkedList<Ward>();
			wardsForSingle = new LinkedList<Ward>();
			wardsForMulti.addLast(new Ward(107, 2));
			wardsForMulti.addLast(new Ward(108, 2));
			wardsForMulti.addLast(new Ward(109, 2));
			wardsForSingle.addLast(new Ward(101, 1));
			wardsForSingle.addLast(new Ward(102, 1));
			deviceList = new LinkedList<Device>();

		}
	}

	protected class CardiologyDepartment extends Department {
		public CardiologyDepartment() {
			name = "Cardiology";
			waitingRoom = new WaitingRoom("WR " + name);
			wardsForMulti = new LinkedList<Ward>();
			wardsForSingle = new LinkedList<Ward>();
			wardsForMulti.addLast(new Ward(111, 3));
			wardsForMulti.addLast(new Ward(110, 2));
			wardsForSingle.addLast(new Ward(103, 1));
			wardsForSingle.addLast(new Ward(104, 1));
			deviceList = new LinkedList<Device>();

		}
	}
	
	protected class RadiologyDepartment extends Department {
		public RadiologyDepartment() {
			name = "Radiology";
			waitingRoom = new WaitingRoom("WR " + name);
			wardsForMulti = new LinkedList<Ward>();
			wardsForSingle = new LinkedList<Ward>();
			wardsForMulti.addLast(new Ward(112, 3));
			wardsForMulti.addLast(new Ward(106, 3));
			wardsForMulti.addLast(new Ward(113, 2));
			wardsForSingle.addLast(new Ward(105, 1));
			deviceList = new LinkedList<Device>();
		}
	}
	
	protected class RadiotherapyDepartment extends Department {
		public RadiotherapyDepartment() {
			name = "Radiotherapy";
			waitingRoom = new WaitingRoom("WR " + name);
			wardsForMulti = new LinkedList<Ward>();
			wardsForSingle = new LinkedList<Ward>();
			wardsForMulti.addLast(new Ward(119, 3));
			wardsForMulti.addLast(new Ward(126, 3));
			wardsForMulti.addLast(new Ward(120, 2));
			wardsForSingle.addLast(new Ward(125, 1));
			deviceList = new LinkedList<Device>();
			addDevice("needle", 2);
		}
	}
	
	protected class OncologyDepartment extends Department {
		public OncologyDepartment() {
			name = "Oncology";
			waitingRoom = new WaitingRoom("WR " + name);
			wardsForMulti = new LinkedList<Ward>();
			wardsForSingle = new LinkedList<Ward>();
			wardsForMulti.addLast(new Ward(118, 3));
			wardsForMulti.addLast(new Ward(117, 2));
			wardsForSingle.addLast(new Ward(124, 1));
			wardsForSingle.addLast(new Ward(123, 1));
			deviceList = new LinkedList<Device>();
			addDevice("needle", 1);
			addDevice("stethoscope", 1);
		}
	}
	
	protected class PhysiotherapyDepartment extends Department {
		public PhysiotherapyDepartment() {
			name = "Physiotherapy";
			waitingRoom = new WaitingRoom("WR " + name);
			wardsForMulti = new LinkedList<Ward>();
			wardsForSingle = new LinkedList<Ward>();
			wardsForMulti.addLast(new Ward(116, 2));
			wardsForMulti.addLast(new Ward(115, 2));
			wardsForMulti.addLast(new Ward(114, 2));
			wardsForSingle.addLast(new Ward(122, 1));
			wardsForSingle.addLast(new Ward(121, 1));
			deviceList = new LinkedList<Device>();

		}
	}
	
	
	protected LinkedList<Department> departmentList;
	protected EdgeGraph<Department> departmentMap;
	protected Reception reception;
	
	public Hospital() {
		departmentList = new LinkedList<Hospital.Department>();
		departmentList.addLast(new NeurologyDepartment());
		departmentList.addLast(new CardiologyDepartment());
		departmentList.addLast(new RadiologyDepartment());
		departmentList.addLast(new RadiotherapyDepartment());
		departmentList.addLast(new OncologyDepartment());
		departmentList.addLast(new PhysiotherapyDepartment());

		departmentMap = new EdgeGraph<Hospital.Department>();
		reception = new Reception();

		for (Department department : departmentList) {
			departmentMap.addNode(department);
		}
		departmentMap.addEdge(getDepartmentByName("Neurology"), getDepartmentByName("Cardiology"));
		departmentMap.addEdge(getDepartmentByName("Cardiology"), getDepartmentByName("Neurology"));
		departmentMap.addEdge(getDepartmentByName("Cardiology"), getDepartmentByName("Oncology"));
		departmentMap.addEdge(getDepartmentByName("Cardiology"), getDepartmentByName("Radiology"));
		departmentMap.addEdge(getDepartmentByName("Radiology"), getDepartmentByName("Cardiology"));
		departmentMap.addEdge(getDepartmentByName("Radiology"), getDepartmentByName("Radiotherapy"));
		departmentMap.addEdge(getDepartmentByName("Radiotherapy"), getDepartmentByName("Radiology"));
		departmentMap.addEdge(getDepartmentByName("Radiotherapy"), getDepartmentByName("Oncology"));
		departmentMap.addEdge(getDepartmentByName("Oncology"), getDepartmentByName("Cardiology"));
		departmentMap.addEdge(getDepartmentByName("Oncology"), getDepartmentByName("Radiotherapy"));
		departmentMap.addEdge(getDepartmentByName("Oncology"), getDepartmentByName("Physiotherapy"));
		departmentMap.addEdge(getDepartmentByName("Physiotherapy"), getDepartmentByName("Oncology"));
	}
	public Department getDepartmentByName(String depName) {
		for (Department department : departmentList) {
			if(department.getName().equals(depName)) return department;
		}
		return null;
	}
	
	protected Device deviceFactory(String type, int initialAmount) {
		if(type.equals("needle")) return new NeedleDevice(initialAmount);
		if(type.equals("stethoscope")) return new StethoscopeDevice(initialAmount);
		return null;
	}
	
	public String getRoomNumberForPatientNamed(String name) {
		return reception.getPatientInfo(name).getRoom().toString();
	}
	
	public void signInPatientToDepartment(Patient p, String depName) {
		Department department = getDepartmentByName(depName);
		department.addPatient(p);
	}
	
	
	public void signOutPatient(Patient p) {
		signOutPatient(p.getName());
	}
	
	public void signOutPatient(String name) {
		PatientInfo info = reception.getPatientInfo(name);
		Department department = info.getDepartment();
		department.signOutPatient(info.getPatient());
		reception.removePatientInfo(info);
	}
	
	public void printRouteFromTo(String fromDepartment, String toDepartment) {
		LinkedList<Department> path = departmentMap.findPath(
				getDepartmentByName(fromDepartment), 
				getDepartmentByName(toDepartment));
		if (path == null) {
			System.out.println("Route from " + fromDepartment + " to " + toDepartment + " not found!");
			return;
		} else {
			String outputString = "Path from " + fromDepartment + " to " + toDepartment + ":\n"
					+ "\t" + fromDepartment;
			for (Department department : path) {
				outputString += " >> " + department.getName();
			}
			System.out.println(outputString);
		}
	}
	
	public void printRouteFromToAvoiding(String fromDepartment, String toDepartment, String avoidDepartment) {
		LinkedList<Department> path = departmentMap.findPathAvoiding(
				getDepartmentByName(fromDepartment), 
				getDepartmentByName(toDepartment),
				getDepartmentByName(avoidDepartment)); 

		if (path == null) {
			System.out.println("Route from " + fromDepartment 
					+ " to " + toDepartment 
					+ " avoiding " + avoidDepartment
					+ " not found!");
			return;
		} else {
			String outputString = "Path from " + fromDepartment
					+ " to " + toDepartment 
					+ " avoiding " + avoidDepartment + ":\n"
					+ "\t" + fromDepartment;
			for (Department department : path) {
				outputString += " >> " + department.getName();
			}
			System.out.println(outputString);
		}
	}
	
	public void printRouteFromToVia(
			String fromDepartment, String toDepartment, String viaDepartment) {
		LinkedList<Department> path = departmentMap.findPathVia(
				getDepartmentByName(fromDepartment), 
				getDepartmentByName(toDepartment),
				getDepartmentByName(viaDepartment)); 
		if (path == null) {
			System.out.println("Route from " + fromDepartment 
					+ " to " + toDepartment 
					+ " via " + viaDepartment
					+ " not found!");
			return;
		} else {
			String outputString = "Path from " + fromDepartment
					+ " to " + toDepartment 
					+ " via " + viaDepartment + ":\n"
					+ "\t" + fromDepartment;
			for (Department department : path) {
				outputString += " >> " + department.getName();
			}
			System.out.println(outputString);
		}
	}
	
	public String closestDepartmentToWithDevice(String dep, String device) {
		Department fromDepartment = getDepartmentByName(dep);
		for (Department department : departmentMap.bfsIteratFrom(fromDepartment)) {
			if(department.hasDevice(device) != null) return department.name;
		}
		return null;
	}
	
	public String showRegister() {
		return reception.toString();
	}
	
	public String toString() {
		String output = "";
		output += "\n******************* PATIENTS ********************\n";
		output += reception.toString();

		output += "\n****************** DEPARTMENTS ******************\n";
		for (Department department : departmentList) {
			output += department.toString() + "\n";
		}
		return output;
	}
}
