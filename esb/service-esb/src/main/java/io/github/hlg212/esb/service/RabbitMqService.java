package io.github.hlg212.esb.service;



/** 
 * ClassName: RabbitMqService
 * Function: mq接口服务 
 * Reason: 封装提供常用接口
 * date: 2018年5月16日 上午10:06:01
 * 
 * @author huangligui 
 */
public interface RabbitMqService {
    /**
     * 创建exchange
     * @param exchangeName
     *  @param type
     */
    void createExchange(String exchangeName, String type);
    
    void createExchange(String exchangeName, String type, boolean autoDelete);


    void createQueue(String queueName);


    /**
     * 删除exchange
     * @param exchangeName
     */
    void deleteExchange(String exchangeName);
    
    /**
     * 删除queue
     * @param queueName
     */
    void deleteQueue(String queueName);

    /**
     * 绑定exchange到queue
     * @param exchangeName
     * @param queueName
     */
    void bindExchangeToQueue(String exchangeName, String queueName, String routingKey);

    /**
     * 绑定exchange到exchange
     * @param exchangeName
     * @param queueName
     */
    void bindExchangeToExchange(String exchangeName, String toExchangeName,String routeKey);
    
    /**
     * 取消绑定exchange到queue
     * @param exchangeName
     * @param queueName
     */
    void unbindExchangeToQueue(String exchangeName, String queueName, String routingKey);
    
    /**
     * 取消绑定exchange到exchange
     * @param exchangeName
     * @param queueName
     */
    void unbindExchangeToExchange(String exchangeName, String toExchangeName);

    /**
     * 发送消息到exchange
     * @param exchangeName
     * @param message
     */
    void sendToExchange(String exchangeName, String routingKey, byte[] message);

    /**
     * 发送消息到queue
     * @param queueName
     * @param message
     */
    void sendToQueue(String queueName, byte[] message);
}
