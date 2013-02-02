package cz.alry.moli.subpages.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import static cz.alry.moli.common.helper.LiferayHelper.*;
import cz.alry.moli.subpages.dto.PageDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.portlet.*;


/**
 * @author Ales Rybak <ales.rybak@gmail.com>
 */
public class SubpagesDisplayPortlet extends GenericPortlet {

    public static final String JSP_MAIN = "/WEB-INF/jsp/subpages/view.jsp";
    public static final String ATTR_SUBPAGES = "subpages";
    
    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        prepareSubpages(request);
        PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher(JSP_MAIN);
        dispatcher.include(request, response);
    }

    private void prepareSubpages(RenderRequest request) throws PortletException {
        try {
        
            ThemeDisplay td = getThemeDisplay(request);
            Layout currentPage = td.getLayout();
            List<Layout> layouts = currentPage.getChildren();
            
            List<PageDTO> subpages = new ArrayList<PageDTO>(layouts.size());
            
            for (Layout layout : layouts) {
                PageDTO page = new PageDTO();
                page.setLayoutId(layout.getLayoutId());
                page.setName(layout.getName(request.getLocale()));
                page.setLink(PortalUtil.getLayoutFriendlyURL(layout, td, request.getLocale()));
                page.setIconURL(getLayoutIconUrl(td, layout));

                subpages.add(page);
            }
            
            request.setAttribute(ATTR_SUBPAGES, subpages);
            
        } catch (PortalException ex) {
            throw new PortletException(ex);
        } catch (SystemException ex) {
            throw new PortletException(ex);
        }
    }

}
