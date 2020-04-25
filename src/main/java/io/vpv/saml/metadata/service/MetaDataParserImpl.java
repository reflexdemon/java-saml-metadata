package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;

import static io.vpv.saml.metadata.util.XMLParser.getAttributeValue;

public class MetaDataParserImpl implements MetaDataParser {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public SPMetaData parseSPMetadata(InputStream xml) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document xmlDocument = dBuilder.parse(xml);

            SPMetaData spMetaData = SPMetaData.builder()
                    .entityID(getAttributeValue(xmlDocument.getFirstChild(), "entityID"))
                    .validUntil(getAttributeValue(xmlDocument.getFirstChild(), "validUntil"))
                    .cacheDuration(getAttributeValue(xmlDocument.getFirstChild(), "cacheDuration"))
                    .iD(getAttributeValue(xmlDocument.getFirstChild(), "ID"))
                    .build();

            for(int i =0; i < xmlDocument.getFirstChild().getChildNodes().getLength(); i++) {
                Node child = xmlDocument.getFirstChild().getChildNodes().item(i);
                LOGGER.debug(child.getNodeName() + "->'" + child.getNodeValue() + "'");
                String nodeName = stripNameSpace(child.getNodeName());
                switch (nodeName) {
                    case "Signature": spMetaData.setSignature(getSignatureInstance(child));
                                         break;

                }
            }

                return spMetaData;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String stripNameSpace(String nodeName) {
        int index = nodeName.indexOf(":");
        if (index >= 0) { // Name spaced!
            return nodeName.substring(index + 1);
        }
        return nodeName;
    }

    private Signature getSignatureInstance(Node signatureNode) {

        Signature signature
                = Signature.builder()
                .build();
        for(int i =0; i < signatureNode.getChildNodes().getLength(); i++) {
            Node child = signatureNode.getChildNodes().item(i);
            LOGGER.debug(child.getNodeName() + "->'" + child.getNodeValue() + "'");
            String nodeName = stripNameSpace(child.getNodeName());

            // SignedInfo
            switch (nodeName) {
                case "SignedInfo" :
                        signature.setSignedInfo(getSignedInfo(child));
                        break;
                case "SignatureValue" :
                        signature.setSignatureValue(child.getNodeValue());
                        break;
                case "KeyInfo" :
                        signature.setKeyInfo(getKeyInfo(child));
                        break;

            }
        }

        return signature;
    }

    private KeyInfo getKeyInfo(Node keyInfoNode) {
        throw new IllegalStateException("Unimplemented Method");
//        return null;
    }

    private SignedInfo getSignedInfo(Node signedInfoNode) {
        SignedInfo signedInfo = SignedInfo.builder().build();

        throw new IllegalStateException("Unimplemented Method");
//        return null;
    }

    @Override
    public IDPMetaData parseIDPMetaData(InputStream xml) {
        return null;
    }
}
