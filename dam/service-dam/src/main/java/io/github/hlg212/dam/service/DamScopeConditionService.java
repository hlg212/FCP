package io.github.hlg212.dam.service;

import io.github.hlg212.dam.model.bo.DamScopeConditionBo;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;
import org.springframework.cache.annotation.CacheConfig;

/** 
 * DamScopeConditionService
 *
 * @author huangligui
 * @date 2021年06月01日
 */
@CacheConfig(cacheNames = "DataAuthorityScopeCondition")
public interface DamScopeConditionService extends CurdServiceImpl<DamScopeConditionBo> {
	
}
