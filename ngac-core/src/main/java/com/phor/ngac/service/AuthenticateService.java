package com.phor.ngac.service;

import com.phor.ngac.entity.vo.requests.AlterPermissionRequestVo;
import com.phor.ngac.entity.vo.requests.UserMenuAuthRequestVo;

public interface AuthenticateService {
    boolean visitMenu(UserMenuAuthRequestVo userMenuAuthRequestVo);

    boolean alterPermission(AlterPermissionRequestVo alterPermissionRequestVo);
}
