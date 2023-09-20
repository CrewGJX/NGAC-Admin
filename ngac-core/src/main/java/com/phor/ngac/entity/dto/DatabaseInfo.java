package com.phor.ngac.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class DatabaseInfo {
    private String databaseName;

    private List<TableInfo> tableInfos;
}
