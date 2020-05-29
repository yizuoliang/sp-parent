package org.example.sp.business.system.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.example.sp.business.system.shiro.ShiroRealm;
import org.example.sp.business.system.shiro.filter.SpAuthenticatingFilter;
import org.example.sp.common.constant.ShiroConstant;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Author: yizl
 * @Date: 2020/5/28
 * @Description:
 */
@Configuration
public class ShiroConfig{

    @Bean
    public ShiroRealm shiroRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {

        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroRealm;
    }
    /**
     * 不指定名字的话，自动创建一个方法名 :第一个字母小写的bean
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }
    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Shiro的Web过滤器Factory
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置拦截器,实现无权限返回,无权限result,而不是跳转到登录页index.jsp
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new SpAuthenticatingFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/login/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     *  凭证匹配器(注册到自定义的Realm中)
     *  在认证时,就会按照下面方式进行认证(说白了,就是将登陆的密码,按照下面方式进行加密,在和数据库的对比)
     *
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法:MD5
        credentialsMatcher.setHashAlgorithmName(ShiroConstant.ALGORITHM_NAME);
        // MD5加密次数
        credentialsMatcher.setHashIterations(ShiroConstant.ENCODE_COUNT);
        return credentialsMatcher;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        return definition;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,用注解的方式进行权限验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
