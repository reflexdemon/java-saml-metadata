
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class Organization {

    public OrganizationName organizationName;
    public OrganizationDisplayName organizationDisplayName;
    public OrganizationURL organizationURL;

}
