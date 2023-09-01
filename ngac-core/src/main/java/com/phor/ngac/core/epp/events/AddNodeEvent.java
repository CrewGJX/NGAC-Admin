package com.phor.ngac.core.epp.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddNodeEvent extends BaseEvent {
    private List<String> labels;
    private Map<String, Object> propertyMap;
}
