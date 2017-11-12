package numberfact.codeham.com.farmdelite;

/**
 * Created by Abhishek on 12/11/2017.
 */

public class UserDetail {
    private String email;
    private String first_name;

    UserDetail() {

    }

    public UserDetail(String email, String first_name, String last_name, String mobile) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile = mobile;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String last_name;
    private String mobile;

}
