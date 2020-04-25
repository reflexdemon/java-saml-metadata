
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class Reference {

    public String uRI;
    public Transforms transforms;
    public DigestMethod digestMethod;
    public String digestValue;

}
