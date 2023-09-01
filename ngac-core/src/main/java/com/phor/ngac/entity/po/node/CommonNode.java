package com.phor.ngac.entity.po.node;

import lombok.Data;

import java.util.List;

@Data
public class CommonNode implements BaseNode {
    private Long id;

    private String name;

    private List<String> labels;
}
