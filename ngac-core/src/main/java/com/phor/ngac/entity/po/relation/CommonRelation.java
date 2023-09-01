package com.phor.ngac.entity.po.relation;

import lombok.Data;

import java.util.Map;

@Data
public class CommonRelation implements BaseRelation {
    private String type;
    private Long id;
    private Map<String, Object> propertyMap;
}
