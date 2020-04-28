
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RoleDescriptor {

    public String type;
    public String protocolSupportEnumeration;
    public KeyDescriptor keyDescriptor;
    public ClaimTypesOffered claimTypesOffered;
    public SecurityTokenServiceEndpoint securityTokenServiceEndpoint;
    public PassiveRequestorEndpoint passiveRequestorEndpoint;
    public TargetScopes targetScopes;
    public ApplicationServiceEndpoint applicationServiceEndpoint;

}
