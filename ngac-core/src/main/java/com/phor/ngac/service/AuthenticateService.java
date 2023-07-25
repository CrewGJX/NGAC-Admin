package com.phor.ngac.service;

public interface AuthenticateService {
    boolean visitResource(String name, String resource, String action);

    String findUser(String name);
}
