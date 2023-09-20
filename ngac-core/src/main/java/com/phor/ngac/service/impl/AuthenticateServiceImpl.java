package com.phor.ngac.service.impl;

import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.core.pep.PolicyEnforcementPoint;
import com.phor.ngac.entity.dto.ColumnInfo;
import com.phor.ngac.entity.dto.DatabaseInfo;
import com.phor.ngac.entity.dto.TableInfo;
import com.phor.ngac.entity.vo.requests.AlterPermissionRequestVo;
import com.phor.ngac.entity.vo.requests.UserMenuAuthRequestVo;
import com.phor.ngac.entity.vo.requests.VisitTableColumnRequestVo;
import com.phor.ngac.entity.vo.requests.VisitTableDataRequestVo;
import com.phor.ngac.service.AuthenticateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                NodeEnum.getByName(alterPermissionRequestVo.getPermissionName()),
                CommonAccess.getMetaAccess(alterPermissionRequestVo.getPermissionType()).getType());
    }

    @Override
    public boolean visitTableColumn(VisitTableColumnRequestVo requestVo) {
//        return pep.enforcePolicy();
        return true;
    }

    @Override
    public boolean visitTableData(VisitTableDataRequestVo requestVo) {
//        return pep.enforcePolicy();
        return true;
    }

    @Override
    public List<DatabaseInfo> batchVisitTableColumn(String loginUserName, List<DatabaseInfo> databaseInfoList) {
        // 如果权限验证速度太慢，可以考虑使用redis缓存
        return databaseInfoList.stream().filter(databaseInfo -> { // 有tableInfo的
            String databaseName = databaseInfo.getDatabaseName();
            List<TableInfo> tableInfoList = databaseInfo.getTableInfos().stream().filter(tableInfo -> {
                String tableName = tableInfo.getTableName();
                List<ColumnInfo> columnInfoList = tableInfo.getColumnInfos().stream().map(columnInfo -> {
                    String columnName = columnInfo.getColumnName();
                    boolean access = pep.enforcePolicy(loginUserName, String.format("%s-%s-%s", databaseName, tableName, columnName), CommonAccess.READ.getType());
                    if (!access) {
                        return null;
                    }
                    return columnInfo;
                }).filter(Objects::nonNull).collect(Collectors.toList());
                tableInfo.setColumnInfos(columnInfoList);
                return CollectionUtils.isNotEmpty(columnInfoList);
            }).collect(Collectors.toList());
            databaseInfo.setTableInfos(tableInfoList);
            return CollectionUtils.isNotEmpty(tableInfoList);
        }).collect(Collectors.toList());
    }
}
