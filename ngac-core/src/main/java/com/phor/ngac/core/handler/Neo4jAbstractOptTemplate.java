package com.phor.ngac.core.handler;

import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.core.handler.core.AccessRightOptTemplate;
import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.entity.po.node.pc.PolicyClass;
import com.phor.ngac.exception.PolicyClassException;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.Path;

import java.util.List;

/**
 * neo4j相关操作抽象模板类
 *
 * @date 2023/8/11
 */
@Slf4j
public abstract class Neo4jAbstractOptTemplate extends AccessRightOptTemplate {
    protected AccessRight getAccessRightFromRecord(Record resultRecord) {
        // 获取所有superPolicyClass到resource的通路
        List<Path> pcPath = resultRecord.get("pc_path").asList(Value::asPath);
        log.debug("pcPath: {}", pcPath);

        // 计算user_path和pc_path的交集，并从subject延伸出来，获取所有授权ar
        List<Path> arPath = resultRecord.get("ar_path").asList(Value::asPath);
        log.debug("arPath: {}", arPath);

        List<PolicyClass> policyClassNodeFromPath = getPolicyClassNodeFromPath(pcPath);

        PolicyClass policyClass = policyClassNodeFromPath.stream().findFirst().orElseThrow(() -> new PolicyClassException("未找到有效的pc策略"));

        List<CommonAccess> commonAccessListFromPath = getAccessListFromPath(arPath);
        log.debug("commonAccessListFromPath: {}", commonAccessListFromPath);
        AccessRight.AccessRightBuilder builder = AccessRight.builder();
        builder.policy(policyClass.getName())
                .commonAccessList(commonAccessListFromPath);
        return builder.build();
    }
}
