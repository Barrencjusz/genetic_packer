package genetic.packer.config;

/**
 * @author piotr.larysz
 */

import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

@GeneratePojoBuilder(withSetterNamePattern = "*")
@Inherited
public @interface BuilderAware {
}
