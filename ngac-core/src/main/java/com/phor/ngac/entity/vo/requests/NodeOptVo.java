package com.phor.ngac.entity.vo.requests;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel("添加节点")
@Deprecated
public class NodeOptVo {
    private String name;
    private Long id;
    private List<String> label;
    private Map<String, Object> property;
}
