package numberfact.codeham.com.farmdelite;

/**
 * Created by Abhishek on 28/10/2017.
 */

public class AccountClass {

    private String str;
    private int mImagearrPosition;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public AccountClass(String str, int mImagearr) {
        this.str = str;
        this.mImagearrPosition = mImagearr;
    }

    public int getmImagearr() {
        return mImagearrPosition;
    }

    public void setmImagearr(int mImagearr) {
        this.mImagearrPosition = mImagearr;
    }
}
