/**
 * 
 */
package org.jocean.logback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.jocean.util.OSUtils;

import ch.qos.logback.classic.net.LoggingEventPreSerializationTransformer;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.spi.PreSerializationTransformer;

/**
 * @author isdom
 *
 */
public class HornetQAppender extends AppenderBase<ILoggingEvent> {

	public HornetQAppender() {
	}
	
	/**
	 * @return the _modulename
	 */
	public String getModulename() {
		return _modulename;
	}

	/**
	 * @param _modulename the _modulename to set
	 */
	public void setModulename(String _modulename) {
		this._modulename = _modulename;
	}
	
	/**
	 * @return the _hornetqHost
	 */
	public String getHornetqHost() {
		return _hornetqHost;
	}

	/**
	 * @param _hornetqHost the _hornetqHost to set
	 */
	public void setHornetqHost(String hornetqHost) {
		this._hornetqHost = hornetqHost;
	}

	/**
	 * @return the _hornetqPort
	 */
	public int getHornetqPort() {
		return _hornetqPort;
	}

	/**
	 * @param _hornetqPort the _hornetqPort to set
	 */
	public void setHornetqPort(int hornetqPort) {
		this._hornetqPort = hornetqPort;
	}

	/**
	 * @return the _topic
	 */
	public String getTopic() {
		return _topic;
	}

	/**
	 * @param _topic the _topic to set
	 */
	public void setTopic(String topic) {
		this._topic = topic;
	}
	
	public void setIncludeCallerData(boolean includeCallerData) {
		this._includeCallerData = includeCallerData;
	}
	  
	@Override
	public void start() {
		if ( null == _hornetqHost 
			&& 0 == _hornetqPort
			&& null == _topic ) {
			addError("Miss layout or Host or Port or MessageAddress for the appender named [" + name + "].");
			return;
		}
		
		final Map<String, Object> connectionParams = new HashMap<String, Object>();
	    
		connectionParams.put(org.hornetq.core.remoting.impl.netty.TransportConstants.PORT_PROP_NAME, 
				_hornetqPort);
		
		connectionParams.put(org.hornetq.core.remoting.impl.netty.TransportConstants.HOST_PROP_NAME, 
				_hornetqHost);
		
		try {
			final TransportConfiguration transportConfigurations = new TransportConfiguration(
							NettyConnectorFactory.class.getName(),
							connectionParams);

			final HornetQConnectionFactory cf = 
					HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, 
					transportConfigurations);
			
			final Topic destTopic = HornetQJMSClient.createTopic(_topic);
			
			_connection = cf.createConnection();
			//And we create a non transacted JMS Session, with AUTO_ACKNOWLEDGE acknowledge mode:
			
			_session = _connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//we create a MessageProducer that will send orders to the queue:

			//final MessageProducer producer = session.createProducer(orderQueue);
			_producer = _session.createProducer(destTopic);
			
		} catch (Exception e) {
			addError( "failed to init HornetQ Connection with param host[" + _hornetqHost + "],"
					+ "port[" + _hornetqPort + "],topic[" + _topic + "]", e );
			if ( null != _connection ) {
				try {
					_connection.close();
				} catch (JMSException e1) {
					addError( "failed to init HornetQ Connection", e1 );
				}
				finally {
					_connection = null;
				}
			}
			return;
		}
		
		super.start();
	}
	 
	
	/* (non-Javadoc)
	 * @see ch.qos.logback.core.AppenderBase#stop()
	 */
	@Override
	public void stop() {
		super.stop();
		
		if ( null != _connection ) {
			try {
				_connection.close();
			} catch (JMSException e1) {
				addError( "failed to close HornetQ Connection", e1 );
			}
			finally {
				_connection = null;
			}
		}
	}

	@Override
	protected void append(ILoggingEvent eventObject) {

		if ( null == this._hostname ) {
			synchronized (this) {
				//	Double-check locking avoid race condition 
				if ( null == this._hostname ) {
					this._hostname = OSUtils.getLocalHostname();
					this._pid = Integer.toString( OSUtils.getCurrentPid() );
				}
			}
		}
		
		try {
	        postProcessEvent(eventObject);
	        final Serializable serEvent = _pst.transform(eventObject);
			final ObjectMessage message = _session.createObjectMessage(serEvent);
			message.setStringProperty("hostname", _hostname);
			message.setStringProperty("pid", _pid);
			message.setStringProperty("modulename", _modulename);
			
			_producer.send(message);
		} catch (JMSException e) {
			addError( "failed to append logevent", e );
		}
		
	}

	protected void postProcessEvent(ILoggingEvent event) {
		if (_includeCallerData) {
			event.getCallerData();
		}
	}

	boolean _includeCallerData = false;

	final PreSerializationTransformer<ILoggingEvent> _pst = 
			new LoggingEventPreSerializationTransformer();
	  
	private String 	_hornetqHost = null;
	
	private int 	_hornetqPort = 0;
	
	private String	_topic = null;
	
	private Connection 	_connection;
	
	private Session		_session;
	
	private MessageProducer _producer;
	
	private volatile String	_hostname;
	
	private	volatile String	_pid;
	
	private String			_modulename = "unknown";

}
