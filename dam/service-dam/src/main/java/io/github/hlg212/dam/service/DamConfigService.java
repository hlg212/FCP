
package io.github.hlg212.dam.service;

import io.github.hlg212.dam.model.bo.DamConfigBo;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;
import org.springframework.cache.annotation.CacheConfig;

/** 
 * DamConfigService
 *
 * @author huangligui
 * @date 2021年06月01日
 */
@CacheConfig
public interface DamConfigService extends CurdServiceImpl<DamConfigBo> {
	
}
