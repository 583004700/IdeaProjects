/**
 *    Copyright 2009-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.builder.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

/**
 * @author Clinton Begin
 * @author Kazuki Shimizu
 */
public class ProviderSqlSource implements SqlSource {

  private SqlSourceBuilder sqlSourceParser;
  //注解指向的类
  private Class<?> providerType;
  //注解指向的满足条件的方法
  private Method providerMethod;
  //方法参数名 [param1,param2,id]这种形式
  private String[] providerMethodArgumentNames;

  public ProviderSqlSource(Configuration config, Object provider) {
    String providerMethodName = null;
    try {
      this.sqlSourceParser = new SqlSourceBuilder(config);
      this.providerType = (Class<?>) provider.getClass().getMethod("type").invoke(provider);
      providerMethodName = (String) provider.getClass().getMethod("method").invoke(provider);

      for (Method m : this.providerType.getMethods()) {
        if (providerMethodName.equals(m.getName())) {
          if (m.getReturnType() == String.class) {
            if (providerMethod != null){
              throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
                      + providerMethodName + "' is found multiple in SqlProvider '" + this.providerType.getName()
                      + "'. Sql provider method can not overload.");
            }
            this.providerMethod = m;
            this.providerMethodArgumentNames = extractProviderMethodArgumentNames(m);
          }
        }
      }
    } catch (BuilderException e) {
      throw e;
    } catch (Exception e) {
      throw new BuilderException("Error creating SqlSource for SqlProvider.  Cause: " + e, e);
    }
    if (this.providerMethod == null) {
      throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
          + providerMethodName + "' not found in SqlProvider '" + this.providerType.getName() + "'.");
    }
  }

  @Override
  public BoundSql getBoundSql(Object parameterObject) {
    SqlSource sqlSource = createSqlSource(parameterObject);
    return sqlSource.getBoundSql(parameterObject);
  }

  /**
   * 执行注解指向的方法，返回的字符串创建sqlSource
   * @param parameterObject 调用接口方法时具体的参数
   * @return
   */
  private SqlSource createSqlSource(Object parameterObject) {
    try {
      Class<?>[] parameterTypes = providerMethod.getParameterTypes();
      String sql;
      if (parameterTypes.length == 0) {
        sql = (String) providerMethod.invoke(providerType.newInstance());
      } else if (parameterTypes.length == 1 &&
              (parameterObject == null || parameterTypes[0].isAssignableFrom(parameterObject.getClass()))) {
        sql = (String) providerMethod.invoke(providerType.newInstance(), parameterObject);
      } else if (parameterObject instanceof Map) {
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) parameterObject;
        sql = (String) providerMethod.invoke(providerType.newInstance(), extractProviderMethodArguments(params, providerMethodArgumentNames));
      } else {
        throw new BuilderException("Error invoking SqlProvider method ("
                + providerType.getName() + "." + providerMethod.getName()
                + "). Cannot invoke a method that holds "
                + (parameterTypes.length == 1 ? "named argument(@Param)": "multiple arguments")
                + " using a specifying parameterObject. In this case, please specify a 'java.util.Map' object.");
      }
      Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
      return sqlSourceParser.parse(sql, parameterType, new HashMap<String, Object>());
    } catch (BuilderException e) {
      throw e;
    } catch (Exception e) {
      throw new BuilderException("Error invoking SqlProvider method ("
          + providerType.getName() + "." + providerMethod.getName()
          + ").  Cause: " + e, e);
    }
  }

  /**
   * 得到参数[param1,parma2,id]  形式
   * @param providerMethod
   * @return
   */
  private String[] extractProviderMethodArgumentNames(Method providerMethod) {
    String[] argumentNames = new String[providerMethod.getParameterTypes().length];
    for (int i = 0; i < argumentNames.length; i++) {
      Param param = findParamAnnotation(providerMethod, i);
      argumentNames[i] = param != null ? param.value() : "param" + (i + 1);
    }
    return argumentNames;
  }

  private Param findParamAnnotation(Method providerMethod, int parameterIndex) {
    final Object[] annotations = providerMethod.getParameterAnnotations()[parameterIndex];
    Param param = null;
    for (Object annotation : annotations) {
      if (annotation instanceof Param) {
        param = Param.class.cast(annotation);
        break;
      }
    }
    return param;
  }

  private Object[] extractProviderMethodArguments(Map<String, Object> params, String[] argumentNames) {
    Object[] args = new Object[argumentNames.length];
    for (int i = 0; i < args.length; i++) {
      args[i] = params.get(argumentNames[i]);
    }
    return args;
  }

}
