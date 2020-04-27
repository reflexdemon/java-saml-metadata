# Java SAML Metadata Parser [![jdk11](https://img.shields.io/badge/Open%20JDK-11-green.svg)](http://jdk.java.net/11/)[![jdk11](https://img.shields.io/badge/Oracle%20JDK-11-green.svg)](https://www.oracle.com/technetwork/java/javase/11-relnote-issues-5012449.html) [![Build Status](https://travis-ci.org/reflexdemon/java-saml-metadata.svg?branch=master)](https://travis-ci.org/reflexdemon/java-saml-metadata)

## Introduction
This is a simple library to parse the IDP/SP XML Metadata. This converts the XML document into POJOs. This is very useful in usecases where the federated SSO will be used.

### Maven Dependency

To include this library on to your project,
```xml
<dependency>
  <groupId>io.vpv.saml.meta</groupId>
  <artifactId>java-saml-metadata</artifactId>
  <version>0.0.1</version>
</dependency>
``` 
This can be used with just plain vanilla Java Projects as well. It works perfectly on spring based projects as well.

### Example Usage Parsing IDP Metadata

To parse `IDP metadata` please see the below code.

```java
    MetaDataParser metaDataParser = new MetaDataParserImpl();
    IDPMetaData idpMetaData = null;
    String fileName = getFilePath() +
            "/data/idp-metadata.xml";
    try {
        idpMetaData = metaDataParser.parseIDPMetaData(fileName);
    } catch (FileNotFoundException e) {
        LOGGER.error("Unable to read file: {}", fileName, e);
    }
    LOGGER.debug("Parsed Object: {}", idpMetaData);

```

### Example Usage Parsing SP Metadata

To parse `SP metadata` please see the below code.

```java
    MetaDataParser metaDataParser = new MetaDataParserImpl();
    SPMetaData spMetaData = null;
    String fileName = getFilePath() +
            "/data/sp-metadata.xml";
    try {
        spMetaData = metaDataParser.parseSPMetadata(fileName);
    } catch (FileNotFoundException e) {
        LOGGER.error("Unable to read file: {}", fileName, e);
    }
    LOGGER.debug("Parsed Object: {}", spMetaData);
```

### Overloaded `parseXXXMetadata`

To make it convenient the parser method has been over loaded with following signatures,

#### IDP Parsing methods
1. `IDPMetaData parseIDPMetaData(InputStream xml)`
2. `IDPMetaData parseIDPMetaData(URL xmlUrl) throws IOException `
3. `IDPMetaData parseIDPMetaData(File xmlFile) throws FileNotFoundException`
4. `IDPMetaData parseIDPMetaData(String fileName) throws FileNotFoundException`


#### SP Parsing methods

1. `SPMetaData parseSPMetadata(InputStream xml)`
2. `SPMetaData parseSPMetadata(URL xmlUrl) throws IOException`
3. `SPMetaData parseSPMetadata(File xmlFile) throws FileNotFoundException`
4. `SPMetaData parseSPMetadata(String fileName) throws FileNotFoundException`




[![HitCount](http://hits.dwyl.io/reflexdemon/java-saml-metadata.svg)](http://hits.dwyl.io/reflexdemon/java-saml-metadata)

[![codecov](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graph/badge.svg)](https://codecov.io/gh/reflexdemon/java-saml-metadata)




## Code Coverage Graphs

### Sunburst
![Sunburst](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/sunburst.svg "Sunburst Code Coverage")

### Icicle
![Icicle](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/icicle.svg "Icicle Code Coverage")

### Grid
![Grid](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/tree.svg "Grid Code Coverage")

### Commits Graph
![Grid](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/commits.svg "Commit Graph")
