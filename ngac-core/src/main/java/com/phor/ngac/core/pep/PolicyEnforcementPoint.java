package com.phor.ngac.core.pep;

public interface PolicyEnforcementPoint {
    boolean enforcePolicy(String subject, String resource, String action);
}
