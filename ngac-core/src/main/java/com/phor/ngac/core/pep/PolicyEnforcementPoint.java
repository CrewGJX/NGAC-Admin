package com.phor.ngac.core.pep;

import com.phor.ngac.consts.NodeEnum;

public interface PolicyEnforcementPoint {
    boolean enforcePolicy(String subject, String object, String action);

    boolean enforcePolicy(String subject, NodeEnum nodeEnum, String action);
}
