package com.phor.ngac.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class TableInfo {
    private String tableName;

    private List<ColumnInfo> columnInfos;
}
