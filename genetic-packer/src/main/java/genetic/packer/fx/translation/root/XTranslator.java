package genetic.packer.fx.translation.root;

import genetic.packer.fx.specification.BoundsGetter;
import genetic.packer.fx.specification.BoxTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("xTranslator")
public class XTranslator extends BoxRootBasedTranslator {

    @Autowired
    public XTranslator(@Qualifier("xBoxTranslator") BoxTranslator xTranslationSetter, BoundsGetter xSizeGetter) {
        super(xTranslationSetter, xSizeGetter);
    }
}