package com.phor.ngac.core.handler.core;

import cn.hutool.core.stream.StreamUtil;
import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.entity.dto.AccessRight;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Slf4j
public class AccessRightOptTemplate extends PathOptTemplate implements AccessRightOptHandler {
    @Override
    public AccessRight intersection(AccessRight a, AccessRight b) {
        return createBean(a, b, ListUtils::intersection);
    }

    @Override
    public AccessRight union(AccessRight a, AccessRight b) {
        return createBean(a, b, ListUtils::union);
    }

    @Override
    public AccessRight difference(AccessRight a, AccessRight b) {
        return createBean(a, b, ListUtils::subtract);
    }

    @Override
    public boolean isArContainsAction(AccessRight ar, String action) {
        return ar.getCommonAccessList().stream().anyMatch(access -> access.getType().equals(action.toLowerCase()));
    }

    private AccessRight createBean(AccessRight a, AccessRight b, BinaryOperator<List<CommonAccess>> biFunction) {
        AccessRight.AccessRightBuilder newAR = AccessRight.builder();
        List<CommonAccess> oneAl = a.getCommonAccessList();
        List<CommonAccess> antAl = b.getCommonAccessList();

        List<CommonAccess> result = biFunction.apply(oneAl, antAl);
        newAR.policy(joinPolicyName(a.getPolicy(), b.getPolicy()))
                .commonAccessList(result);
        return newAR.build();
    }

    private String joinPolicyName(String pcNameA, String pcNameB) {
        String pcName = StreamUtil.of(pcNameA, pcNameB).distinct().collect(Collectors.joining(","));
        return pcName.replaceAll("(^,)|(,$)", "");
    }
}
