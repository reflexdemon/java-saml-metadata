
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
public class SPMetaData {

    public String validUntil;
    public String cacheDuration;
    public String entityID;
    public String iD;
    public Signature signature;
    public SPSSODescriptor sPSSODescriptor;
    public Organization organization;
    public List<ContactPerson> contactPerson = new ArrayList<ContactPerson>();

}
