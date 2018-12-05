package com.cloud.gateway.support;
import com.cloud.gateway.api.RibbonFilterContext;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月30日
 */
public class RibbonFilterContextHolder {

    /**
     * Stores the {@link RibbonFilterContext} for current thread.
     */
    private final ThreadLocal<RibbonFilterContext> currentContext = new InheritableThreadLocal<RibbonFilterContext>() {
        @Override
        protected RibbonFilterContext initialValue() {
            return new DefaultRibbonFilterContext();
        }
    };

    /**
     * Retrieves the current thread bound instance of {@link RibbonFilterContext}.
     *
     * @return the current context
     */
    public RibbonFilterContext getCurrentContext() {
        return currentContext.get();
    }

    /**
     * Clears the current context.
     */
    public void clearCurrentContext() {
        currentContext.remove();
    }
}
