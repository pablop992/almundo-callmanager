package org.test.almundo.callcenter.processor;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.almundo.callcenter.model.CallToProcess;
import org.test.almundo.callcenter.model.RoleType;

/**
 * 
 * Define el comportamiento abstracto de un processor de llamadas
 * 
 * @author pablo.pelaez
 *
 */
public class CallProcessor {
	
	/**
	 * Proximo processor en la cadena de responsabilidad
	 */
	private final CallProcessor nextProcessor;
	/**
	 * Pool de workers para soportar la capacidad del processor
	 */
	private final ThreadPoolExecutor callProcessorsPool;
	/**
	 * Numero de workers que puede lanzar este processor
	 */
	private final Integer numberOfProcessors;
	
	/**
	 * Rol al cual corresponde el processor
	 */
	private final RoleType role;
	
	private static final Logger LOG = LoggerFactory.getLogger(CallProcessor.class);
	
	public CallProcessor(CallProcessor nextProcessor, Integer numberOfProcessors, RoleType role){
		this.nextProcessor = nextProcessor;
		this.numberOfProcessors = numberOfProcessors;
		this.role = role;
		/*
		 * Se crea un pool sin la capacidad de encolar tareas
		 * ni de extender la capacidad total, esto para que
		 * la capacidad inicial sea la capacidad final y no
		 * admita mas tareas adicionales cuando llegue al tope
		 */
		this.callProcessorsPool = new ThreadPoolExecutor(
				this.numberOfProcessors, this.numberOfProcessors, 0L, 
				TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
	}
	
	/**
	 * Intenta procesar una llamada. Si la capacidad de procesamiento
	 * esta a tope, intenta pasarlo al siguiente processor, si existe.
	 * @param call Llamada a procesar
	 * @return true si el processor actual pudo procesar la llamada,
	 * 			false si no fue capaz de procesarlo y no tiene mas 
	 * 			processors en la cadena, o si el siguiente processor
	 * 			en la cadena no pudo procesar la llamada
	 */
	public boolean processCall(CallToProcess call){
		
		Boolean taskAccepted = Boolean.FALSE;
		
		try{
			this.callProcessorsPool.execute(call);
			taskAccepted = Boolean.TRUE;
			LOG.info("Llamada con id: {} aceptada por processor: {}", call.getCallId(), role.name());

		}catch(RejectedExecutionException rex){
			//Si el pool rechaza la llamada, lo pasa al siguiente processor
			if(nextProcessor != null){
				taskAccepted = nextProcessor.processCall(call);
			}
			else{
				LOG.info("Llamada con id: {} rechazada. No existen workers disponibles", call.getCallId());
			}
		}
		
		return taskAccepted;
	}
	
	/**
	 * Obtiene la capacidad de procesamiento del actual processor y
	 * aniadiendo la capacidad de los processors encadenados
	 * @return capacidad de processors
	 */
	public Integer getCapacityIncludingChainedCapacity(){
		return this.numberOfProcessors 
				+ (nextProcessor != null ? nextProcessor.getCapacityIncludingChainedCapacity() : 0);
	}
	
	
}
