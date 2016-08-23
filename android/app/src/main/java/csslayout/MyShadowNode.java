package csslayout;

import com.facebook.react.uimanager.LayoutShadowNode;

/**
 * Created by s_baa on 8/22/2016.
 */
public class MyShadowNode extends LayoutShadowNode {
    @Override
    public void markUpdated(){
        super.markUpdated();
        dirty();
    }
    @Override
    public boolean isDirty(){
        return true;
    }
    public void doMarkLayoutSeen(){
        markLayoutSeen();
    }
}
