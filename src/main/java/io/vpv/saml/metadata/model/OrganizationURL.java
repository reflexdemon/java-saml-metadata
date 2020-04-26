
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class OrganizationURL {

    public String lang;
    public String value;

}
