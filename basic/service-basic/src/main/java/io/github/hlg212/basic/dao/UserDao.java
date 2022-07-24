package io.github.hlg212.basic.dao;

import io.github.hlg212.fcf.dao.BaseDao;
import io.github.hlg212.basic.model.po.User;
import org.springframework.stereotype.Component;

/** 
 * 用户Dao
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Component("USER_DATA")
public interface UserDao extends BaseDao<User> {
	
}
