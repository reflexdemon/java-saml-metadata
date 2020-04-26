
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class IDPSSODescriptor {

    public String wantAuthnRequestsSigned;
    public String protocolSupportEnumeration;
    public List<KeyDescriptor> keyDescriptor;
    public SingleLogoutService singleLogoutService;
    public String nameIDFormat;
    public SingleSignOnService singleSignOnService;

}
