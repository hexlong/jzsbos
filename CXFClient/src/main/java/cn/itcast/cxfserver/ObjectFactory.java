
package cn.itcast.cxfserver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.itcast.cxfserver package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetInfoByCityName_QNAME = new QName("http://cxfserver.itcast.cn/", "getInfoByCityName");
    private final static QName _GetInfoByCityNameResponse_QNAME = new QName("http://cxfserver.itcast.cn/", "getInfoByCityNameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.itcast.cxfserver
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInfoByCityNameResponse }
     * 
     */
    public GetInfoByCityNameResponse createGetInfoByCityNameResponse() {
        return new GetInfoByCityNameResponse();
    }

    /**
     * Create an instance of {@link GetInfoByCityName }
     * 
     */
    public GetInfoByCityName createGetInfoByCityName() {
        return new GetInfoByCityName();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoByCityName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxfserver.itcast.cn/", name = "getInfoByCityName")
    public JAXBElement<GetInfoByCityName> createGetInfoByCityName(GetInfoByCityName value) {
        return new JAXBElement<GetInfoByCityName>(_GetInfoByCityName_QNAME, GetInfoByCityName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoByCityNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxfserver.itcast.cn/", name = "getInfoByCityNameResponse")
    public JAXBElement<GetInfoByCityNameResponse> createGetInfoByCityNameResponse(GetInfoByCityNameResponse value) {
        return new JAXBElement<GetInfoByCityNameResponse>(_GetInfoByCityNameResponse_QNAME, GetInfoByCityNameResponse.class, null, value);
    }

}
