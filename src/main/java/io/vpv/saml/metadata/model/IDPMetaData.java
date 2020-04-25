
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@ToString
@Builder
public class IDPMetaData {

    public String validUntil;
    public String cacheDuration;
    public String entityID;
    public String iD;
    public Signature signature;
    public IDPSSODescriptor iDPSSODescriptor;
    public Organization organization;
    public List<ContactPerson> contactPerson = new ArrayList<ContactPerson>();

}
