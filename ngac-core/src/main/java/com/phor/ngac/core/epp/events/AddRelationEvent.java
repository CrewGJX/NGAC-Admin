package com.phor.ngac.core.epp.events;

import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.CommonRelation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AddRelationEvent extends BaseEvent {
    private CommonNode source;
    private CommonNode target;
    private CommonRelation relation;
}
