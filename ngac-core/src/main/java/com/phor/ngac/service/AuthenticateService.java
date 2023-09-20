package com.phor.ngac.service;

import com.phor.ngac.entity.dto.DatabaseInfo;
import com.phor.ngac.entity.vo.requests.AlterPermissionRequestVo;
import com.phor.ngac.entity.vo.requests.UserMenuAuthRequestVo;
import com.phor.ngac.entity.vo.requests.VisitTableColumnRequestVo;
import com.phor.ngac.entity.vo.requests.VisitTableDataRequestVo;

import java.util.List;

public interface AuthenticateService {
    /**
     * 业务接口：访问菜单的鉴权
     *
     * @param userMenuAuthRequestVo 用户菜单鉴权请求
     * @return 是否有权限访问
     */
    boolean visitMenu(UserMenuAuthRequestVo userMenuAuthRequestVo);

    /**
     * 业务接口：修改权限（对权限的管理）的鉴权
     *
     * @param alterPermissionRequestVo 修改权限请求
     * @return 是否有权限修改
     */
    boolean alterPermission(AlterPermissionRequestVo alterPermissionRequestVo);

    /**
     * 业务接口：访问数据列的鉴权
     *
     * @return 是否有权限访问
     */
    boolean visitTableColumn(VisitTableColumnRequestVo requestVo);

    /**
     * 业务接口：访问数据行的鉴权
     *
     * @return 是否有权限访问
     */
    boolean visitTableData(VisitTableDataRequestVo requestVo);

    List<DatabaseInfo> batchVisitTableColumn(String loginUserName, List<DatabaseInfo> databaseInfoList);
}
