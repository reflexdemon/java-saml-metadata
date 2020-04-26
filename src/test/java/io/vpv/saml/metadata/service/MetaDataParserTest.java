package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.SPMetaData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MetaDataParserTest {
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
    void shouldParseMetadataForSPXML() {
        try {
            SPMetaData spMetaData = metaDataParser.parseSPMetadata("./data/sp-metadata.xml");
            LOGGER.debug("Parsed Object: {}", spMetaData);
            assertNotNull(spMetaData, "Should parse and get an instance of metadata");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void parseIDPMetaData() {
    }
}