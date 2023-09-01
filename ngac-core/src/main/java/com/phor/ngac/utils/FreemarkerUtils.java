package com.phor.ngac.utils;

import com.phor.ngac.exception.FreeMarkerException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

/**
 * freemarker 模板工具类
 *
 * @author gongjx12
 * @date 2022/6/18
 */
@Slf4j
@Component
public class FreemarkerUtils {
    private static Configuration configuration;

    @Autowired
    FreemarkerUtils(Configuration configuration) {
        FreemarkerUtils.configuration = configuration;
    }

    /**
     * 根据模板和数据生成内容字符串
     *
     * @param path   模板路径
     * @param params 数据
     * @return 内容字符串
     */
    public static String getContentByTemplatePath(String path, Map<String, Object> params) {
        if (StringUtils.isEmpty(path)) {
            log.error("路径path不能为空");
            return "";
        }

        if (null == params) {
            log.error("参数params不能为空");
            return "";
        }

        if (null == configuration) {
            log.error("freemarker configuration注入失败");
            return "";
        }

        try {
            // 使用springboot自带的freemarkerUtils构建模板
            Template template = configuration.getTemplate(path);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (Exception e) {
            log.error("获取模板内容失败", e);
            throw new FreeMarkerException("获取模板内容失败", e);
        }
    }
}
