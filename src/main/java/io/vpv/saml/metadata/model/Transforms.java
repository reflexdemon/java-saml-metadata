
package io.vpv.saml.metadata.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class Transforms {

    public List<Transform> transform;

}
