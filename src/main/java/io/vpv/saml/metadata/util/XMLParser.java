package io.vpv.saml.metadata.util;


import org.w3c.dom.Node;

import static java.util.Objects.nonNull;

public class XMLParser {

//    static final Logger LOGGER = LoggerFactory.getLogger(XMLParser.class);

    public static String getAttributeValue(Node node, String attributeName) {
        String response = null;
        if (nonNull(node)
                && nonNull(node.getAttributes())
                && nonNull(node.getAttributes().getNamedItem(attributeName))
        ) {
            response = node.getAttributes().getNamedItem(attributeName).getNodeValue();
        }
        return response;
    }
    public static String stripNameSpace(String nodeName) {
        int index = nodeName.indexOf(":");
        if (index >= 0) { // Name spaced!
            return nodeName.substring(index + 1);
        }
        return nodeName;
    }

    public static String getXPath(Node node) {
        Node parent = node.getParentNode();
        if (parent == null) {
            return node.getNodeName();
        }
        return getXPath(parent) + "/" + node.getNodeName();
    }
}
