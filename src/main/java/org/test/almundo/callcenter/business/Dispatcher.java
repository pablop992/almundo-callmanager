package org.test.almundo.callcenter.business;

import org.test.almundo.callcenter.model.CallToProcess;
import org.test.almundo.callcenter.processor.CallProcessor;
import org.test.almundo.callcenter.processor.EmployeeCallProcessor;

/**
 * Clase encargada de recibir las llamadas para su procesamiento
 * e iniciar con la busqueda del processor para su ejecucion
 * 
 * @author pablo.pelaez
 *
 */
public class Dispatcher {
	
	/**
	 * El primer processor en la cadena de responsabilidad
	 */
	public final CallProcessor mainProcessor = new EmployeeCallProcessor();
	
	/**
	 * Envia la llamada para su procesamiento a los processor
	 * encadenados para tal fin
	 * 
	 * @param call llamada a procesar
	 * @return true si la llamada fue aceptada, false en caso contrario
	 */
	public boolean dispatchCall(CallToProcess call){
		return mainProcessor.processCall(call);
	}

}
