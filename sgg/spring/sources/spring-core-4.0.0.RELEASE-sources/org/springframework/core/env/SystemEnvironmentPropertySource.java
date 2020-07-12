package org.springframework.core.env;

import java.util.Map;
import org.springframework.util.Assert;

/**
 * 系统环境信息的PropertySource
 */
public class SystemEnvironmentPropertySource extends MapPropertySource {


    public SystemEnvironmentPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }


    @Override
    public boolean containsProperty(String name) {
        return getProperty(name) != null;
    }


    @Override
    public Object getProperty(String name) {
        Assert.notNull(name, "property name must not be null");
        String actualName = resolvePropertyName(name);
        if (logger.isDebugEnabled() && !name.equals(actualName)) {
            logger.debug(String.format(
                    "PropertySource [%s] does not contain '%s', but found equivalent '%s'",
                    this.getName(), name, actualName));
        }
        return super.getProperty(actualName);
    }


    private String resolvePropertyName(String name) {
        if (super.containsProperty(name)) {
            return name;
        }

        String usName = name.replace('.', '_');
        if (!name.equals(usName) && super.containsProperty(usName)) {
            return usName;
        }

        String ucName = name.toUpperCase();
        if (!name.equals(ucName)) {
            if (super.containsProperty(ucName)) {
                return ucName;
            } else {
                String usUcName = ucName.replace('.', '_');
                if (!ucName.equals(usUcName) && super.containsProperty(usUcName)) {
                    return usUcName;
                }
            }
        }

        return name;
    }
}
