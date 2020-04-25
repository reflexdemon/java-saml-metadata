package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.SPMetaData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class MetaDataParserTest {

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
            assertNotNull(spMetaData, "Should parse and get an instance of metadata");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void parseIDPMetaData() {
    }
}