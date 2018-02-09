package org.test.almundo.callcenter.business;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.almundo.callcenter.model.CallToProcess;

/**
 * 
 * Manages the incoming calls in a queue data structure.
 * 
 * @author pablo.pelaez
 *
 */
public class CallQueueManager {

	/**
	 * Constante que establece un periodo casi inmediato de ejecucion entre cada
	 * intento de desencolamiento/procesamiento de llamadas
	 */
	private static final Long TIME_BETWEEN_EXECUTION_MILLIS = 1L;
	/**
	 * Tiempo en MS a esperar si se requiere
	 */
	private static final Long TIME_TO_WAIT = 1000L;
	private static final Logger LOG = LoggerFactory.getLogger(CallQueueManager.class);

	private final BlockingQueue<CallToProcess> callQueue = new LinkedBlockingQueue<CallToProcess>();
	private final Dispatcher callDispatcher = new Dispatcher();
	
	public CallQueueManager() {
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new CallRetriever(),
				TIME_BETWEEN_EXECUTION_MILLIS, TIME_BETWEEN_EXECUTION_MILLIS, TimeUnit.MILLISECONDS);
	}

	/**
	 * Encola una llamada
	 * 
	 * @param call
	 *            llamada a encolar
	 */
	public void queueCall(CallToProcess call) {
		try {
			callQueue.put(call);
		} catch (InterruptedException e) {
			// Esta interrupcion no deberia suceder, sin embargo se maneja
			LOG.error("Error tratando de encolar llamada con id: {}", call.getCallId(), e);
		}
		LOG.info("Llamada con id: {} encolada exitosamente", call.getCallId());
	}
	
	/**
	 * Define si la cola de llamadas esta vacia
	 * 
	 * @return true si la cola esta vacia, false en caso contrario
	 */
	public boolean isQueueEmpty() {
		return callQueue.isEmpty();
	}

	private class CallRetriever implements Runnable {

		/**
		 * Ejecucion que permite obtener llamadas de la cola y ejecutarlas sobre
		 * el Dispatcher
		 */
		public void run() {
			try {
				// Revisa el primer elemento en cola
				CallToProcess call = callQueue.peek();

				/*
				 * Si existe al menos un elemento esperando en cola, y un
				 * processor ya se encuentra procesandola, eliminela de la cola;
				 * de lo contrario duerma el hilo por el tiempo indicado e
				 * intente nuevamente en la siguiente ejecución del hilo
				 */
				if (call != null && callDispatcher.dispatchCall(call)) {
					callQueue.take();
				} else {
					Thread.sleep(TIME_TO_WAIT);
				}
			} catch (InterruptedException e) {
				// Esta interrupcion no deberia suceder, sin embargo se captura
				// pero no se maneja
				LOG.error("Error tratando de procesar llamada", e);
			}
		}
	}

}
