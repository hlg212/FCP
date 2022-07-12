package io.github.hlg212.basic.model.qco;

import io.github.hlg212.fcf.model.Qco;
import lombok.Data;

import java.util.List;

/** 
 * 应用-角色Qco
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class AppRoleQco extends Qco {
	
	private static final long serialVersionUID = 1L;

	private List<String> roleIdIn;


}
