package com.phor.ngac.pep;

public interface PolicyEnforcementPoint {
    public boolean enforcePolicy(String subject, String resource, String action);
}
