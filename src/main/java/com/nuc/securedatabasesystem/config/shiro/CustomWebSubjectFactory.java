package com.nuc.securedatabasesystem.config.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @Author DeepBlue
 * @Date 2020/11/19 16:13
 */
public class CustomWebSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 禁用session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}