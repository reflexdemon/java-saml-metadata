
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class SPSSODescriptor {

    public String authnRequestsSigned;
    public String wantAssertionsSigned;
    public String protocolSupportEnumeration;
    public SingleLogoutService singleLogoutService;
    public String nameIDFormat;
    public AssertionConsumerService assertionConsumerService;

}
