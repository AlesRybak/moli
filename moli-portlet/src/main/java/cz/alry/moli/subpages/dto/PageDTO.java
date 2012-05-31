package cz.alry.moli.subpages.dto;

/**
 *
 * @author Ales Rybak <ales.rybak@ibacz.eu>
 */
public class PageDTO {

    private long layoutId;
    
    private String name;
    
    private String iconURL;
    
    private String link;

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(long layoutId) {
        this.layoutId = layoutId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
