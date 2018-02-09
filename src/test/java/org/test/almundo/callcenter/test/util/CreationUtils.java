package org.test.almundo.callcenter.test.util;

import org.test.almundo.callcenter.business.CallQueueManager;
import org.test.almundo.callcenter.business.Dispatcher;
import org.test.almundo.callcenter.model.CallToProcess;

/**
 * Clase utilitaria para la creacion de objetos de prueba
 * 
 * @author pablo.pelaez
 *
 */
public class CreationUtils {
	
	private CreationUtils(){}
	
	/**
	 * Utilitario que lanza una cantidad determinada de llamadas
	 * 
	 * @param dispatcher Objeto de tipo Dispatcher usado para lanzar las llamadas
	 * @param callQty numero de llamadas a lanzar
	 * @return resultado de la ultima ejecución de llamada
	 */
	public static boolean dispatchFixedQuantityCallsToDispatcher(Dispatcher dispatcher, Integer callQty){
		
		/*
		 * Manda a procesar el total de llamadas esperadas, menos una 
		 */
		for (int i = 1; i < callQty; i++) {
			dispatcher.dispatchCall(new CallToProcess());
		}
		
		//Solo retorna la ultima llamada a procesar, sea cual sea el resultado
		return dispatcher.dispatchCall(new CallToProcess());
	}
	
	/**
	 * Encola un determinado numero de llamadas
	 * 
	 * @param mngr Objeto donde se desean encolar las llamadas
	 * @param numberOfCalls numero de llamadas a encolar
	 */
	public static void queueCalls(CallQueueManager mngr, Integer numberOfCalls){
		for (int i = 0; i < numberOfCalls; i++) {
			mngr.queueCall(new CallToProcess());
		}
	}
}
