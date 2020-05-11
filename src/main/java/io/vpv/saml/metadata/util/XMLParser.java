package io.vpv.saml.metadata.util;


import io.vpv.saml.metadata.xml.modal.EntityDescriptorType;
import io.vpv.saml.metadata.xml.modal.ReferenceList;
import org.w3c.dom.Node;

import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import java.io.File;
import java.io.InputStream;

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

    public static EntityDescriptorType parseXML(InputStream inputStream) {
        EntityDescriptorType referenceList = null;
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(EntityDescriptorType.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            referenceList = (EntityDescriptorType) JAXBIntrospector.getValue(unmarshaller.unmarshal(inputStream));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return referenceList;
    }
}
