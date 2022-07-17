package io.github.hlg212.esb.task;

import io.github.hlg212.esb.cache.Constants;
import io.github.hlg212.fcf.Task;
import io.github.hlg212.fcf.api.mq.QueueApi;
import io.github.hlg212.fcf.api.mq.QueueApi;
import io.github.hlg212.fcf.constants.TaskConstants;
import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.mq.IQueue;
import io.github.hlg212.fcf.model.mq.QueueDeleteParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 清理废弃的mq  queue;
 * 每过一小时清理一次，清理逻辑：
 * 如果该queue没有消费者，堆积的数据超过了10000
 * 将会加入到清理列表，当第二次还是存在该队列，堆积的数据超过了上一次，将进行清理
 *
 * 已采用ttl与expires实现数据与队列的删除
 * @author hlg
 * @date 2019年4月17日
 */
@Slf4j
//@Component
//@CacheConfig(cacheNames = Constants.QueueClear)
public class QueueClear implements Task {
    @Autowired
    private QueueApi queueApi;

    private static String prefix = "eventServiceBus.";

    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

    @Autowired
    @Lazy
    private QueueClear self;

    @Override
    public String executeTask(String param) {
        PageInfo info = queueApi.findPage(prefix,1,500,virtualHost);

        List<IQueue> list = info.getList();
        Map currClearMap = judgeClear(list);
        Map lastClearMap = self.getLastClearData();
        if( lastClearMap == null )
        {
            lastClearMap = new HashMap();
        }
        Map clearMap = judgeClear(currClearMap,lastClearMap);
        clear(clearMap);

        lastClearMap.putAll(currClearMap);
        self.saveLastClearMap(lastClearMap,clearMap);

        return TaskConstants.SUCCESS;
    }

    @CachePut(key = "'lastClearMap'")
    public Map saveLastClearMap(Map lastClearMap,Map clearMap)
    {
        clearMap.forEach( (key,value) -> {
          lastClearMap.remove(key);
        });

        return lastClearMap;
    }

    private void clear(Map clearMap)
    {
        log.info("清理废弃的队列: {}",clearMap.size());
        clearMap.forEach( (key,value) -> {
            log.info("queue[{}]清理,数据积压:{} ",key,value);
//            QueueDeleteParam param = new QueueDeleteParam(virtualHost,key.toString());
//            queueApi.del(key.toString(),virtualHost,param);
            self.asyncQueueDel(key.toString());
        });
    }

    @Async
    public void  asyncQueueDel(String key)
    {
        QueueDeleteParam param = new QueueDeleteParam(virtualHost,key);
        queueApi.del(key,virtualHost,param);
    }

    private Map judgeClear(Map currClearMap,Map lastClearMap)
    {
        Map clearMap = new HashMap();
        currClearMap.forEach( (key,value) -> {

            Object val = lastClearMap.get(key);
            if( value != null &&  val !=null && Integer.valueOf(value.toString())  >= Integer.valueOf(val.toString()) )
            {
                clearMap.put(key,value);
            }
        });

        return clearMap;
    }

    private Map judgeClear(List<IQueue> list)
    {
        Map clearMap = new HashMap();
        if( list != null )
        {
            list.stream().forEach(queue->{
                if( queue.getConsumers() <= 0 )
                {
                    if(  queue.getMessagesReady()  >= 10000 )
                    {
                        clearMap.put(queue.getName(),queue.getMessagesReady());
                    }
                }
            });
        }
        return clearMap;
    }

    @Cacheable(key = "'lastClearMap'")
    public HashMap getLastClearData()
    {
        return null;
    }


}
