
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
public class IDPMetaData {

    public String iD;
    public String entityID;
    public Signature signature;
    public List<RoleDescriptor> roleDescriptor;
    public IDPSSODescriptor iDPSSODescriptor;
    public String validUntil;
    public String cacheDuration;
    public Organization organization;
    public List<ContactPerson> contactPerson;
}
