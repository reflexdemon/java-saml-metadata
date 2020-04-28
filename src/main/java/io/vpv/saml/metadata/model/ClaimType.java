
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class ClaimType {

    public String uri;
    public String displayName;
    public String description;

}
