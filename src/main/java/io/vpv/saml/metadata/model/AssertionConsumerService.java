
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class AssertionConsumerService {

    public String binding;
    public String location;
    public String index;

}
