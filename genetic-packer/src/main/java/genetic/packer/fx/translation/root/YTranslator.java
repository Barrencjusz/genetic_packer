package genetic.packer.fx.translation.root;

import genetic.packer.fx.specification.BoundsGetter;
import genetic.packer.fx.specification.BoxTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("yTranslator")
public class YTranslator extends BoxRootBasedTranslator {

    @Autowired
    public YTranslator(@Qualifier("yBoxTranslator") BoxTranslator yTranslationSetter, BoundsGetter ySizeGetter) {
        super(yTranslationSetter, ySizeGetter);
    }
}
