package io.vpv.saml.metadata.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

public class XMLParser {

    static final Logger LOGGER = LoggerFactory.getLogger(XMLParser.class);

    public static String getAttributeValue(Node node, String attributeName) {
        return node.getAttributes().getNamedItem(attributeName).getNodeValue();
    }
    public static String stripNameSpace(String nodeName) {
        int index = nodeName.indexOf(":");
        if (index >= 0) { // Name spaced!
            return nodeName.substring(index + 1);
        }
        return nodeName;
    }
}
