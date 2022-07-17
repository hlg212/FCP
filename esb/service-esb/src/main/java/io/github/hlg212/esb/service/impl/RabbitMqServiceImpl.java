package io.github.hlg212.esb.service.impl;

import io.github.hlg212.esb.service.RabbitMqService;
import io.github.hlg212.esb.util.MqChannelPoolHelper;
import com.rabbitmq.client.Channel;
import io.github.hlg212.fcf.util.ExceptionHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * ClassName: RabbitMqServiceImpl Function: TODO ADD FUNCTION. Reason: TODO ADD
 * REASON(可选). date: 2018年5月16日 下午5:36:53
 * 
 * @author huangligui
 */
@Service
public class RabbitMqServiceImpl implements RabbitMqService {

	
	@Override
	public void createExchange(String exchangeName, String type) {
		createExchange(exchangeName, type, false);
	}


	@Override
	public void createExchange(String exchangeName, String type, boolean autoDelete) {
		Channel channel = getChannel();
		try {
			channel.exchangeDeclare(exchangeName, type, true, autoDelete, null);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	@Override
	public void createQueue(String queueName) {
		Channel channel = getChannel();
		try {
			channel.queueDeclare(queueName, true, false,false, null);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	@Override
	public void deleteExchange(String exchangeName) {
		Channel channel = getChannel();
		try {
			channel.exchangeDelete(exchangeName, false);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}

	}

	@Override
	public void deleteQueue(String queueName) {
		Channel channel = getChannel();
		try {
			channel.queueDeleteNoWait(queueName, false, false);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	@Override
	public void bindExchangeToQueue(String exchangeName, String queueName, String routingKey) {
		Channel channel = getChannel();
		try {
			// 绑定队列到交换机
			channel.queueBind(queueName, exchangeName, routingKey);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	@Override
	public void unbindExchangeToQueue(String exchangeName, String queueName, String routingKey) {
		Channel channel = getChannel();
		try {
			channel.queueUnbind(queueName, exchangeName, routingKey);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	@Override
	public void sendToExchange(String exchangeName, String routingKey, byte[] message) {
		Channel channel = getChannel();
		try {
			channel.basicPublish(exchangeName, routingKey, null, message);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}

	}

	@Override
	public void sendToQueue(String queueName, byte[] message) {
		Channel channel = getChannel();
		try {
			channel.basicPublish("", queueName, null, message);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	@Override
	public void bindExchangeToExchange(String exchangeName, String toExchangeName,String routeKey) {
		// TODO Auto-generated method stub
		Channel channel = getChannel();
		try {
			channel.exchangeBind(toExchangeName, exchangeName, routeKey);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	@Override
	public void unbindExchangeToExchange(String exchangeName, String toExchangeName) {
		// TODO Auto-generated method stub
		Channel channel = getChannel();
		try {
			channel.exchangeUnbind(toExchangeName, exchangeName, "", null);
		} catch (IOException e) {
			error(e);
		} finally {
			returnChannel(channel);
		}
	}

	private Channel getChannel() {
		return MqChannelPoolHelper.getChannel();
	}
	
	private void returnChannel(Channel channel)
	{
		MqChannelPoolHelper.returnObject(channel);
	}
	
	private void error(Exception e)
	{
		//Throwable ce = ExceptionUtils.getCause(e);

		ExceptionHelper.throwServerException(e);
	}
	
}
