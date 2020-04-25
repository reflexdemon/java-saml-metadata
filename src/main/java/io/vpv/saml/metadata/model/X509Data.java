
package io.vpv.saml.metadata.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class X509Data {

    public String x509Certificate;

}
