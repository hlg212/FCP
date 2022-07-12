package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.model.Qco;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** 
 * 角色资源Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class RoleResQco extends Qco {
	
	private static final long serialVersionUID = 1L;

	private  String roleId;

	private  List<String> roleIdIn;

	private  String resType;

	private List<String> resIdIn;
}
