package com.phor.ngac.core.pip;

import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.entity.po.node.u.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MysqlPip implements PolicyInformationPoint {
    @Override
    public Optional<User> findUserAndRelations(String name) {
        return Optional.empty();
    }

    @Override
    public List<AccessRight> getAccessRights(String subject, String resource) {
        return null;
    }

    @Override
    public List<AccessRight> getProhibitions(String subject, String resource) {
        return null;
    }
}
