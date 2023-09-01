package com.phor.ngac.core.epp.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeleteNodeEvent extends BaseEvent {
    private List<String> labels;

    private Long id;

    private String name;
}
