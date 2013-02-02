package cz.alry.moli.common.helper;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import javax.portlet.PortletRequest;

/**
 *
 * @author AldaR
 */
public class LiferayHelper {

    private LiferayHelper() {
    }

    /**
     * Extract and return ThemeDisplay object from given request.
     */
    public static ThemeDisplay getThemeDisplay(PortletRequest request) {
        return (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    }

    /**
     * Get URL of icon image of given layout (Liferay page). If layout is NULL
     * or layout doesn't have icon set, method will return NULL.
     * @param td ThemeDisplay from current request
     * @param layout Layout for which the icon URL should be returned
     * @return URL of icon image. NULL if layout doesn't have icon.
     */
    public static String getLayoutIconUrl(ThemeDisplay td, Layout layout) {
        String iconUrl = null;

        if ((layout != null) && layout.isIconImage()) {
            iconUrl = td.getPathImage() + "/layout_icon?img_id=" + layout.getIconImageId();
        }

        return iconUrl;
    }

    /**
     * Get URL of icon image of given layout (Liferay page). If layout is NULL
     * or layout doesn't have icon set, method will return NULL.
     * @param request Current request
     * @param layout Layout for which the icon URL should be returned
     * @return URL of icon image. NULL if layout doesn't have icon.
     */
    public static String getLayoutIconUrl(PortletRequest request, Layout layout) {
        ThemeDisplay td = getThemeDisplay(request);
        return getLayoutIconUrl(td, layout);
    }

}
