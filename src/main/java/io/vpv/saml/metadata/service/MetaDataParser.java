package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.IDPMetaData;
import io.vpv.saml.metadata.model.SPMetaData;
import io.vpv.saml.metadata.xml.modal.EntityDescriptorType;

import java.io.*;
import java.net.URL;

import static java.util.Objects.nonNull;


/**
 * The Interface MetaDataParser.
 */
public interface MetaDataParser {

    /**
     * Parses the SP metadata.
     *
     * @param xml the xml
     * @return the SP meta data
     */
    SPMetaData parseSPMetadata(InputStream xml);


    /**
     * Parses the IDP meta data.
     *
     * @param xml the xml
     * @return the IDP meta data
     */
    IDPMetaData parseIDPMetaData(InputStream xml);

    /**
     * Parses the SP metadata.
     *
     * @param xmlFile the xml file
     * @return the SP meta data
     * @throws FileNotFoundException the file not found exception
     */
    default SPMetaData parseSPMetadata(File xmlFile) throws FileNotFoundException {
        if (nonNull(xmlFile) && xmlFile.exists()) {
            return parseSPMetadata(new FileInputStream(xmlFile));
        }
        throw new FileNotFoundException("Unable to parse file :" + xmlFile);
    }

    /**
     * Parses the IDP meta data.
     *
     * @param xmlFile the xml file
     * @return the IDP meta data
     * @throws FileNotFoundException the file not found exception
     */
    default IDPMetaData parseIDPMetaData(File xmlFile) throws FileNotFoundException {
        if (nonNull(xmlFile) && xmlFile.exists()) {
            return parseIDPMetaData(new FileInputStream(xmlFile));
        }
        throw new FileNotFoundException("Unable to parse file :" + xmlFile);
    }

    /**
     * Parses the SP metadata.
     *
     * @param fileName the file name
     * @return the SP meta data
     * @throws FileNotFoundException the file not found exception
     */
    default SPMetaData parseSPMetadata(String fileName) throws FileNotFoundException {
        if (nonNull(fileName)) {
            return parseSPMetadata(new File(fileName));
        }
        throw new FileNotFoundException("Unable to parse null filename");
    }

    /**
     * Parses the IDP meta data.
     *
     * @param fileName the file name
     * @return the IDP meta data
     * @throws FileNotFoundException the file not found exception
     */
    default IDPMetaData parseIDPMetaData(String fileName) throws FileNotFoundException {
        if (nonNull(fileName)) {
            return parseIDPMetaData(new File(fileName));
        }
        throw new FileNotFoundException("Unable to parse null filename");
    }

    /**
     * Parses the SP metadata.
     *
     * @param xmlUrl the xml url
     * @return the SP meta data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    default SPMetaData parseSPMetadata(URL xmlUrl) throws IOException {
        if (nonNull(xmlUrl)) {
            return parseSPMetadata(xmlUrl.openStream());
        }
        throw new IOException("Unable to parse as the URL provided was null");
    }

    /**
     * Parses the IDP meta data.
     *
     * @param xmlUrl the xml url
     * @return the IDP meta data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    default IDPMetaData parseIDPMetaData(URL xmlUrl) throws IOException {
        if (nonNull(xmlUrl)) {
            return parseIDPMetaData(xmlUrl.openStream());
        }
        throw new IOException("Unable to parse as the URL provided was null");
    }

    EntityDescriptorType parseMetadata(InputStream xml);

    default EntityDescriptorType parseMetadata(File xmlFile) throws FileNotFoundException {
        if (nonNull(xmlFile) && xmlFile.exists()) {
            return parseMetadata(new FileInputStream(xmlFile));
        }
        throw new FileNotFoundException("Unable to parse file :" + xmlFile);
    }

    default EntityDescriptorType parseMetadata(String fileName) throws FileNotFoundException {
        if (nonNull(fileName)) {
            return parseMetadata(new File(fileName));
        }
        throw new FileNotFoundException("Unable to parse null filename");
    }
    default EntityDescriptorType parseMetadata(URL xmlUrl) throws IOException {
        if (nonNull(xmlUrl)) {
            return parseMetadata(xmlUrl.openStream());
        }
        throw new IOException("Unable to parse as the URL provided was null");
    }
}
