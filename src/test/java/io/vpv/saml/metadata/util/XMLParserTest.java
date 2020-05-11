package io.vpv.saml.metadata.util;

import io.vpv.saml.metadata.service.MetaDataParser;
import io.vpv.saml.metadata.service.MetaDataParserImpl;
import io.vpv.saml.metadata.xml.modal.EntityDescriptorType;
import io.vpv.saml.metadata.xml.modal.ReferenceList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class XMLParserTest {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private MetaDataParser metaDataParser;

    @BeforeEach
    void setUp() {
        metaDataParser = new MetaDataParserImpl();
    }

    @AfterEach
    void tearDown() {
        metaDataParser = null;
    }


    @Test
    void shouldBeAbleToParseIDPXMLfile() throws FileNotFoundException {
        String xmlFile = getFilePath() + "/data/idp-metadata.xml";
        InputStream inputStream = new FileInputStream(new File(xmlFile));
        EntityDescriptorType referenceList = metaDataParser.parseMetadata(inputStream);
        LOGGER.info("The Parsed XML: {}", referenceList);
        assertNotNull(referenceList, "Should not be a null value");
    }

    @Test
    void shouldBeAbleToParseSPXMLfile() throws FileNotFoundException {
        String xmlFile = getFilePath() + "/data/sp-metadata.xml";
        InputStream inputStream = new FileInputStream(new File(xmlFile));
        EntityDescriptorType referenceList = metaDataParser.parseMetadata(inputStream);
        LOGGER.info("The Parsed XML: {}", referenceList);
        assertNotNull(referenceList, "Should not be a null value");
    }
    private String getFilePath() {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        return resourceDirectory.toFile().getAbsolutePath();
    }
}