package com.phor.ngac.entity.dto;

import com.google.common.collect.Lists;
import com.phor.ngac.consts.CommonAccess;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class AccessRight {
    private String user;
    private List<CommonAccess> commonAccessList;
    private String object;
    private String policy;

    public static AccessRight emptyAccess() {
        return AccessRight.builder()
                .policy("")
                .commonAccessList(Lists.newArrayList()).build();
    }

    public static AccessRight fullAccess() {
        return AccessRight.builder()
                .policy("")
                .commonAccessList(Arrays.asList(CommonAccess.values())).build();
    }
}
