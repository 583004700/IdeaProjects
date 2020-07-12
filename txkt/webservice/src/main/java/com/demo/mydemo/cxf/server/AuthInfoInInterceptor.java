package com.demo.mydemo.cxf.server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

import java.util.List;

public class AuthInfoInInterceptor extends AbstractPhaseInterceptor<SoapMessage>{

    public AuthInfoInInterceptor(){
        super(Phase.PRE_INVOKE);//接收后，执行前
    }

    //从soap头中获取username和password
    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        List<Header> headers = soapMessage.getHeaders();
        if(headers == null || headers.size() < 1){
            throw new Fault(new IllegalArgumentException("请输入用户信息"));
        }
        Header authInfo = headers.get(0);
        Element authInfoEle = (Element) authInfo.getObject();
        String userName = authInfoEle.getElementsByTagName("userName").item(0).getTextContent();
        String password = authInfoEle.getElementsByTagName("password").item(0).getTextContent();
        System.out.println(userName);
        System.out.println(password);
    }
}
