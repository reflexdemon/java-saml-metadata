package io.vpv.saml.metadata.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.*;
import java.util.Iterator;
import java.util.Objects;

public class XMLParser {

    static final Logger LOGGER = LoggerFactory.getLogger(XMLParser.class);

    // Namespaces
    public static String NS_SAML = "urn:oasis:names:tc:SAML:2.0:assertion";
    public static String NS_SAMLP = "urn:oasis:names:tc:SAML:2.0:protocol";
    public static String NS_SOAP = "http://schemas.xmlsoap.org/soap/envelope/";
    public static String NS_MD = "urn:oasis:names:tc:SAML:2.0:metadata";
    public static String NS_XS = "http://www.w3.org/2001/XMLSchema";
    public static String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static String NS_XENC = "http://www.w3.org/2001/04/xmlenc#";
    public static String NS_DS = "http://www.w3.org/2000/09/xmldsig#";

    public static NodeList getNodeList(Document doc, String expression) throws XPathExpressionException {
        final XPath xPath =  getXPathFactory().newInstance().newXPath();
        final NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
                doc, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            LOGGER.debug(nodeList.item(i).getNodeValue());
        }
        return nodeList;
    }

    public static String getNodeValue(Document doc, String expression) throws XPathExpressionException {
        NodeList nodeList = getNodeList(doc, expression);
        if (Objects.nonNull(nodeList) && nodeList.getLength() > 0) {
            return nodeList.item(0).getNodeValue();
        }
        return null;
    }
    private static XPathFactory getXPathFactory() {
        try {
            /*
             * Since different environments may return a different XPathFactoryImpl, we should try to initialize the factory
             * using specific implementation that way the XML is parsed in an expected way.
             *
             * We should use the standard XPathFactoryImpl that comes standard with Java.
             *
             * NOTE: We could implement a check to see if the "javax.xml.xpath.XPathFactory" System property exists and is set
             *       to a value, if people have issues with using the specified implementor. This would allow users to always
             *       override the implementation if they so need to.
             */
            return XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI, "com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl", ClassLoader.getSystemClassLoader());
        } catch (XPathFactoryConfigurationException e) {
            LOGGER.debug("Error generating XPathFactory instance: " + e.getMessage(), e);
        }

        /*
         * If the expected XPathFactory did not exist, we fallback to loading the one defined as the default.
         *
         * If this is still throwing an error, the developer can set the "javax.xml.xpath.XPathFactory" system property
         * to specify the default XPathFactoryImpl implementation to use. For example:
         *
         * -Djavax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom=net.sf.saxon.xpath.XPathFactoryImpl
         * -Djavax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom=com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl
         *
         */
        return XPathFactory.newInstance();
    }
    /**
     * Extracts a node from the DOMDocument
     *
     * @param dom
     * 				The DOMDocument
     * @param query
     * 				Xpath Expression
     * @param context
     * 				Context Node (DomElement)
     *
     * @return DOMNodeList The queried node
     *
     * @throws XPathExpressionException
     */
    public static NodeList query(Document dom, String query, Node context) throws XPathExpressionException {
        NodeList nodeList;
        XPath xpath = getXPathFactory().newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {

            @Override
            public String getNamespaceURI(String prefix) {
                String result = null;
                if (prefix.equals("samlp") || prefix.equals("samlp2")) {
                    result = NS_SAMLP;
                } else if (prefix.equals("saml") || prefix.equals("saml2")) {
                    result = NS_SAML;
                } else if (prefix.equals("ds")) {
                    result = NS_DS;
                } else if (prefix.equals("xenc")) {
                    result = NS_XENC;
                } else if (prefix.equals("md")) {
                    result = NS_MD;
                }
                return result;
            }

            @Override
            public String getPrefix(String namespaceURI) {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            public Iterator getPrefixes(String namespaceURI) {
                return null;
            }
        });

        if (context == null) {
            nodeList = (NodeList) xpath.evaluate(query, dom, XPathConstants.NODESET);
        } else {
            nodeList = (NodeList) xpath.evaluate(query, context, XPathConstants.NODESET);
        }
        return nodeList;
    }

    /**
     * Extracts a node from the DOMDocument
     *
     * @param dom
     * 				The DOMDocument
     * @param query
     * 				Xpath Expression
     *
     * @return DOMNodeList The queried node
     *
     * @throws XPathExpressionException
     */
    public static NodeList query(Document dom, String query) throws XPathExpressionException {
        return query(dom, query, null);
    }

    public static String getAttributeValue(Node node, String attributeName) {
        return node.getAttributes().getNamedItem(attributeName).getNodeValue();
    }
}
