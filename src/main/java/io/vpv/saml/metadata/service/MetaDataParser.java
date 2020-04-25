package io.vpv.saml.metadata.service;

import io.vpv.saml.metadata.model.IDPMetaData;
import io.vpv.saml.metadata.model.SPMetaData;

import java.io.*;
import java.net.URL;

import static java.util.Objects.nonNull;

public interface MetaDataParser {
    SPMetaData parseSPMetadata(InputStream xml);
    IDPMetaData parseIDPMetaData(InputStream xml);

    default SPMetaData parseSPMetadata(File xmlFile) throws FileNotFoundException {
        if (nonNull(xmlFile) && xmlFile.exists()) {
            return parseSPMetadata(new FileInputStream(xmlFile));
        }
        throw new FileNotFoundException("Unable to parse file :" + xmlFile);
    }
    default IDPMetaData parseIDPMetaData(File xmlFile) throws FileNotFoundException {
        if (nonNull(xmlFile) && xmlFile.exists()) {
            return parseIDPMetaData(new FileInputStream(xmlFile));
        }
        throw new FileNotFoundException("Unable to parse file :" + xmlFile);
    }

    default SPMetaData parseSPMetadata(String fileName) throws FileNotFoundException {
        if (nonNull(fileName)) {
            return parseSPMetadata(new File(fileName));
        }
        throw new FileNotFoundException("Unable to parse null filename");
    }
    default IDPMetaData parseIDPMetaData(String fileName) throws FileNotFoundException {
        if (nonNull(fileName)) {
            return parseIDPMetaData(new File(fileName));
        }
        throw new FileNotFoundException("Unable to parse null filename");
    }

    default SPMetaData parseSPMetadata(URL xmlUrl) throws IOException {
        if (nonNull(xmlUrl)) {
            return parseSPMetadata(xmlUrl.openStream());
        }
        throw new IOException("Unable to parse as the URL provided was null");
    }
    default IDPMetaData parseIDPMetaData(URL xmlUrl) throws IOException {
        if (nonNull(xmlUrl)) {
            return parseIDPMetaData(xmlUrl.openStream());
        }
        throw new IOException("Unable to parse as the URL provided was null");
    }
}
