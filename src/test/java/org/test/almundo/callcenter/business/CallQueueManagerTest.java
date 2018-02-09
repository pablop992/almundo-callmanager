package org.test.almundo.callcenter.business;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.almundo.callcenter.test.util.CreationUtils;

/**
 * Clase de pruebas para la clase CallQueueManager.
 * 
 * @author pablo.pelaez
 *
 */
public class CallQueueManagerTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(CallQueueManager.class);
	
	private static final Long TIME_WAIT_NO_QUEUED = 5000L;
	private static final Long TIME_WAIT_QUEUED_LIMIT = 10000L;
	private static final Long TIME_WAIT_QUEUED_DOUBLE_LIMIT = 20000L;
	
	private static final Integer CALLS_QTY_LIMIT = 10;
	private static final Integer CALLS_QTY_DOUBLE_LIMIT = 20;
	
	
	/**
	 * Verifica el comportamiento normal de la aplicacion cuando no 
	 * existe ninguna llamada encolada
	 * @throws InterruptedException si algo sucede en la ejecucion - No manejada
	 */
	@Test
	public void verifyBehaviorWhenNoCallsQueued() throws InterruptedException{
		LOG.info(">>>>>>>Init test: verifyBehaviorWhenNoCallsQueued");
		CallQueueManager callQueueMngr = new CallQueueManager();
		Thread.sleep(TIME_WAIT_NO_QUEUED);
		LOG.info(">>>>>>>Finish test: verifyBehaviorWhenNoCallsQueued");
	}
	
	/**
	 * Verifica el comportamiento normal de la aplicacion cuando se
	 * encola un total de llamadas que llega al limite permitido
	 * @throws InterruptedException si algo sucede en la ejecucion - No manejada
	 */
	@Test
	public void verifyQueueCallsUpToLimit() throws InterruptedException{
		LOG.info(">>>>>>>Init test: verifyQueueCallsUpToLimit");
		CallQueueManager callQueueMngr = new CallQueueManager();
		CreationUtils.queueCalls(callQueueMngr, CALLS_QTY_LIMIT);
		Thread.sleep(TIME_WAIT_QUEUED_LIMIT);
		assertTrue(callQueueMngr.isQueueEmpty());
		LOG.info(">>>>>>>Finish test: verifyQueueCallsUpToLimit");
	}
	
	/**
	 * Verifica el comportamiento normal de la aplicacion cuando se
	 * encola un total de llamadas que dobla al limite permitido
	 * @throws InterruptedException si algo sucede en la ejecucion - No manejada
	 */
	@Test
	public void verifyQueueCallsMoreThanLimit() throws InterruptedException{
		LOG.info(">>>>>>>Init test: verifyQueueCallsMoreThanLimit");
		CallQueueManager callQueueMngr = new CallQueueManager();
		CreationUtils.queueCalls(callQueueMngr, CALLS_QTY_DOUBLE_LIMIT);
		//Se espera a depurar el total de llamadas encoladas, para el peor de los casos
		Thread.sleep(TIME_WAIT_QUEUED_DOUBLE_LIMIT);
		assertTrue(callQueueMngr.isQueueEmpty());
		LOG.info(">>>>>>>Finish test: verifyQueueCallsMoreThanLimit");
	}
}
