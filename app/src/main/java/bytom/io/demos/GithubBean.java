package bytom.io.demos;

/**
 * Created by Nil on 2018/6/18
 */
public class GithubBean {
    private String current_user_url;
    private String team_url;
    private String hub_url;
    private String following_url;
    private String emojis_url;

    public String getCurrent_user_url() {
        return current_user_url;
    }

    public void setCurrent_user_url(String current_user_url) {
        this.current_user_url = current_user_url;
    }

    public String getTeam_url() {
        return team_url;
    }

    public void setTeam_url(String team_url) {
        this.team_url = team_url;
    }

    public String getHub_url() {
        return hub_url;
    }

    public void setHub_url(String hub_url) {
        this.hub_url = hub_url;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\ncurrent_user_url=" + current_user_url
                + "\nteam_url=" + team_url
                + "\nhub_url=" + hub_url
                + "\nfollowing_url=" + following_url
                + "\nemojis_url=" + emojis_url);
        return builder.toString();
    }
}
