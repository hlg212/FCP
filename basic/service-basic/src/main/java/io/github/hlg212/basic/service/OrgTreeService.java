package io.github.hlg212.basic.service;

import io.github.hlg212.basic.model.bo.OrgTreeBo;
import io.github.hlg212.basic.model.bo.OrgTreeParam;
import io.github.hlg212.basic.model.bo.OrgTreeSaveBo;
import io.github.hlg212.basic.model.qco.OrgTreeQco;
import io.github.hlg212.fcf.annotation.RequestParamOrBody;
import io.github.hlg212.fcf.service.impl.CurdServiceImpl;
import io.github.hlg212.fcf.service.impl.CurdieServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/** 
 * 机构树Service
 *
 * @author huanglg
 * @date 2022-03-28
 */
public interface OrgTreeService extends CurdieServiceImpl<OrgTreeBo> {

    void saveTree(@RequestBody OrgTreeSaveBo bo);

    List<OrgTreeBo> findTree(@RequestParamOrBody OrgTreeQco qco);


    /**
     * 获取机构树子节点,集合方式返回
     * @param orgTreeTypeCode 机构树类型编码
     * @param orgId 机构id
     * @param childLevel 子级的深度,不填就是所有
     * @return 子机构列表
     */
    List<OrgTreeBo> getChildList(OrgTreeParam param);

    /**
     * 获取机构树子节点,树方式返回
     * @param orgTreeTypeCode 机构树类型编码
     * @param orgId 机构id
     * @param childLevel 子级的深度,不填就是所有
     * @return 子机构树
     */
    List<OrgTreeBo> getChildTree(OrgTreeParam param);

    /**
     * 获取机构父机构列表
     *
     * @param orgTreeTypeCode 机构树类型编码
     * @param orgId 机构id
     * @param childLevel 父级的(向上的)深度,不填就是所有
     * @return 父机构列表
     */
    List<OrgTreeBo> getParentList(OrgTreeParam param);

    /**
     * 获取机构父机构树
     *
     * @param orgTreeTypeCode 机构树类型编码
     * @param orgId 机构id
     * @param childLevel 父级的(向上的)深度,不填就是所有
     * @return 父机构树
     */
    List<OrgTreeBo> getParentTree(OrgTreeParam param);

    /**
     * 获取机构父机构
     *
     * @param orgTreeTypeCode 机构树类型编码
     * @param orgId 机构id
     * @return 父机构
     */
    OrgTreeBo getParent(String orgTreeTypeCode,String orgId);
}
