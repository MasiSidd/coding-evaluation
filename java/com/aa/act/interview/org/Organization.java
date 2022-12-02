package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

	private Position root;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		//your code here
		if(title.equalsIgnoreCase("CEO")) {
			Integer employeeIdentifier = (int) System.currentTimeMillis();
			Employee emp = new Employee(employeeIdentifier, person);
			Optional<Employee> employee = Optional.of(emp);
			root.setEmployee(employee);
		}else {
			Position pos = root;
			hireEmployee(person, title, pos);
		}
		 
		return Optional.empty();
	}

	/**
	 * @param person
	 * @param title
	 * @param pos
	 */
	private void hireEmployee(Name person, String title, Position pos) {
		for(Position position : pos.getDirectReports())
		{
			if (position.getTitle() == title)
			{
				Integer employeeIdentifier = (int) ((int) System.currentTimeMillis()*Math.random());
				Employee emp = new Employee(employeeIdentifier, person);
				Optional<Employee> employee = Optional.of(emp);
				if(!position.isFilled()) {
					// if position is not filled hire current employee at the position
					position.setEmployee(employee);	
				}else {
					// if position is already filled create a new position
					// and add as a direct reportee to the 1 level above position
					Position newPosition = new Position(title, emp);
					pos.addDirectReport(newPosition);
				}
			}else {
				hireEmployee(person, title, position);
			}	
		}
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
