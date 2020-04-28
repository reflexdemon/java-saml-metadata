package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.IDPMetaData;
import io.vpv.saml.metadata.model.SPMetaData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

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

    @SneakyThrows
    @Test
    void shouldParseMetadataForSPXMLURL() {

        SPMetaData spMetaData = null;
        URL pathAsURL = getFilePathAsURL("/data/sp-metadata.xml");
        try {
            spMetaData = metaDataParser.parseSPMetadata(pathAsURL);
        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to read file: {}", pathAsURL, e);
        }
        LOGGER.debug("Parsed Object: {}", spMetaData);
        assertNotNull(spMetaData, "Should parse and get an instance of metadata");

    }

    @SneakyThrows
    @Test
    void shouldParseMetadataForIDPXML() {

        IDPMetaData idpMetaData = null;
        String fileName = getFilePath() +
                "/data/idp-metadata.xml";
        try {
            idpMetaData = metaDataParser.parseIDPMetaData(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to read file: {}", fileName, e);
        }
        LOGGER.debug("Parsed Object: {}", idpMetaData);
        assertNotNull(idpMetaData, "Should parse and get an instance of metadata");

    }

    @SneakyThrows
//    @Test
    void shouldParseIDPMetaDaaWithClaims() {
        // TODO: Will have to FIx this ASAP
        IDPMetaData idpMetaData = null;
        String fileName = getFilePath() +
                "/data/idp-with-claims.metadata.xml";
        try {
            idpMetaData = metaDataParser.parseIDPMetaData(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to read file: {}", fileName, e);
        }
        LOGGER.debug("Parsed Object: {}", idpMetaData);
        assertNotNull(idpMetaData, "Should parse and get an instance of metadata");

    }

    @SneakyThrows
    @Test
    void shouldParseMetadataForIDPXMLURL() {

        IDPMetaData idpMetaData = null;
        URL pathAsURL = getFilePathAsURL("/data/idp-metadata.xml");
        try {
            idpMetaData = metaDataParser.parseIDPMetaData(pathAsURL);
        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to read file: {}", pathAsURL, e);
        }
        LOGGER.debug("Parsed Object: {}", idpMetaData);
        assertNotNull(idpMetaData, "Should parse and get an instance of metadata");

    }

    @SneakyThrows
    @Test
    void shouldNotParseMetadataForIDPXML() {

        IDPMetaData idpMetaData = null;
        String fileName = "/data/idp-not-a-valid-file.xml";
        try {
            idpMetaData = metaDataParser.parseIDPMetaData(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.error("Unable to read file: {}", fileName, e);
        }
        LOGGER.debug("Parsed Object: {}", idpMetaData);
        assertNull(idpMetaData, "Should not parse and not get an instance of metadata");

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

    private String getFilePath() {
        Path resourceDirectory = Paths.get("src", "test", "resources");
        return resourceDirectory.toFile().getAbsolutePath();
    }

    private URL getFilePathAsURL(String filePath) throws IOException {
        Path resourceFile = Paths.get("src", "test", "resources", filePath);
        return resourceFile.toUri().toURL();

    }

    @Test
    void testingAllNegativeFlowForCoverage() {
        final String fileName = null;
        assertThrows(FileNotFoundException.class, () -> metaDataParser.parseIDPMetaData(fileName));
        assertThrows(FileNotFoundException.class, () -> metaDataParser.parseSPMetadata(fileName));
        final URL url = null;
        assertThrows(IOException.class, () -> metaDataParser.parseIDPMetaData(url));
        assertThrows(IOException.class, () -> metaDataParser.parseSPMetadata(url));
    }

    @SneakyThrows
    @Test
    void testBadXML() {
        final URL fileName = getFilePathAsURL("/data/bad.xml");
        assertThrows(RuntimeException.class, () -> metaDataParser.parseIDPMetaData(fileName));
        assertThrows(RuntimeException.class, () -> metaDataParser.parseSPMetadata(fileName));
    }
}