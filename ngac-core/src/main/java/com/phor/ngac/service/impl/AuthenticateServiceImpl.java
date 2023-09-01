package com.phor.ngac.service.impl;

import com.google.common.collect.Lists;
import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.core.pep.PolicyEnforcementPoint;
import com.phor.ngac.entity.vo.requests.AlterPermissionRequestVo;
import com.phor.ngac.entity.vo.requests.UserMenuAuthRequestVo;
import com.phor.ngac.service.AuthenticateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    @Resource
    private PolicyEnforcementPoint pep;

    @Override
    public boolean visitMenu(UserMenuAuthRequestVo userMenuAuthRequestVo) {
        return pep.enforcePolicy(userMenuAuthRequestVo.getName(), userMenuAuthRequestVo.getMenu(), CommonAccess.READ.getType());
    }

    @Override
    public boolean alterPermission(AlterPermissionRequestVo alterPermissionRequestVo) {
        return pep.enforcePolicy(alterPermissionRequestVo.getLoginUserName(),
                NodeEnum.getByLabel(Lists.newArrayList(alterPermissionRequestVo.getPermissionName())),
                CommonAccess.getMetaAccess(alterPermissionRequestVo.getPermissionType()).getType());
    }
}
