# Java SAML Metadata Parser [![jdk11](https://img.shields.io/badge/Open%20JDK-11-green.svg)](http://jdk.java.net/11/)[![jdk11](https://img.shields.io/badge/Oracle%20JDK-11-green.svg)](https://www.oracle.com/technetwork/java/javase/11-relnote-issues-5012449.html) [![Build Status](https://travis-ci.org/reflexdemon/java-saml-metadata.svg?branch=master)](https://travis-ci.org/reflexdemon/java-saml-metadata)

[![HitCount](http://hits.dwyl.io/reflexdemon/java-saml-metadata.svg)](http://hits.dwyl.io/reflexdemon/java-saml-metadata)
[![codecov](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graph/badge.svg)](https://codecov.io/gh/reflexdemon/java-saml-metadata)

## Introduction
This is a simple library to parse the IDP/SP XML Metadata. This converts the XML document into POJOs. This is very useful in usecases where the federated SSO will be used.

### Maven Dependency

To include this library on to your project,
```xml
<!-- https://oss.sonatype.org/#nexus-search;quick~java-saml-metadata -->
<dependency>
  <groupId>io.vpv.saml.meta</groupId>
  <artifactId>java-saml-metadata</artifactId>
  <version>0.0.4</version>
</dependency>
```

### Gradle Dependency
```groovy
// https://oss.sonatype.org/#nexus-search;quick~java-saml-metadata
compile group: 'io.vpv.saml.meta', name: 'java-saml-metadata', version: '0.0.4'
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

#### Output
```text
DEBUG io.vpv.saml.metadata.service.MetaDataParserTest - Parsed Object: IDPMetaData(validUntil=2020-04-27T10:44:43Z, cacheDuration=PT1588416283S, entityID=https://truelogin.vpv.io, iD=pfx8d46a4d2-9f8f-46e1-82b7-93254a84d4c5, signature=Signature(signedInfo=SignedInfo(canonicalizationMethod=CanonicalizationMethod(algorithm=http://www.w3.org/2001/10/xml-exc-c14n#), signatureMethod=SignatureMethod(algorithm=http://www.w3.org/2000/09/xmldsig#rsa-sha1), reference=Reference(uRI=#pfx8d46a4d2-9f8f-46e1-82b7-93254a84d4c5, transforms=Transforms(transform=[Transform(algorithm=http://www.w3.org/2000/09/xmldsig#enveloped-signature), Transform(algorithm=http://www.w3.org/2001/10/xml-exc-c14n#)]), digestMethod=DigestMethod(algorithm=http://www.w3.org/2000/09/xmldsig#sha1), digestValue=gZuDllTVz/9nYosAutd80CrUIfQ=)), signatureValue=JsKNHx5Soc3AQBLfFyXA4wbsDMOtyfW0rHLomAL8TdIZVucOTIFdxw6uucUXtj+m9Kku7KrwKyFUqunhnO28d+3bjusXYdQkgYilum9c9TNbrmJxCAVgVBu/DD8tbaHd1VS1x+aLqwcDyYN32Kl4Ez7cQwa0KTM25OZiwatnmXko/jCi47GUgJa0bIfmTCXCrh9eVN/GMJdvrb7nc5zCkIWe35tyX4CT3P68x0Qjnl9LBWB/V7bp337sHFAVh3Ta1XuJ/AqyZz3PqKnMswghNlYwv+/AbGk7aRRjxt7L3dKul6JPiPH1f7gYTypQAv4CTZyBh2IoR1AtkjTXQ5zqGIg=, keyInfo=KeyInfo(x509Data=X509Data(x509Certificate=MIID7zCCAtagAwIBAgIBADANBgkqhkiG9w0BAQsFADCBkDELMAkGA1UEBhMCdXMxCzAJBgNVBAgMAkdBMRMwEQYDVQQKDApUcnVlIExvZ2luMRkwFwYDVQQDDBB0cnVlbG9naW4udnB2LmlvMRAwDgYDVQQHDAdDdW1taW5nMRMwEQYDVQQLDApUcnVlIExvZ2luMR0wGwYJKoZIhvcNAQkBFg5jb250YWN0QHZwdi5pbzAeFw0yMDA0MjUxMDQxMzlaFw00NzA5MTAxMDQxMzlaMIGQMQswCQYDVQQGEwJ1czELMAkGA1UECAwCR0ExEzARBgNVBAoMClRydWUgTG9naW4xGTAXBgNVBAMMEHRydWVsb2dpbi52cHYuaW8xEDAOBgNVBAcMB0N1bW1pbmcxEzARBgNVBAsMClRydWUgTG9naW4xHTAbBgkqhkiG9w0BCQEWDmNvbnRhY3RAdnB2LmlvMIIBIzANBgkqhkiG9w0BAQEFAAOCARAAMIIBCwKCAQIAzhdbKY7Pck7Q504fFI8vubSPB2nIdtKbmFCNBCv+UxnMVL9w5ucJzlYvnPMZTpZA0j4gHUfuSA/1lIuai3s7AvxLjVlHnchbkEtG+Gi4TOcEuKn57qxzzXQmhjLEhsjti7SbwPSH9KONtRkEj2DHH+J5t18llhds6Q+OtcRxwhfCaxWLbN6tz36AKAvfZbmpFc816QMkL+1uxmXwA0hbxUPeTfBqGuNLNxxp8ls476keqPZE8bWT0M4C8H5OMTNv4nbvpgwTlujAMb0bcUEyOCMuxs3E10G738YtweEr1x2StLiu9bd9WV2vibtdfwrAW7LTROTkBAt0n3RKlYCnMg0CAwEAAaNQME4wHQYDVR0OBBYEFGCl3XHSjbKtlh5ogyO+V9H5romoMB8GA1UdIwQYMBaAFGCl3XHSjbKtlh5ogyO+V9H5romoMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggECAKw267LNnop5FRia44na/Ojn6853SAl2MHL93+qs+FFYMUYvkW/c9Khsi6xQp/14dOXvV7spKC+/L+bfY8jpHf/49dVt/MVEMTjU2pv7NCHOpv7noj2L5yLiyBSPXkSUzVOMP48PtpTCZ5LyLLViL7ZbouUzGlKH+LX6aGI+/H6RbKSc9v9ovqX81sOhw7FsLHBi28wVsudzo6lnBnLSD82sCBfsGdtvtJXuGPXV/r6ip8DG7Sr/Wb18xkvDDVZTXmV/lmDom0eb/Sp0Nic3ed1Wm3J9tXtn4lgYKmDmV8pg9bq7w3clsGV+bxmCHL2M7ZBsSoN1x5qEkYstWY/XZwg6))), iDPSSODescriptor=IDPSSODescriptor(wantAuthnRequestsSigned=false, protocolSupportEnumeration=urn:oasis:names:tc:SAML:2.0:protocol, keyDescriptor=[KeyDescriptor(use=signing, keyInfo=KeyInfo(x509Data=X509Data(x509Certificate=MIID7zCCAtagAwIBAgIBADANBgkqhkiG9w0BAQsFADCBkDELMAkGA1UEBhMCdXMxCzAJBgNVBAgMAkdBMRMwEQYDVQQKDApUcnVlIExvZ2luMRkwFwYDVQQDDBB0cnVlbG9naW4udnB2LmlvMRAwDgYDVQQHDAdDdW1taW5nMRMwEQYDVQQLDApUcnVlIExvZ2luMR0wGwYJKoZIhvcNAQkBFg5jb250YWN0QHZwdi5pbzAeFw0yMDA0MjUxMDQxMzlaFw00NzA5MTAxMDQxMzlaMIGQMQswCQYDVQQGEwJ1czELMAkGA1UECAwCR0ExEzARBgNVBAoMClRydWUgTG9naW4xGTAXBgNVBAMMEHRydWVsb2dpbi52cHYuaW8xEDAOBgNVBAcMB0N1bW1pbmcxEzARBgNVBAsMClRydWUgTG9naW4xHTAbBgkqhkiG9w0BCQEWDmNvbnRhY3RAdnB2LmlvMIIBIzANBgkqhkiG9w0BAQEFAAOCARAAMIIBCwKCAQIAzhdbKY7Pck7Q504fFI8vubSPB2nIdtKbmFCNBCv+UxnMVL9w5ucJzlYvnPMZTpZA0j4gHUfuSA/1lIuai3s7AvxLjVlHnchbkEtG+Gi4TOcEuKn57qxzzXQmhjLEhsjti7SbwPSH9KONtRkEj2DHH+J5t18llhds6Q+OtcRxwhfCaxWLbN6tz36AKAvfZbmpFc816QMkL+1uxmXwA0hbxUPeTfBqGuNLNxxp8ls476keqPZE8bWT0M4C8H5OMTNv4nbvpgwTlujAMb0bcUEyOCMuxs3E10G738YtweEr1x2StLiu9bd9WV2vibtdfwrAW7LTROTkBAt0n3RKlYCnMg0CAwEAAaNQME4wHQYDVR0OBBYEFGCl3XHSjbKtlh5ogyO+V9H5romoMB8GA1UdIwQYMBaAFGCl3XHSjbKtlh5ogyO+V9H5romoMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggECAKw267LNnop5FRia44na/Ojn6853SAl2MHL93+qs+FFYMUYvkW/c9Khsi6xQp/14dOXvV7spKC+/L+bfY8jpHf/49dVt/MVEMTjU2pv7NCHOpv7noj2L5yLiyBSPXkSUzVOMP48PtpTCZ5LyLLViL7ZbouUzGlKH+LX6aGI+/H6RbKSc9v9ovqX81sOhw7FsLHBi28wVsudzo6lnBnLSD82sCBfsGdtvtJXuGPXV/r6ip8DG7Sr/Wb18xkvDDVZTXmV/lmDom0eb/Sp0Nic3ed1Wm3J9tXtn4lgYKmDmV8pg9bq7w3clsGV+bxmCHL2M7ZBsSoN1x5qEkYstWY/XZwg6))), KeyDescriptor(use=encryption, keyInfo=KeyInfo(x509Data=X509Data(x509Certificate=MIID7zCCAtagAwIBAgIBADANBgkqhkiG9w0BAQsFADCBkDELMAkGA1UEBhMCdXMxCzAJBgNVBAgMAkdBMRMwEQYDVQQKDApUcnVlIExvZ2luMRkwFwYDVQQDDBB0cnVlbG9naW4udnB2LmlvMRAwDgYDVQQHDAdDdW1taW5nMRMwEQYDVQQLDApUcnVlIExvZ2luMR0wGwYJKoZIhvcNAQkBFg5jb250YWN0QHZwdi5pbzAeFw0yMDA0MjUxMDQxMzlaFw00NzA5MTAxMDQxMzlaMIGQMQswCQYDVQQGEwJ1czELMAkGA1UECAwCR0ExEzARBgNVBAoMClRydWUgTG9naW4xGTAXBgNVBAMMEHRydWVsb2dpbi52cHYuaW8xEDAOBgNVBAcMB0N1bW1pbmcxEzARBgNVBAsMClRydWUgTG9naW4xHTAbBgkqhkiG9w0BCQEWDmNvbnRhY3RAdnB2LmlvMIIBIzANBgkqhkiG9w0BAQEFAAOCARAAMIIBCwKCAQIAzhdbKY7Pck7Q504fFI8vubSPB2nIdtKbmFCNBCv+UxnMVL9w5ucJzlYvnPMZTpZA0j4gHUfuSA/1lIuai3s7AvxLjVlHnchbkEtG+Gi4TOcEuKn57qxzzXQmhjLEhsjti7SbwPSH9KONtRkEj2DHH+J5t18llhds6Q+OtcRxwhfCaxWLbN6tz36AKAvfZbmpFc816QMkL+1uxmXwA0hbxUPeTfBqGuNLNxxp8ls476keqPZE8bWT0M4C8H5OMTNv4nbvpgwTlujAMb0bcUEyOCMuxs3E10G738YtweEr1x2StLiu9bd9WV2vibtdfwrAW7LTROTkBAt0n3RKlYCnMg0CAwEAAaNQME4wHQYDVR0OBBYEFGCl3XHSjbKtlh5ogyO+V9H5romoMB8GA1UdIwQYMBaAFGCl3XHSjbKtlh5ogyO+V9H5romoMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggECAKw267LNnop5FRia44na/Ojn6853SAl2MHL93+qs+FFYMUYvkW/c9Khsi6xQp/14dOXvV7spKC+/L+bfY8jpHf/49dVt/MVEMTjU2pv7NCHOpv7noj2L5yLiyBSPXkSUzVOMP48PtpTCZ5LyLLViL7ZbouUzGlKH+LX6aGI+/H6RbKSc9v9ovqX81sOhw7FsLHBi28wVsudzo6lnBnLSD82sCBfsGdtvtJXuGPXV/r6ip8DG7Sr/Wb18xkvDDVZTXmV/lmDom0eb/Sp0Nic3ed1Wm3J9tXtn4lgYKmDmV8pg9bq7w3clsGV+bxmCHL2M7ZBsSoN1x5qEkYstWY/XZwg6)))], singleLogoutService=SingleLogoutService(binding=urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect, location=https://truelogin.vpv.io/signout), nameIDFormat=urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified, singleSignOnService=SingleSignOnService(binding=urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect, location=https://truelogin.vpv.io/signin)), organization=Organization(organizationName=OrganizationName(lang=en-US, value=True Login), organizationDisplayName=OrganizationDisplayName(lang=en-US, value=True Login), organizationURL=OrganizationURL(lang=en-US, value=https://truelogin.vpv.io)), contactPerson=[ContactPerson(contactType=technical, givenName=Venkateswara, emailAddress=contact@vpv.io), ContactPerson(contactType=support, givenName=Venkateswara, emailAddress=contact@vpv.io)])
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

#### Output
```text
DEBUG io.vpv.saml.metadata.service.MetaDataParserTest - Parsed Object: SPMetaData(validUntil=2020-04-27T10:56:36Z, cacheDuration=PT604800S, entityID=https://math.vpv.io, iD=pfx3116688f-4787-d521-daff-63c1202a9ae3, signature=Signature(signedInfo=SignedInfo(canonicalizationMethod=CanonicalizationMethod(algorithm=http://www.w3.org/2001/10/xml-exc-c14n#), signatureMethod=SignatureMethod(algorithm=http://www.w3.org/2000/09/xmldsig#rsa-sha1), reference=Reference(uRI=#pfx3116688f-4787-d521-daff-63c1202a9ae3, transforms=Transforms(transform=[Transform(algorithm=http://www.w3.org/2000/09/xmldsig#enveloped-signature), Transform(algorithm=http://www.w3.org/2001/10/xml-exc-c14n#)]), digestMethod=DigestMethod(algorithm=http://www.w3.org/2000/09/xmldsig#sha1), digestValue=QtOwMzoSFZwgyPrUuJIve6nB8LQ=)), signatureValue=KR/egsuTAk9jMu46Bbo0E3xjoNUJuBrDO1qJ5CwrZIBtEPMP9CNAdMdYimSmsbc5lhIB35ja0+Is9fZV3d5iz7aWkBRoM/cAswBaPhRO4DWsaZno/41cd/NIIYkYQdNvo2iXsLqKSe3zQE2VN9hxiEWP/xg2JnxhYpFdn/LFegvwpHGlvPaZibKiDjJpxgaqaAV6iQSswhOMs9pct3nfRU8D31USnwfVBnZEbobTXkOlTZlB+0dw7XXjiDO6eLwTdiX7yduSrQWOSdAi+rN9tE7JMU7DAEy/pebK7aciGV6SE3KURFqSQxaycqD2zUY8gB2KIB+A+JOKcN3cZF836Rs=, keyInfo=KeyInfo(x509Data=X509Data(x509Certificate=MIID3TCCAsSgAwIBAgIBADANBgkqhkiG9w0BAQsFADCBhzELMAkGA1UEBhMCdXMxCzAJBgNVBAgMAkdBMREwDwYDVQQKDAhNYXRoIFZQVjEUMBIGA1UEAwwLbWF0aC52cHYuaW8xEDAOBgNVBAcMB0N1bW1pbmcxETAPBgNVBAsMCE1BdGggVlBWMR0wGwYJKoZIhvcNAQkBFg5jb250YWN0QHZwdi5pbzAeFw0yMDA0MjUxMDQ2NTFaFw00NzA5MTAxMDQ2NTFaMIGHMQswCQYDVQQGEwJ1czELMAkGA1UECAwCR0ExETAPBgNVBAoMCE1hdGggVlBWMRQwEgYDVQQDDAttYXRoLnZwdi5pbzEQMA4GA1UEBwwHQ3VtbWluZzERMA8GA1UECwwITUF0aCBWUFYxHTAbBgkqhkiG9w0BCQEWDmNvbnRhY3RAdnB2LmlvMIIBIzANBgkqhkiG9w0BAQEFAAOCARAAMIIBCwKCAQIApevLLGEmSj7tCDan5EpNIVKMZjS4GNqWKJwNP7mM1GWh1D3yB2EfeyfUDzA9PvjrGEtVwMt0ZEy2YZc/hvet19VYyNe7GIIknsilwADmqrkcJQbIFTOb00Am+03DwZLPJf0Qw73v4XnaRDqBWF1BGeYBrmG/R21hPTgamIn8VgBNP815AeaYfBRnwQGAwe8iUuxIMgI7wFk/sDKxxRxGkPvPqqXIVTT5eW/aSDb2T/wBeIfwhsme2qjcI9IsoqR28hHes/Gd5mxWInbwsfGvEvPKaJFsYH92Ild9k9K7PsaouFKmIxOT3RHcdOt4pq+tqPDO3ZyvjM/TF5Dy03D2/ikCAwEAAaNQME4wHQYDVR0OBBYEFD3jNwN6VsLn8FM+Gik30KZBaUbpMB8GA1UdIwQYMBaAFD3jNwN6VsLn8FM+Gik30KZBaUbpMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggECAIvP7g5RpeBdpYVAtv1iqo/8ze6GXWq3sCJQXKqvRVI/FngriuM68h/1cke8r7u2XwmLdA10vBrNtbuoce6v/4RYRKJWiy7RhqSJEogAqzRp0d27S4ECW7C7IRhYhSR0/Z9Bh/OAo/G1LxP4wHxLfju0hFcFxcp/Gf/TTd6meIPsF129fsB/iwg2ZdkpnxPRi7IPV/P3ywPTPZypfj1plT6aFLLMqwT9/j09CLSXtG22dojJ1AW0EkUxLfSlfRWbL+rGInWZRvn+oU2ghzWGU8xhtNIZOoAeW8WFx5JGVM0qI71pIF8+Bx+5DFg3QxRfVByxpZebYA7KNOcyNDVGe+dF))), sPSSODescriptor=SPSSODescriptor(authnRequestsSigned=false, wantAssertionsSigned=true, protocolSupportEnumeration=urn:oasis:names:tc:SAML:2.0:protocol, singleLogoutService=SingleLogoutService(binding=urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect, location=https://math.vpv.io/signout), nameIDFormat=urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified, assertionConsumerService=AssertionConsumerService(binding=urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST, location=https://math.vpv.io/SAML/POST, index=1)), organization=Organization(organizationName=OrganizationName(lang=en-US, value=MATH VPV), organizationDisplayName=OrganizationDisplayName(lang=en-US, value=MATH VPV), organizationURL=OrganizationURL(lang=en-US, value=https://math.vpv.io)), contactPerson=[ContactPerson(contactType=technical, givenName=Venkateswara, emailAddress=contact@vpv.io), ContactPerson(contactType=support, givenName=Venkateswara, emailAddress=contact@vpv.io)])
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

## Building

The Minimum JDK required for this is `JDK 11`. To build the project locally clone the repo and run the below command.


```shell script
./mvnw clean package
```
This should result in the `jar` file on the `/target` folder.

## Publishing Snapshot builds

If we wish to publish snapshot build to reporitory please issue the below command.

```shell script
./mvnw clean deploy
```

## Making a Release Build

To make a release build we will have to do the following steps in sequence.

### Step 1
```shell script
./mvnw release:clean release:prepare
```
### Step 2
```shell script
./mvnw release:perform
```

## Troubleshooting

Some of the common issues that you face during publish.

1. Ensure to have your SCM workspace clean before release.
2. Refer to the [Maven deployment guideline].
3. If it fails in the middle of publish, then cleanup your local SCM workspace and begin



## Links

1. [Maven deployment guideline](https://central.sonatype.org/pages/apache-maven.html)

## Code Coverage Graphs

### Sunburst
![Sunburst](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/sunburst.svg "Sunburst Code Coverage")

### Icicle
![Icicle](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/icicle.svg "Icicle Code Coverage")

### Grid
![Grid](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/tree.svg "Grid Code Coverage")

### Commits Graph
![Grid](https://codecov.io/gh/reflexdemon/java-saml-metadata/branch/master/graphs/commits.svg "Commit Graph")
