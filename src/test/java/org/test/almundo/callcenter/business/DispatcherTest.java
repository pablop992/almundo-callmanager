package org.test.almundo.callcenter.business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.test.almundo.callcenter.test.util.CreationUtils;

/**
 * 
 * Test Suite para la clase Dispatcher y Processors asociados
 * 
 * @author pablo.pelaez
 *
 */
public class DispatcherTest {
	
	/**
	 * Constantes para la prueba
	 */
	private static final Integer CALLS_QTY_LIMIT = 10;
	private static final Integer CALLS_QTY_MORE_THAN_LIMIT = 11;
	
	private static final Logger LOG = LoggerFactory.getLogger(DispatcherTest.class);
	
	/**
	 * Envia a procesar una cantidad de llamadas que llega al limite de procesamiento.
	 * Se verifica que sucede con la ultima llamada enviada
	 */
	@Test
	public void processUpToTenCalls(){
		LOG.info(">>>>>>>Init test: processUpToTenCalls");
		boolean callAccepted = CreationUtils.dispatchFixedQuantityCallsToDispatcher(
				new Dispatcher(), CALLS_QTY_LIMIT);
		assertTrue(callAccepted);
		LOG.info(">>>>>>>Finish test: processUpToTenCalls");
	}
	
	/**
	 * Envia a procesar una cantidad de llamadas que sobrepasa el limite de procesamiento.
	 * Se verifica que sucede con la ultima llamada enviada
	 */
	@Test
	public void processMoreThanTenCalls(){
		LOG.info(">>>>>>>Init test: processMoreThanTenCalls");
		boolean callAccepted = CreationUtils.dispatchFixedQuantityCallsToDispatcher(
				new Dispatcher(), CALLS_QTY_MORE_THAN_LIMIT);
		assertFalse(callAccepted);
		LOG.info(">>>>>>>Finish test: processMoreThanTenCalls");
	}
}
