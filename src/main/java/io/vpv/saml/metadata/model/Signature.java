
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class Signature {

    public SignedInfo signedInfo;
    public String signatureValue;
    public KeyInfo keyInfo;

}
