package ua.nure.easygo.model;

public class User implements Entity {
    public String login;
    public String password;
    public String name;
    //public Bitmap avatar;

    /**
     *
     * @param name
     * @param login
     * @param password
     * @param avatar can be null
     */
    public User(String login, String password, String name) {
        this.name = name;
        this.login = login;
        this.password = password;
        //this.avatar = avatar;
    }

    public User() {
    }
	
	@Override
	public boolean equals(Object obj) {	
		if (obj == null || !(obj instanceof User)) {
			return false;
		}
		return this.login == ((User)obj).login;
	}
	
	@Override
	public int hashCode() {
		return login.hashCode();
	}
}
