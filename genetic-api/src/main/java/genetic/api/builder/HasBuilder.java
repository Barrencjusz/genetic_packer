package genetic.api.builder;

/**
 * @author piotr.larysz
 */

import java.lang.annotation.Inherited;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder(withSetterNamePattern = "*")
@Inherited
public @interface HasBuilder {
}
