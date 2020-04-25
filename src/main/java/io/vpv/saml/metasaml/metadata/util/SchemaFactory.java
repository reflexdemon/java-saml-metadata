package io.vpv.saml.metasaml.metadata.util;


import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;

import org.xml.sax.SAXException;

/**
 * SchemaFactory class of VPV Saml Tool
 *
 * A class that read SAML schemas that will be used to validate XMLs of the VPV SAML Tool
 */ 
public abstract class SchemaFactory {
	
	private SchemaFactory() {
	      //not called
	}
	
	public static final URL SAML_SCHEMA_METADATA_2_0 = SchemaFactory.class
			.getResource("/schemas/saml-schema-metadata-2.0.xsd");
	public static final URL SAML_SCHEMA_PROTOCOL_2_0 = SchemaFactory.class
			.getResource("/schemas/saml-schema-protocol-2.0.xsd");

	public static Schema loadFromUrl(URL schemaUrl) throws SAXException {
		return javax.xml.validation.SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl);
	}
}
