package io.github.hlg212.esb.util;

import com.rabbitmq.client.Channel;
import io.github.hlg212.fcf.util.SpringHelper;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqChannelPoolHelper {

	
	private static MqChannelPoolHelper _instance;
	
	@Autowired
	CachingConnectionFactory connectionFactory;
	
	@Value("${mqtt.maxPoolSize:200}")
	private int maxPoolSize;
	
	//@Autowired
	private ChannelPool channelPool;
	
	//@Bean
	public ChannelPool channelPool()
	{
		ChannelPoolFactory f = new ChannelPoolFactory();
		f.setConnectionFactory(connectionFactory);
		ChannelPool pool  = new ChannelPool(f,maxPoolSize);
		return pool;
	}
	

	
	private static MqChannelPoolHelper getInstance()
	{
		if( _instance == null )
		{
			_instance =	(MqChannelPoolHelper) SpringHelper.getBean(MqChannelPoolHelper.class);
			_instance.channelPool = _instance.channelPool();
		}
		return _instance;
	}
	
	public static Channel getChannel()
	{
		try {
			return getInstance().channelPool.borrowObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void returnObject(Channel channel)
	{
		if( channel != null ) {
			try {
				getInstance().channelPool.returnObject(channel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public class ChannelPool extends GenericObjectPool<Channel>
	{
		
		public ChannelPool(ChannelPoolFactory f, int maxPoolSize) {
			// TODO Auto-generated constructor stub
			//super(f,maxPoolSize);
			super(f);
		}
		
	}
	
	public class ChannelPoolFactory extends BasePooledObjectFactory<Channel>
	{
		CachingConnectionFactory connectionFactory;

		@Override
		public Channel create() throws Exception {
			return connectionFactory.createConnection().createChannel(false);
		}

		@Override
		public PooledObject<Channel> wrap(Channel channel) {
			return new DefaultPooledObject(channel);
		}

		public void setConnectionFactory(CachingConnectionFactory connectionFactory) {
			this.connectionFactory = connectionFactory;
		}

	}
	
}
