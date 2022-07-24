package io.github.hlg212.basic.dao;

import io.github.hlg212.fcf.dao.BaseDao;
import io.github.hlg212.basic.model.po.App;
import org.springframework.stereotype.Component;

/** 
 * 应用Dao
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Component("basic.AppDao")
public interface AppDao extends BaseDao<App> {
	
}
