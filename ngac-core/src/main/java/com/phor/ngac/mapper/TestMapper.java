package com.phor.ngac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface TestMapper {
    @Select("select icc.imsi as imsi, icc.msisdn as msisdn, icd.imei as imei\n" +
            "from iop_cmp_card icc\n" +
            "         left join iop_cmp_card_dev iccd on icc.id = iccd.card_id\n" +
            "         left join iop_cmp_dev icd on iccd.dev_id = icd.id\n" +
            "where pn_name = 'L-5G-130-2021-10-1634105593190'\n" +
            "  and icd.imei is not null\n" +
            "  and icd.imei <> '';")
    Map<String, Object> testSql(@Param("testParam") String testParam);

    @Select("SELECT count(1) as count\n" +
            "FROM iop_cmp_card icc\n" +
            "         LEFT JOIN iop_cmp_card_dev iccd ON icc.id = iccd.card_id\n" +
            "         LEFT JOIN iop_cmp_dev icd ON iccd.dev_id = icd.id\n" +
            "WHERE icc.pn_name = 'L-5G-130-2021-10-1634105593190';")
    Map<String, Object> testCount(@Param("testParam") String testParam);

    @Select("select status_flag, count(1)\n" +
            "from wk_order_inst\n" +
            "where order_type = 'D'\n" +
            "  and order_id is not null\n" +
            "  and order_id <> ''\n" +
            "group by status_flag;")
    List<Map<String, Object>> testCount2(String testParam);
}
