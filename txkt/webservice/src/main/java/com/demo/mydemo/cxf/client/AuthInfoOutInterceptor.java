package com.demo.mydemo.cxf.client;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.namespace.QName;

public class AuthInfoOutInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String name;
    private String password;
    public AuthInfoOutInterceptor(){
        super(Phase.PREPARE_SEND);
        this.name = name;
        this.password = password;
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        QName q = new QName("yhptest");
        Document document = DOMUtils.createDocument();
        Element authInfoEle = document.createElement("authInfo");
        Element usernameElement = document.createElement("userName");
        usernameElement.setTextContent("name");
        authInfoEle.appendChild(usernameElement);
        Element passwordElement = document.createElement("password");
        passwordElement.setTextContent("password");
        authInfoEle.appendChild(passwordElement);
        Header authInHeader = new Header(q,authInfoEle);
        try {
            soapMessage.getHeaders().add(authInHeader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
