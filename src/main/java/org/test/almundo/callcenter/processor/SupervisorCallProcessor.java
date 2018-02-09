package org.test.almundo.callcenter.processor;

import org.test.almundo.callcenter.model.RoleType;

/**
 * Representa un conjunto de supervisores como processors de llamadas.
 * 
 * @author pablo.pelaez
 *
 */
public class SupervisorCallProcessor extends CallProcessor{

	public SupervisorCallProcessor() {
		super(new DirectorCallProcessor(), 3, RoleType.SUPERVISOR);
	}

}
