package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.*;
import io.vpv.saml.metadata.xml.modal.EntityDescriptorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static io.vpv.saml.metadata.util.XMLParser.*;
import static org.apache.commons.lang3.StringUtils.trimToNull;

public class MetaDataParserImpl implements MetaDataParser {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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
                    .contactPerson(new ArrayList<>())
                    .build();

            for (int i = 0; i < xmlDocument.getFirstChild().getChildNodes().getLength(); i++) {
                Node child = xmlDocument.getFirstChild().getChildNodes().item(i);
                String nodeName = stripNameSpace(child.getNodeName());
                switch (nodeName) {
                    case "Signature":
                        spMetaData.setSignature(getSignatureInstance(child));
                        break;
                    case "SPSSODescriptor":
                        spMetaData.setSPSSODescriptor(getSPSSODescriptor(child));
                        break;
                    case "Organization":
                        spMetaData.setOrganization(getOrganization(child));
                        break;
                    case "ContactPerson":
                        spMetaData.getContactPerson().add(getContactPerson(child));
                        break;
                    default:
                        logNode(child);
                        break;

                }
            }

            return spMetaData;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error("Unable ot parse the document due to error", e);
            throw new RuntimeException("Unable ot parse the document due to error", e);
        }
    }

    private ContactPerson getContactPerson(Node contactPersonNode) {
        ContactPerson contactPerson = ContactPerson.builder()
                .contactType(getAttributeValue(contactPersonNode, "contactType"))
                .build();

        for (int i = 0; i < contactPersonNode.getChildNodes().getLength(); i++) {
            Node child = contactPersonNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "GivenName":
                    contactPerson.setGivenName(child.getTextContent());
                    break;
                case "EmailAddress":
                    contactPerson.setEmailAddress(child.getTextContent());
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return contactPerson;
    }

    private Organization getOrganization(Node organizationNode) {
        Organization organization = Organization.builder().build();
        for (int i = 0; i < organizationNode.getChildNodes().getLength(); i++) {
            Node child = organizationNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "OrganizationName":
                    organization.setOrganizationName(getOrganizationName(child));
                    break;
                case "OrganizationDisplayName":
                    organization.setOrganizationDisplayName(getOrganizationDisplayName(child));
                    break;
                case "OrganizationURL":
                    organization.setOrganizationURL(getOrganizationURL(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return organization;
    }

    private OrganizationDisplayName getOrganizationDisplayName(Node organizationDisplayNameNode) {

        return OrganizationDisplayName.builder()
                .lang(getAttributeValue(organizationDisplayNameNode, "xml:lang"))
                .value(organizationDisplayNameNode.getTextContent())
                .build();
    }

    private OrganizationURL getOrganizationURL(Node organizationURLNode) {

        return OrganizationURL.builder()
                .lang(getAttributeValue(organizationURLNode, "xml:lang"))
                .value(organizationURLNode.getTextContent())
                .build();
    }

    private OrganizationName getOrganizationName(Node organizationNameNode) {
        return OrganizationName.builder()
                .lang(getAttributeValue(organizationNameNode, "xml:lang"))
                .value(organizationNameNode.getTextContent())
                .build();
    }

    private SPSSODescriptor getSPSSODescriptor(Node spssoDescriptorrNode) {
        SPSSODescriptor spssoDescriptor = SPSSODescriptor.builder()
                .authnRequestsSigned(getAttributeValue(spssoDescriptorrNode, "AuthnRequestsSigned"))
                .wantAssertionsSigned(getAttributeValue(spssoDescriptorrNode, "WantAssertionsSigned"))
                .protocolSupportEnumeration(getAttributeValue(spssoDescriptorrNode, "protocolSupportEnumeration"))
                .build();
        for (int i = 0; i < spssoDescriptorrNode.getChildNodes().getLength(); i++) {
            Node child = spssoDescriptorrNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "SingleLogoutService":
                    spssoDescriptor.setSingleLogoutService(getSingleLogoutService(child));
                    break;
                case "NameIDFormat":
                    spssoDescriptor.setNameIDFormat(child.getTextContent());
                    break;
                case "AssertionConsumerService":
                    spssoDescriptor.setAssertionConsumerService(getAssertionConsumerService(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return spssoDescriptor;
    }

    private AssertionConsumerService getAssertionConsumerService(Node sssertionConsumerServiceNode) {

        return AssertionConsumerService.builder()
                .binding(getAttributeValue(sssertionConsumerServiceNode, "Binding"))
                .location(getAttributeValue(sssertionConsumerServiceNode, "Location"))
                .index(getAttributeValue(sssertionConsumerServiceNode, "index"))
                .build();
    }

    private SingleLogoutService getSingleLogoutService(Node singleLogoutServiceNode) {


        return SingleLogoutService.builder()
                .binding(getAttributeValue(singleLogoutServiceNode, "Binding"))
                .location(getAttributeValue(singleLogoutServiceNode, "Location"))
                .build();
    }


    private Signature getSignatureInstance(Node signatureNode) {
        Signature signature = Signature.builder().build();

        for (int i = 0; i < signatureNode.getChildNodes().getLength(); i++) {
            Node child = signatureNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            // SignedInfo
            switch (nodeName) {
                case "SignedInfo":
                    signature.setSignedInfo(getSignedInfo(child));
                    break;
                case "SignatureValue":
                    signature.setSignatureValue(child.getTextContent());
                    break;
                case "KeyInfo":
                    signature.setKeyInfo(getKeyInfo(child));
                    break;
                default:
                    logNode(child);
                    break;

            }
        }

        return signature;
    }

    private KeyInfo getKeyInfo(Node keyInfoNode) {
        KeyInfo keyInfo = KeyInfo.builder().build();
        for (int i = 0; i < keyInfoNode.getChildNodes().getLength(); i++) {
            Node child = keyInfoNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());
            if ("X509Data".equals(nodeName)) {
                keyInfo.setX509Data(getX509Data(child));
            } else {
                logNode(child);
            }
        }
        return keyInfo;
    }

    private X509Data getX509Data(Node x509DataNode) {


        return X509Data.builder()
                .x509Certificate(trimToNull(x509DataNode.getTextContent()))
                .build();
    }

    private SignedInfo getSignedInfo(Node signedInfoNode) {
        SignedInfo signedInfo = SignedInfo.builder().build();
        for (int i = 0; i < signedInfoNode.getChildNodes().getLength(); i++) {
            Node child = signedInfoNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "CanonicalizationMethod":
                    signedInfo.setCanonicalizationMethod(getCanonicalizationMethod(child));
                    break;
                case "SignatureMethod":
                    signedInfo.setSignatureMethod(getSignatureMethod(child));
                    break;
                case "Reference":
                    signedInfo.setReference(getReference(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return signedInfo;
    }

    private Reference getReference(Node referenceNode) {
        Reference reference = Reference.builder()
                .uRI(getAttributeValue(referenceNode, "URI"))
                .build();
        for (int i = 0; i < referenceNode.getChildNodes().getLength(); i++) {
            Node child = referenceNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());
            switch (nodeName) {
                case "Transforms":
                    reference.setTransforms(getTransforms(child));
                    break;
                case "DigestMethod":
                    reference.setDigestMethod(getDigestMethod(child));
                    break;
                case "DigestValue":
                    reference.setDigestValue(child.getTextContent());
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return reference;
    }

    private DigestMethod getDigestMethod(Node digestMethodNode) {
        return DigestMethod.builder()
                .algorithm(getAttributeValue(digestMethodNode, "Algorithm"))
                .build();
    }

    private Transforms getTransforms(Node transformsNode) {
        Transforms transforms = Transforms.builder()
                .transform(new ArrayList<>())
                .build();
        for (int i = 0; i < transformsNode.getChildNodes().getLength(); i++) {
            Node child = transformsNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());
            if ("Transform".equals(nodeName)) {
                transforms.getTransform().add(Transform.builder()
                        .algorithm(getAttributeValue(child, "Algorithm"))
                        .build());
            }
        }
        return transforms;
    }

    private SignatureMethod getSignatureMethod(Node signatureMethodNode) {
        return SignatureMethod.builder()
                .algorithm(getAttributeValue(signatureMethodNode, "Algorithm"))
                .build();
    }

    private CanonicalizationMethod getCanonicalizationMethod(Node canonicalizationMethodNode) {
        return CanonicalizationMethod.builder()
                .algorithm(getAttributeValue(canonicalizationMethodNode, "Algorithm"))
                .build();
    }

    @Override
    public IDPMetaData parseIDPMetaData(InputStream xml) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document xmlDocument = dBuilder.parse(xml);

            IDPMetaData idpMetaData = IDPMetaData.builder()
                    .entityID(getAttributeValue(xmlDocument.getFirstChild(), "entityID"))
                    .validUntil(getAttributeValue(xmlDocument.getFirstChild(), "validUntil"))
                    .cacheDuration(getAttributeValue(xmlDocument.getFirstChild(), "cacheDuration"))
                    .iD(getAttributeValue(xmlDocument.getFirstChild(), "ID"))
                    .contactPerson(new ArrayList<>())
                    .roleDescriptor(new ArrayList<>())
                    .build();

            for (int i = 0; i < xmlDocument.getFirstChild().getChildNodes().getLength(); i++) {
                Node child = xmlDocument.getFirstChild().getChildNodes().item(i);
                String nodeName = stripNameSpace(child.getNodeName());
                switch (nodeName) {
                    case "Signature":
                        idpMetaData.setSignature(getSignatureInstance(child));
                        break;
                    case "IDPSSODescriptor":
                        idpMetaData.setIDPSSODescriptor(getIDPSSODescriptor(child));
                        break;
                    case "Organization":
                        idpMetaData.setOrganization(getOrganization(child));
                        break;
                    case "ContactPerson":
                        idpMetaData.getContactPerson().add(getContactPerson(child));
                        break;
                    case "RoleDescriptor":
                        idpMetaData.getRoleDescriptor().add(getRoleDescriptor(child));
                        break;
                    default:
                        logNode(child);
                        break;

                }
            }

            return idpMetaData;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.error("Unable ot parse the document due to error", e);
            throw new RuntimeException("Unable ot parse the document due to error", e);
        }
    }

    @Override
    public EntityDescriptorType parseMetadata(InputStream xml) {
        return parseXML(xml);
    }

    private RoleDescriptor getRoleDescriptor(Node roleDescriptorNode) {
        RoleDescriptor roleDescriptor = RoleDescriptor.builder()
                .type(getAttributeValue(roleDescriptorNode, "type"))
                .protocolSupportEnumeration(getAttributeValue(roleDescriptorNode, "protocolSupportEnumeration"))
                .build();

        for (int i = 0; i < roleDescriptorNode.getChildNodes().getLength(); i++) {
            Node child = roleDescriptorNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "KeyDescriptor":
                    roleDescriptor.setKeyDescriptor(getKeyDescriptor(child));
                    break;
                case "ClaimTypesOffered":
                    roleDescriptor.setClaimTypesOffered(getClaimTypesOffered(child));
                    break;
                case "SecurityTokenServiceEndpoint":
                    roleDescriptor.setSecurityTokenServiceEndpoint(getSecurityTokenServiceEndpoint(child));
                    break;
                case "PassiveRequestorEndpoint":
                    roleDescriptor.setPassiveRequestorEndpoint(getPassiveRequestorEndpoint(child));
                    break;
                case "TargetScopes":
                    roleDescriptor.setTargetScopes(getTargetScopes(child));
                    break;
                case "ApplicationServiceEndpoint":
                    roleDescriptor.setApplicationServiceEndpoint(getApplicationServiceEndpoint(child));
                    break;

                default:
                    logNode(child);
                    break;
            }
        }


        return roleDescriptor;
    }

    private ApplicationServiceEndpoint getApplicationServiceEndpoint(Node applicationServiceEndpointNode) {
        ApplicationServiceEndpoint applicationServiceEndpoint = ApplicationServiceEndpoint.builder().build();

        for (int i = 0; i < applicationServiceEndpointNode.getChildNodes().getLength(); i++) {
            Node child = applicationServiceEndpointNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "EndpointReference":
                    applicationServiceEndpoint.setEndpointReference(getEndpointReference(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return applicationServiceEndpoint;
    }

    private TargetScopes getTargetScopes(Node targetScopesNode) {
        TargetScopes targetScopes = TargetScopes.builder().build();

        for (int i = 0; i < targetScopesNode.getChildNodes().getLength(); i++) {
            Node child = targetScopesNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "EndpointReference":
                    targetScopes.setEndpointReference(getEndpointReference(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return targetScopes;
    }

    private PassiveRequestorEndpoint getPassiveRequestorEndpoint(Node passiveRequestorEndpointNode) {
        PassiveRequestorEndpoint passiveRequestorEndpoint = PassiveRequestorEndpoint.builder().build();

        for (int i = 0; i < passiveRequestorEndpointNode.getChildNodes().getLength(); i++) {
            Node child = passiveRequestorEndpointNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "EndpointReference":
                    passiveRequestorEndpoint.setEndpointReference(getEndpointReference(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return passiveRequestorEndpoint;
    }

    private SecurityTokenServiceEndpoint getSecurityTokenServiceEndpoint(Node securityTokenServiceEndpointNode) {
        SecurityTokenServiceEndpoint securityTokenServiceEndpoint = SecurityTokenServiceEndpoint.builder()
                .build();

        for (int i = 0; i < securityTokenServiceEndpointNode.getChildNodes().getLength(); i++) {
            Node child = securityTokenServiceEndpointNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "EndpointReference":
                    securityTokenServiceEndpoint.setEndpointReference(getEndpointReference(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return securityTokenServiceEndpoint;
    }

    private EndpointReference getEndpointReference(Node endpointReferenceNode) {
        EndpointReference endpointReference = EndpointReference.builder()
                .build();
        for (int i = 0; i < endpointReferenceNode.getChildNodes().getLength(); i++) {
            Node child = endpointReferenceNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "Address":
                    endpointReference.setAddress(child.getTextContent());
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return endpointReference;
    }

    private ClaimTypesOffered getClaimTypesOffered(Node claimTypesOfferedNode) {
        ClaimTypesOffered claimTypesOffered = ClaimTypesOffered.builder()
                .claimType(new ArrayList<>())
                .build();

        for (int i = 0; i < claimTypesOfferedNode.getChildNodes().getLength(); i++) {
            Node child = claimTypesOfferedNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "ClaimType":
                    claimTypesOffered.getClaimType().add(getClaimType(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return claimTypesOffered;
    }

    private ClaimType getClaimType(Node claimTypeNode) {
        ClaimType claimType = ClaimType.builder()
                .uri(getAttributeValue(claimTypeNode, "Uri"))
                .build();

        for (int i = 0; i < claimTypeNode.getChildNodes().getLength(); i++) {
            Node child = claimTypeNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "Description":
                    claimType.setDescription(child.getTextContent());
                    break;
                case "DisplayName":
                    claimType.setDisplayName(child.getTextContent());
                    break;
                default:
                    logNode(child);
                    break;
            }
        }

        return claimType;
    }

    private IDPSSODescriptor getIDPSSODescriptor(Node idpSSODescriptorNode) {
        IDPSSODescriptor idpssoDescriptor = IDPSSODescriptor.builder()
                .wantAuthnRequestsSigned(getAttributeValue(idpSSODescriptorNode, "WantAuthnRequestsSigned"))
                .protocolSupportEnumeration(getAttributeValue(idpSSODescriptorNode, "protocolSupportEnumeration"))
                .keyDescriptor(new ArrayList<>())
                .build();
        for (int i = 0; i < idpSSODescriptorNode.getChildNodes().getLength(); i++) {
            Node child = idpSSODescriptorNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());

            switch (nodeName) {
                case "SingleLogoutService":
                    idpssoDescriptor.setSingleLogoutService(getSingleLogoutService(child));
                    break;
                case "NameIDFormat":
                    idpssoDescriptor.setNameIDFormat(child.getTextContent());
                    break;
                case "SingleSignOnService":
                    idpssoDescriptor.setSingleSignOnService(getSingleSignOnService(child));
                    break;
                case "KeyDescriptor":
                    idpssoDescriptor.getKeyDescriptor().add(getKeyDescriptor(child));
                    break;
                default:
                    logNode(child);
                    break;
            }
        }
        return idpssoDescriptor;
    }

    private KeyDescriptor getKeyDescriptor(Node keyDescriptorNode) {
        KeyDescriptor keyDescriptor = KeyDescriptor.builder()
                .use(getAttributeValue(keyDescriptorNode, "use"))
                .build();

        for (int i = 0; i < keyDescriptorNode.getChildNodes().getLength(); i++) {
            Node child = keyDescriptorNode.getChildNodes().item(i);
            String nodeName = stripNameSpace(child.getNodeName());
            if ("KeyInfo".equals(nodeName)) {
                keyDescriptor.setKeyInfo(getKeyInfo(child));
            } else {
                logNode(child);
            }
        }

        return keyDescriptor;
    }

    private void logNode(Node child) {
        //We do not want to log #text, #comment nodes
        List<String> ignored = List.of("#text", "#comment");
        if (!ignored.contains(child.getNodeName())) {
            LOGGER.warn("Unmatched:{} -> {}", getXPath(child), child.getNodeValue());
        }
    }


    private SingleSignOnService getSingleSignOnService(Node singleSignOnServiceNode) {

        return SingleSignOnService.builder()
                .binding(getAttributeValue(singleSignOnServiceNode, "Binding"))
                .location(getAttributeValue(singleSignOnServiceNode, "Location"))
                .build();
    }

}
