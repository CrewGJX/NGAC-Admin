package com.phor.ngac.mapper;

import com.phor.ngac.entity.po.node.u.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByName(String name);
}
