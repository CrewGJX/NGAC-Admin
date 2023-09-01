package com.phor.ngac.mapper;

import com.phor.ngac.entity.po.node.pc.PolicyClass;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends BaseRepository<PolicyClass, Long> {
    PolicyClass findByName(String name);
}
