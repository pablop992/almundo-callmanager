package org.test.almundo.callcenter.processor;

import org.test.almundo.callcenter.model.RoleType;

/**
 * 
 * Representa los empleados como processors de llamadas
 * 
 * @author pablo.pelaez
 *
 */
public class EmployeeCallProcessor extends CallProcessor {

	public EmployeeCallProcessor() {
		super(new SupervisorCallProcessor(), 6, RoleType.EMPLEADO);
	}

}
