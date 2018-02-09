package org.test.almundo.callcenter.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Representa una llamada a procesar
 * 
 * @author pablo.pelaez
 *
 */
public class CallToProcess implements Runnable {
	
	/**
	 * Cantidad de milesimas en un segundo
	 */
	private static final Integer MILLIS_PER_SECOND = 1000;
	/**
	 * Cantidad maxima de segundos que puede durar una llamada
	 */
	private static final Integer MAX_CALL_DURAITON_SECONDS = 10;
	/**
	 * Cantidad minima de segundos que puede durar una llamada
	 */
	private static final Integer MIN_CALL_DURAITON_SECONDS = 5;
	
	private static final Logger LOG = LoggerFactory.getLogger(CallToProcess.class);
	
	/**
	 * Duracion de la llamada
	 */
	private Integer secondsDurationTime;
	/**
	 * Identificador de la llamada
	 */
	private String callId = UUID.randomUUID().toString();
	
	
	
	public CallToProcess(){
		this.secondsDurationTime = MAX_CALL_DURAITON_SECONDS 
				- Double.valueOf(Math.floor(Math.random()*(MIN_CALL_DURAITON_SECONDS + 1))).intValue();
	}
	
	/**
	 * Lanza una llamada, escencialmente solo duerme el hilo
	 * para simular la duracion de esta
	 */
	public void run() {
		try {
			Thread.sleep(secondsDurationTime * MILLIS_PER_SECOND);
		} catch (InterruptedException e) {
			LOG.error("Error tratando de lanzar llamada con id: {}", callId, e);
		}
	}
	
	/*
	 * Accesores de la info de llamada
	 */
	
	public Integer getSecondsDurationTime(){
		return this.secondsDurationTime;
	}
	
	public String getCallId(){
		return this.callId;
	}

}
