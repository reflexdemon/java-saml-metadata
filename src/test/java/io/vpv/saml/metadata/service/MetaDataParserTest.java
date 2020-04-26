package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.SPMetaData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @SneakyThrows
    @Test
    void shouldParseMetadataForSPXML() {

        SPMetaData spMetaData = null;
        String fileName = getFilePath() +
                "/data/sp-metadata.xml";
        try {
            spMetaData = metaDataParser.parseSPMetadata(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to read file: {}", fileName, e);
        }
        LOGGER.debug("Parsed Object: {}", spMetaData);
        assertNotNull(spMetaData, "Should parse and get an instance of metadata");

    }

    @Test
    void shouldNotBeAbleToParseAndThroewFileNotFoundForSP() {
        SPMetaData spMetaData = null;
        String fileName = "/data/sp-not-valid-fileName.xml";
        try {
            spMetaData = metaDataParser.parseSPMetadata(fileName);
        } catch (FileNotFoundException e) {
            //Ignore
        }
        LOGGER.debug("Parsed Object: {}", spMetaData);
        assertNull(spMetaData, "Should not parse and not get an instance of metadata");
    }

    private String getFilePath() throws IOException {
        Path resourceDirectory = Paths.get("src","test","resources");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        return absolutePath;
    }

    @Test
    void parseIDPMetaData() {
    }
}