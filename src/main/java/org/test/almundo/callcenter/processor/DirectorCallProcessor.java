package org.test.almundo.callcenter.processor;

import org.test.almundo.callcenter.model.RoleType;

/**
 * Representa al director como processor de llamadas
 * 
 * @author pablo.pelaez
 *
 */
public class DirectorCallProcessor extends CallProcessor {

	public DirectorCallProcessor() {
		super(null, 1, RoleType.DIRECTOR);
	}

}
