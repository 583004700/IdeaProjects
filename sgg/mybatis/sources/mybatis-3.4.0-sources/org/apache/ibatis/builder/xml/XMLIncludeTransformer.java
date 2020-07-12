/**
 *    Copyright 2009-2015 the original author or authors.
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
package org.apache.ibatis.builder.xml;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Properties;

/**
 * @author Frank D. Martinez [mnesarco]
 */
public class XMLIncludeTransformer {

  private final Configuration configuration;
  private final MapperBuilderAssistant builderAssistant;

  public XMLIncludeTransformer(Configuration configuration, MapperBuilderAssistant builderAssistant) {
    this.configuration = configuration;
    this.builderAssistant = builderAssistant;
  }

  public void applyIncludes(Node source) {
    Properties variablesContext = new Properties();
    Properties configurationVariables = configuration.getVariables();
    if (configurationVariables != null) {
      variablesContext.putAll(configurationVariables);
    }
    applyIncludes(source, variablesContext);
  }

  /**
   * Recursively apply includes through all SQL fragments.
   * @param source Include node in DOM tree
   * @param variablesContext Current context for static variables with values
   */
  /**
   *
   * @param source 通常是增删查改标签，或者sql标签
   * @param variablesContext 从当中获取配置信息
   */
  private void applyIncludes(Node source, final Properties variablesContext) {
    if (source.getNodeName().equals("include")) {
      // new full context for included SQL - contains inherited context and new variables from current include node
      //将variablesContext和将include中的子标签中的name,value组织成键值对形式的属性整合
      Properties fullContext;

      String refid = getStringAttribute(source, "refid");
      // replace variables in include refid value
      refid = PropertyParser.parse(refid, variablesContext);
      //查找引用的sql片段node
      Node toInclude = findSqlFragment(refid);
      //将include中的子标签中的name,value组织成键值对形式，variablesContext代表配置
      Properties newVariablesContext = getVariablesContext(source, variablesContext);

      if (!newVariablesContext.isEmpty()) {
        // merge contexts
        fullContext = new Properties();
        fullContext.putAll(variablesContext);
        fullContext.putAll(newVariablesContext);
      } else {
        // no new context - use inherited fully
        fullContext = variablesContext;
      }
      //从fullContext取属性替换sql标签内的${val}形式的内容
      applyIncludes(toInclude, fullContext);

      if (toInclude.getOwnerDocument() != source.getOwnerDocument()) {
        //将节点从一个文档复制到另一个文档
        toInclude = source.getOwnerDocument().importNode(toInclude, true);
      }
      //将source节点替换为toInclude节点，将include标签的内容替换为所引用的标签的内容
      source.getParentNode().replaceChild(toInclude, source);
      while (toInclude.hasChildNodes()) {
        //将sql标签内的内容移到外面
        toInclude.getParentNode().insertBefore(toInclude.getFirstChild(), toInclude);
      }
      //最后将sql标签移除
      toInclude.getParentNode().removeChild(toInclude);
    } else if (source.getNodeType() == Node.ELEMENT_NODE) {
      NodeList children = source.getChildNodes();
      for (int i=0; i<children.getLength(); i++) {
        applyIncludes(children.item(i), variablesContext);
      }
    } else if (source.getNodeType() == Node.ATTRIBUTE_NODE && !variablesContext.isEmpty()) {
      // replace variables in all attribute values
      source.setNodeValue(PropertyParser.parse(source.getNodeValue(), variablesContext));
    } else if (source.getNodeType() == Node.TEXT_NODE && !variablesContext.isEmpty()) {
      // replace variables ins all text nodes
      source.setNodeValue(PropertyParser.parse(source.getNodeValue(), variablesContext));
    }
  }

  /**
   * 得到引用的sql片段的node，并复制返回
   * @param refid
   * @return
   */
  private Node findSqlFragment(String refid) {
    refid = builderAssistant.applyCurrentNamespace(refid, true);
    try {
      XNode nodeToInclude = configuration.getSqlFragments().get(refid);
      return nodeToInclude.getNode().cloneNode(true);
    } catch (IllegalArgumentException e) {
      throw new IncompleteElementException("Could not find SQL statement to include with refid '" + refid + "'", e);
    }
  }

  private String getStringAttribute(Node node, String name) {
    return node.getAttributes().getNamedItem(name).getNodeValue();
  }

  /**
   * Read placholders and their values from include node definition. 
   * @param node Include node instance
   * @param inheritedVariablesContext Current context used for replace variables in new variables values
   * @return variables context from include instance (no inherited values)
   */
  /**
   * 将source中的子标签中的name,value组织成键值对形式，variablesContext代表配置
   */
  private Properties getVariablesContext(Node node, Properties inheritedVariablesContext) {
    Properties variablesContext = new Properties();
    NodeList children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node n = children.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE) {
        String name = getStringAttribute(n, "name");
        String value = getStringAttribute(n, "value");
        // Replace variables inside
        value = PropertyParser.parse(value, inheritedVariablesContext);
        // Push new value
        Object originalValue = variablesContext.put(name, value);
        if (originalValue != null) {
          throw new BuilderException("Variable " + name + " defined twice in the same include definition");
        }
      }
    }
    return variablesContext;
  }
  
}
