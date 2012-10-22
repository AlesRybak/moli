package cz.alry.moli.pagenavigation.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import cz.alry.moli.subpages.dto.PageDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.portlet.*;

/**
 * @author Ales Rybak <ales.rybak@ibacz.eu>
 */
public class PageNavigationPortlet extends GenericPortlet {

    public static final String JSP_MAIN = "/WEB-INF/jsp/page-navigation/view.jsp";
    public static final String ATTR_UPPER_PAGE = "upperPage";
    public static final String ATTR_LEFT_PAGE = "leftPage";
    public static final String ATTR_RIGHT_PAGE = "rightPage";

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        prepareNavigationPages(request);
        PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher(JSP_MAIN);
        dispatcher.include(request, response);
    }

    private void prepareNavigationPages(RenderRequest request) throws PortletException {
        try {

            request.setAttribute(ATTR_UPPER_PAGE, getUpperPage(request));
            request.setAttribute(ATTR_LEFT_PAGE, getLeftPage(request));
            request.setAttribute(ATTR_RIGHT_PAGE, getRightPage(request));

        } catch (RuntimeException ex) {
            throw new PortletException(ex);
        }
    }

    private PageDTO getUpperPage(PortletRequest request) {
        Layout upperLayout = getUpperLayout(request);
        return convertLayoutToPageDTO(request, upperLayout);
    }

    private PageDTO getLeftPage(PortletRequest request) {
        return getSieblingPage(request, -1);
    }

    private PageDTO getRightPage(PortletRequest request) {
        return getSieblingPage(request, 1);
    }

    private PageDTO getSieblingPage(PortletRequest request, int indexDelta) {
        Layout rightLayout = getSieblingLayout(request, indexDelta);
        if (rightLayout != null) {
            return convertLayoutToPageDTO(request, rightLayout);
        } else {
            return null;
        }
    }

    private Layout getSieblingLayout(PortletRequest request, int indexDelta) {
        ThemeDisplay td = getThemeDisplay(request);
        Layout currentLayout = getCurrentLayout(request);
        Layout upperLayout = getUpperLayout(request);
        List<Layout> sieblingLayouts = getChildrenOrTopmostLayouts(request, upperLayout);
        int currentIndex = sieblingLayouts.indexOf(currentLayout);
        int newIndex = currentIndex + indexDelta;
        if ((newIndex >= 0) && (newIndex < sieblingLayouts.size())) {
            return sieblingLayouts.get(newIndex);
        } else {
            return null;
        }
    }

    private List<Layout> getChildrenOrTopmostLayouts(PortletRequest request, Layout upperLayout) {
        try {
            ThemeDisplay td = getThemeDisplay(request);
            Layout currentLayout = getCurrentLayout(request);
            List<Layout> sieblingLayouts;
            if (upperLayout == null) {
                sieblingLayouts = LayoutLocalServiceUtil.getLayouts(td.getScopeGroupId(), currentLayout.isPrivateLayout(), 0);
            } else {
                sieblingLayouts = upperLayout.getChildren();
            }
            return sieblingLayouts;
        } catch (SystemException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Layout getCurrentLayout(PortletRequest request) {
        ThemeDisplay td = getThemeDisplay(request);
        return td.getLayout();
    }

    private Layout getUpperLayout(PortletRequest request) {
        try {

            Layout currentLayout = getCurrentLayout(request);
            Long upperLayoutId = currentLayout.getParentPlid();
            if (upperLayoutId != 0) {
                Layout upperLayout = LayoutLocalServiceUtil.getLayout(upperLayoutId);
                System.out.println(upperLayoutId);
                System.out.println(upperLayout.getName());
                return upperLayout;
            } else {
                return null;
            }

        } catch (PortalException ex) {
            throw new RuntimeException(ex);
        } catch (SystemException ex) {
            throw new RuntimeException(ex);
        }
    }

    private ThemeDisplay getThemeDisplay(PortletRequest request) {
        return (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    }

    private String getLayoutIconUrl(PortletRequest request, Layout layout) {
        ThemeDisplay td = getThemeDisplay(request);
        String iconUrl = null;

        if ((layout != null) && layout.isIconImage()) {
            iconUrl = td.getPathImage() + "/layout_icon?img_id=" + layout.getIconImageId();
        }

        return iconUrl;
    }

    private String getLayoutUrl(PortletRequest request, Layout layout) {
        ThemeDisplay td = getThemeDisplay(request);
        Locale locale = request.getLocale();
        return getLayoutUrl(layout, td, locale);
    }

    private String getLayoutUrl(Layout layout, ThemeDisplay td, Locale locale) {
        try {
            return PortalUtil.getLayoutFriendlyURL(layout, td, locale);
        } catch (PortalException ex) {
            throw new RuntimeException(ex);
        } catch (SystemException ex) {
            throw new RuntimeException(ex);
        }
    }

    private PageDTO convertLayoutToPageDTO(PortletRequest request, Layout layout) {
        if (layout == null) {
            return null;
        }

        PageDTO page = new PageDTO();
        page.setLayoutId(layout.getLayoutId());
        page.setName(layout.getName(request.getLocale()));
        page.setLink(getLayoutUrl(request, layout));
        page.setIconURL(getLayoutIconUrl(request, layout));
        return page;
    }
}
